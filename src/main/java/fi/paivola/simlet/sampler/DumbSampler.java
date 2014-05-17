package fi.paivola.simlet.sampler;

import java.util.*;

/**
 * Created by juhani on 5/14/14.
 */
public class DumbSampler implements Sampler {
    @Override
    public List<Map<String, Double>> CreateSamples(List<Parameter> params, int amount) {
        List<Map<String, Double>> mapList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Map<String, Double> map = new HashMap<>();
            for (Parameter param : params) {
                double min = param.getMin();
                double max = param.getMax();
                double step = (max - min) / amount;
                double val = min + step * i + new Random().nextDouble()*step;
                param.addValue(val);
                map.put(param.getName(), val);
            }
            mapList.add(map);
        }
        return mapList;
    }
}
