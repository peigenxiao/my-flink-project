package loadflink;


import myflink.HotItems;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2020/7/3.
 */
public class PowerItems1 {
    public static void main(String[] args) throws Exception {
    // 创建 execution environment
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    // 告诉系统按照 EventTime 处理
  //  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
    // 为了打印到控制台的结果不乱序，我们配置全局的并发为1，改变并发对结果正确性没有影响
    env.setParallelism(1);

    // PowerInfo.csv 的本地文件路径, 在 resources 目录下
    URL fileUrl = HotItems.class.getClassLoader().getResource("PowerInfo.csv");
    Path filePath = Path.fromLocalFile(new File(fileUrl.toURI()));
    // 抽取 PowerInfo 的 TypeInformation，是一个 PojoTypeInfo
    PojoTypeInfo<PowerInfo> pojoType = (PojoTypeInfo<PowerInfo>) TypeExtractor.createTypeInfo(PowerInfo.class);
    // 由于 Java 反射抽取出的字段顺序是不确定的，需要显式指定下文件中字段的顺序

    String[] fieldOrder = new String[]{"CLDBS", "SJSJ", "ZYGGL", "AZXYG", "BZXYG","CZXYG","ZWGGL","AZXWG","BZXWG","CZXWG","SZGL","ASZGL","BSZGL","CSZGL","ZGLYS","AGLYS","BGLYS","CGLYS"};
    // 创建 PojoCsvInputFormat
    PojoCsvInputFormat<PowerInfo> csvInput = new PojoCsvInputFormat<>(filePath, pojoType, fieldOrder);


    env
            // 创建数据源，得到 PowerInfo 类型的 DataStream
            .createInput(csvInput, pojoType)
            // 抽取出时间和生成 watermark
            .keyBy("CLDBS")
            .flatMap(new LoadWithKeyedState())
            //.setParallelism(1)
            .print().setParallelism(1);

    env.execute("Hot Items Job");
}
}
