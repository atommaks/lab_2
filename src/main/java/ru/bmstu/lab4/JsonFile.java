package ru.bmstu.lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonFile {
    private String packageID;
    private String jsScript;
    private String functionName;
    private Test[] tests;

    public JsonFile() {}

    //@JsonCreator
    //public JsonFile(@JsonProperty("packageId") String packageId, @JsonProperty("jsScript") String jsScript, @JsonProperty("functionName") String functionName, @JsonProperty("tests") Test[] tests) {
    public JsonFile(String packageId, String jsScript, String functionName, Test[] tests) {
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
