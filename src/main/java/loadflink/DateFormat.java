package loadflink;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2020/7/3.
 */
public class DateFormat {

    public static void main(String[] args) throws Exception {

    String startDate = "2017-08-15 00:00:00";
    String endDate = "2017-08-15 00:00:00";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int startDay = 0;
    int endDay = 0;

    try {
        Date dateStart = format.parse(startDate);
        Date datEnd = format.parse(endDate);

        startDay = (int) (dateStart.getTime() / 1000);
        endDay = (int) (datEnd.getTime() / 1000);
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println(startDay);
    System.out.println(endDay);
}
}