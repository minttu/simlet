package fi.paivola.simlet.sampler;

import java.util.List;
import java.util.Map;

/**
 * Created by juhani on 5/14/14.
 */
public interface Sampler {
    public List<Map<String, Double>> CreateSamples(List<Parameter> params, int amount);
}
