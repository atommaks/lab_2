package ru.bmstu.lab4;

public class Test {
    private String testName;
    private String expectedResult;
    private Object[] params;

    public Test() {}

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

//    public void setTestName(String testName) {
//        this.testName = testName;
//    }
//
//    public void setParams(Object[] params) {
//        this.params = params;
//    }
//
//    public void setExpectedResult(String expectedResult) {
//        this.expectedResult = expectedResult;
//    }
}