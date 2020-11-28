package ru.bmstu.lab4;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;

public class TestResults {
    private HashMap<String, Boolean> results;
    private static final String ENGINE_NAME = "nashorn";

    public TestResults() {
        results = new HashMap<>();
    }

    public void runTests(Test[] tests) throws Exception {
        for (Test test : tests) {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);
            
        }
    }

    public HashMap<String, Boolean> getResults() {
        return results;
    }
}
