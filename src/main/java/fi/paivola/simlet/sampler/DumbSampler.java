package fi.paivola.simlet.sampler;

import java.util.List;

/**
 * Created by juhani on 5/14/14.
 */
public class DumbSampler implements Sampler {
    @Override
    public void CreateSamples(List<Parameter> params, int amount) {
        for (Parameter p : params) {
            double min = p.getMin();
            double max = p.getMax();
            double step = (max - min) / amount;
            for (int i = 0; i < amount; i++) {
                p.addValue(step * i);
            }
        }
    }
}
