package loadflink;

import org.apache.flink.api.common.functions.AggregateFunction;

/**
 * Created by Administrator on 2020/7/3.
 */
public class CountAgg implements AggregateFunction<PowerInfo, Long, Long> {
    @Override
    public Long createAccumulator() {
        return 0L;
    }

    @Override
    public Long add(PowerInfo userBehavior, Long acc) {
        return acc + 1;
    }

    @Override
    public Long getResult(Long acc) {
        return acc;
    }

    @Override
    public Long merge(Long acc1, Long acc2) {
        return acc1 + acc2;
    }
}
