package myflink;

import loadflink.PowerInfo;
import org.apache.flink.api.common.functions.FlatMapFunction;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * Created by Administrator on 2020/7/5.
 */
public class WindowWordCount {
    public static void main(String[] args) throws Exception {

        final ParameterTool parameters = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(parameters);

        env.enableCheckpointing(5000, CheckpointingMode.AT_LEAST_ONCE);

        DataStreamSource<String> dataStream = env.addSource(new SourceFromFile()).setParallelism(1);

        dataStream
                .flatMap(new FlatMapFunction<String, PowerInfo>() {
                    @Override
                    public void flatMap(String value, Collector<PowerInfo> out) throws Exception {
                        String[] arr = value.split("[|][|]");
                       /* for (String item : arr) {
                            out.collect(new Tuple2<>(item, 1));
                        }*/
                        out.collect(PowerInfo.of(Long.parseLong(arr[0]),arr[1],Double.parseDouble(arr[2]),Double.parseDouble(arr[3]),Double.parseDouble(arr[4]),Double.parseDouble(arr[5]),Double.parseDouble(arr[6]),Double.parseDouble(arr[7]),Double.parseDouble(arr[8]),Double.parseDouble(arr[9]),Double.parseDouble(arr[10]),Double.parseDouble(arr[11]),Double.parseDouble(arr[12]),Double.parseDouble(arr[13]),Double.parseDouble(arr[14]),Double.parseDouble(arr[15]),Double.parseDouble(arr[16]),Double.parseDouble(arr[17])));

                    }
                })
                .keyBy("CLDBS")
                //.timeWindow(Time.minutes(1))
                //.sum(1)
                .print();


      /*  文件中输入：
        aaa,bbb,ccc

        结果：
        2> (bbb,1)
        3> (aaa,1)
        4> (ccc,1)*/

        env.execute("WindowWordCount");
    }
}
