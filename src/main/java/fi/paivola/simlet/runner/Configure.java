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
public class Configure {
    private final ScriptObjectMirror param;
    private final List<Parameter> parameterList;
    private final Sampler sampler;
    private final int runs;
    private final int samples;
    private final List<Map<String, Double>> mapList;
    public static ParameterPane parameterPane = null;


    public Configure(ScriptObjectMirror param) {
        this.param = param;
        parameterList = ((ScriptObjectMirror) param.get("parameters")).values().stream().map(obj -> (Parameter) obj).collect(Collectors.toList());
        sampler = (Sampler) param.get("sampler");
        runs = (int) param.get("runs");
        samples = (int) param.get("samples");
        mapList = sampler.CreateSamples(parameterList, samples);
    }

    public void updateParameterPane() {
        if(parameterPane != null) {
            Platform.runLater(() -> {
                parameterPane.clear();
                parameterList.forEach(parameterPane::addParameter);
                // parameterPane.update();
            });
        }
    }

    public void run() {

        for (int run = 0; run < runs; run++) {
            for (int sample = 0; sample < samples; sample++) {
                Scheduler scheduler = new Scheduler((Time) param.getMember("ends"));
                param.callMember("plan", scheduler, mapList.get(sample));
                System.out.printf(" --- \nRun %d Sample %d\n --- \n", run, sample);
                scheduler.run();
                // updateProgress(sample + (samples * run), samples * runs);
            }
        }
        // updateProgress(1, 1);
    }
}
