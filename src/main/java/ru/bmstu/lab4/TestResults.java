package ru.bmstu.lab4;

import java.util.HashMap;

public class TestResults {
    private HashMap<String, Boolean> results;

    public TestResults() {
        results = new HashMap<>();
    }

    public void runTests(Test[] tests) {
        for (Test test : tests) {

        }
    }

    public HashMap<String, Boolean> getResults() {
        return results;
    }
}
