package ru.bmstu.lab4;

public class JsonFile {
    private String packageId;
    private String jsScript;
    private String functionName;
    private Test[] tests;

    public JsonFile(String packageId, String jsScript, String functionName, Test[] tests) {
        this.packageId = packageId;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.tests = tests;
    }


    public String getPackageId() {
        return packageId;
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
        this.packageId = packageId;
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
