package ru.bmstu.lab4;

import javax.script.ScriptEngine;
import java.util.HashMap;

public class TestResults {
    private HashMap<String, Boolean> results;

    public TestResults() {
        results = new HashMap<>();
    }

    public void runTests(Test[] tests) throws Exception {
        for (Test test : tests) {
            ScriptEngine
        }
    }

    public HashMap<String, Boolean> getResults() {
        return results;
    }
}
