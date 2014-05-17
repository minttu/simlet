package fi.paivola.simlet.runner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by juhani on 17.5.2014.
 */
public class ConfigureFactory {
    private final String src;

    public ConfigureFactory(String src) {
        this.src = src;
    }

    public Configure create() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        Configure configure = null;
        try {
            engine.eval(src);
            Invocable invocable = (Invocable) engine;
            configure = (Configure)invocable.invokeFunction("getConfiguration");
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return configure;
    }
}
