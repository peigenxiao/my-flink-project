package myflink;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/7/5.
 */
public class SourceFromFile extends RichSourceFunction<String> {
    private volatile Boolean isRunning = true;

    @Override
    public void run(SourceContext ctx) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\计量文件\\功率\\20180829\\test1.txt"));
   //12035911625||2018-08-29 00:00:00||0.5548||0.2186||0.1455||0.1907||0.0373||0.0241||0.0030||0.0102||0||0||0||0||0.9980||0.9940||1.0000||0.9990
   //648162889||2018-08-28 23:45:00||0.9165||0.3029||0.3887||0.2250||0.1758||0.0517||0.0557||0.0684||||||||||0.9820||0.9860||0.9900||0.9570
   //7689895710||2018-08-28 23:45:00||0.8623||0.2941||0.3190||0.2493||0.1614||0.0455||0.0658||0.0502||||||||||0.9830||0.9880||0.9790||0.9800
        while (isRunning) {
            String line = bufferedReader.readLine();
            if (StringUtils.isBlank(line)) {
                continue;
            }
            ctx.collect(line);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}