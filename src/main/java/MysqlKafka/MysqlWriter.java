package MysqlKafka;

/**
 * Created by Administrator on 2020/7/7.
 */

import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author steven
 */
public class MysqlWriter extends RichSinkFunction<Tuple4<Integer,String, String,Integer>> {
    private Connection connection = null;
    private PreparedStatement ps = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动
            connection = DriverManager.getConnection("jdbc:mysql://peigen004:3306/flinktest?useUnicode=true&characterEncoding=UTF-8", "root", "123456");//获取连接
        }

        ps = connection.prepareStatement("insert into student2 values (?,?,?,?)");
        System.out.println("123123");
    }

    @Override
    public void invoke(Tuple4<Integer,String, String,Integer> value, Context context) throws Exception {
        //获取JdbcReader发送过来的结果
        try {
            ps.setString(1, value.f0.toString());
            ps.setString(2, value.f1);
            ps.setString(3, value.f2);
            ps.setString(4, value.f3.toString());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }
        super.close();
    }


}
