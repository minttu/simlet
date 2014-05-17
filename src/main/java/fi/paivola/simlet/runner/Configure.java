package fi.paivola.simlet.runner;

import fi.paivola.simlet.sampler.Parameter;
import fi.paivola.simlet.sampler.Sampler;
import fi.paivola.simlet.time.ScheduleItem;
import fi.paivola.simlet.time.Scheduler;
import fi.paivola.simlet.time.Time;
import fi.paivola.simlet.ui.ParameterPane;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
public class Configure extends Task<Integer> {
    private final ScriptObjectMirror param;
    public static ParameterPane parameterPane = null;

    public Configure(ScriptObjectMirror param) {
        this.param = param;
    }

    @Override
    public Integer call() {
        Sampler sampler = (Sampler) param.get("sampler");
        List<Parameter> parameterList = ((ScriptObjectMirror) param.get("parameters")).values().stream().map(obj -> (Parameter) obj).collect(Collectors.toList());

        int runs = (int) param.get("runs");

        int samples = (int) param.get("samples");
        List<Map<String, Double>> mapList = sampler.CreateSamples(parameterList, samples);

        if(parameterPane != null) {
            Platform.runLater(() -> {
                parameterPane.clear();
                parameterList.forEach(parameterPane::addParameter);
                parameterPane.update();
            });
        }

        for (int run = 0; run < runs; run++) {
            for (int sample = 0; sample < samples; sample++) {
                Scheduler scheduler = new Scheduler((Time) param.getMember("ends"));
                param.callMember("plan", scheduler, mapList.get(sample));
                System.out.printf(" --- \nRun %d Sample %d\n --- \n", run, sample);
                scheduler.run();
                updateProgress(sample + (samples * run), samples * runs);
            }
        }
        updateProgress(1, 1);

        return 0;
    }
}
