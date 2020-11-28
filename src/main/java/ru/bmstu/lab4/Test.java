package ru.bmstu.lab4;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test {
    private static final String ENGINE_NAME = "nashorn";
    private String testName;
    private String expectedResult;
    private Object[] params;

    public Test(String testName, String expectedResult, Object[] params) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
    }

    public String getTestName() {
        return testName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public Object[] getParams() {
        return params;
    }

    public void runTest() throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);
        
    }
}
