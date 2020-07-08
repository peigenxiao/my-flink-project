package MysqlKafka;

/**
 * Created by Administrator on 2020/7/7.
 */
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlReader extends RichSourceFunction<Tuple4<Integer,String, String,Integer>> {


    private Connection connection = null;
    private PreparedStatement ps = null;


    //该方法主要用于打开数据库连接，下面的ConfigKeys类是获取配置的类
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动
        connection = DriverManager.getConnection("jdbc:mysql://peigen004:3306/flinktest?useUnicode=true&characterEncoding=UTF-8", "root", "123456");//获取连接
        ps = connection.prepareStatement("select id,name,password,age from student");

    }


    @Override
    public void run(SourceContext<Tuple4<Integer,String, String,Integer>> sourceContext) throws Exception {
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Tuple4<Integer,String, String,Integer> tuple = new Tuple4<Integer,String, String,Integer>();
            tuple.setFields(Integer.parseInt(resultSet.getString(1)) , resultSet.getString(2), resultSet.getString(3),Integer.parseInt(resultSet.getString(4)));
            sourceContext.collect(tuple);
        }
    }

    @Override
    public void cancel() {
        try {
            super.close();
            if (connection != null) {
                connection.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
