package ru.bmstu.lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonFile {
    public static class Test {
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

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public void setParams(Object[] params) {
            this.params = params;
        }

        public void setExpectedResult(String expectedResult) {
            this.expectedResult = expectedResult;
        }
    }


    private String packageID;
    private String jsScript;
    private String functionName;
    private Test[] tests;

    //@JsonCreator
    //public JsonFile(@JsonProperty("packageId") String packageId, @JsonProperty("jsScript") String jsScript, @JsonProperty("functionName") String functionName, @JsonProperty("tests") Test[] tests) {
    public JsonFile(String packageId, String jsScript, String functionName, JsonFile.Test[] tests) {
        this.packageID = packageId;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.tests = tests;
    }


    public String getPackageId() {
        return packageID;
    }

    public String getJsScript() {
        return jsScript;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Test[] getTests() {
        return tests;
    }

    public void setPackageId(String packageId) {
        this.packageID = packageId;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    public void setTests(Test[] tests) {
        this.tests = tests;
    }
}
