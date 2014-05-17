package fi.paivola.simlet.runner;

import fi.paivola.simlet.sampler.Parameter;
import fi.paivola.simlet.sampler.Sampler;
import fi.paivola.simlet.time.ScheduleItem;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Time;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by juhani on 16.5.2014.
 */
public class Configure implements Runnable {
    private final ScriptObjectMirror param;

    public Configure(ScriptObjectMirror param) {
        this.param = param;
    }

    @Override
    public void run() {
        Sampler sampler = (Sampler) param.get("sampler");
        List<Parameter> parameterList = ((ScriptObjectMirror) param.get("parameters")).values().stream().map(obj -> (Parameter) obj).collect(Collectors.toList());

        int samples = (int) param.get("samples");
        List<Map<String, Double>> mapList = sampler.CreateSamples(parameterList, samples);

        for(Parameter parameter : parameterList) {
            System.out.println(parameter.getName());
            System.out.println(parameter.getValues());
        }

        int run = 0;

        for (int i = 0; i < samples; i++) {
            int sample = i;
            Scheduler scheduler = new Scheduler((Time) param.getMember("ends"));
            param.callMember("plan", scheduler, mapList.get(sample));
            System.out.printf(" --- \nRun %d Sample %d\n --- \n", run, sample);
            scheduler.run();
        }
    }
}
