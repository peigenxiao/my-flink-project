package loadflink;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * @Author: 🐟lifei🐟
 * @Date: 2019/1/22 下午12:18
 */
public class LoadWithKeyedState extends RichFlatMapFunction<PowerInfo,Tuple4<Long,String,Long,Long>> {

    private transient ValueState<Tuple4<Long,String,Long,Long>> sum;

    @Override
    public void flatMap(PowerInfo value, Collector<Tuple4<Long,String, Long,Long>> out) throws Exception {


        Tuple4<Long,String,Long,Long> currentSum=sum.value();


        if(null==currentSum){
            currentSum= Tuple4.of(value.CLDBS,value.SJSJ,0L,0L);
        }

        if(value.ZYGGL >= 1){
            currentSum.f2+=1;  currentSum.f3+=1;}
         if (value.ZYGGL >= 0.8 && value.ZYGGL < 1){
            currentSum.f2+=1;  currentSum.f3 = 0L;}
         if (value.ZYGGL < 0.8) {sum.clear();}

       // out.collect(Tuple3.of(value.CLDBS,currentSum.f2, currentSum.f3));

            if ((currentSum.f3 >= 1) && (value.ZYGGL < 1)) {
                out.collect(Tuple4.of(value.CLDBS,value.SJSJ,currentSum.f2, currentSum.f3));
                currentSum.f3 = 0L;
            }

            if ((currentSum.f2 >= 1) && (value.ZYGGL < 0.8)) {
                out.collect(Tuple4.of(value.CLDBS,value.SJSJ,currentSum.f2, currentSum.f3));
                currentSum.f2 = 0L;
            }

        sum.update(currentSum);


    }

    @Override
    public void open(Configuration parameters) throws Exception {
        /**
         * 注意这里仅仅用了状态，但是没有利用状态来容错
         */
        ValueStateDescriptor<Tuple4<Long,String,Long,Long>> descriptor=
                new ValueStateDescriptor<>(
                        "avgState",
                        TypeInformation.of(new TypeHint<Tuple4<Long,String, Long,Long>>() {})
                );
//        descriptor.enableTimeToLive(ttlConfig);

        sum=getRuntimeContext().getState(descriptor);

    }
}
