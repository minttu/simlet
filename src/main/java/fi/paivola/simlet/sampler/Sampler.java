package fi.paivola.simlet.sampler;

import java.util.List;

/**
 * Created by juhani on 5/14/14.
 */
public interface Sampler {
    public void CreateSamples(List<Parameter> params, int amount);
}
