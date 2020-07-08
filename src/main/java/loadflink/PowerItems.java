package loadflink;

import myflink.HotItems;
import org.apache.flink.api.common.functions.FilterFunction;
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
public class PowerItems {
    public static void main(String[] args) throws Exception {
    // 创建 execution environment
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    // 告诉系统按照 EventTime 处理
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
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
            .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<PowerInfo>() {
        @Override
        public long extractAscendingTimestamp(PowerInfo powerInfo) {
            // 原始数据单位秒，将其转成毫秒
            Date datetime = null;
            long Daytime = 0;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                datetime = format.parse(powerInfo.SJSJ);

                Daytime = (long) (datetime.getTime() );

            } catch (Exception e) {
                    e.printStackTrace();
                }
      //      System.out.println(datetime);
      //      System.out.println(Daytime);
            return Daytime;
        }
    })
            // 过滤出只有点击的数据
       /*     .filter(new FilterFunction<PowerInfo>() {
        @Override
       *//* public boolean filter(PowerInfo PowerInfo) throws Exception {
            // 过滤出只有点击的数据
            return PowerInfo.behavior.equals("pv");
        }*//*
    })*/
            .keyBy("CLDBS")
    .timeWindow(Time.minutes(60), Time.minutes(5))
            .aggregate(new CountAgg(), new WindowResultFunction())
            .keyBy("windowEnd")
    .process(new TopNHotItems(3))
            .print();

    env.execute("Hot Items Job");
}
}
