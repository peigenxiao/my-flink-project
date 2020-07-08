package MysqlKafka;

/**
 * Created by Administrator on 2020/7/7.
 */

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @Description: MySql读和写
 * @author: :  Steven
 * @Date: 2020/3/20 13:44
 */
public class MysqltoMysql {
    public static void main(String[] args) throws Exception {

       final StreamExecutionEnvironment env =StreamExecutionEnvironment.getExecutionEnvironment();
    //    final StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("localhost", 8081, "D:\\flink-steven\\target\\flink-0.0.1-SNAPSHOT.jar");
        env.setParallelism(8);
        DataStreamSource<Tuple4<Integer,String, String,Integer>> dataStream = env.addSource(new MysqlReader());
        dataStream.print();
        dataStream.addSink(new MysqlWriter());
        env.execute("Flink cost DB data to write Database");

    }


}
