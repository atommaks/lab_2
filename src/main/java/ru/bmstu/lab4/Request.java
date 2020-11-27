package ru.bmstu.lab4;

public class Request {
    private String packageId;
    private String jsScript;
    private String functionName;
    private Test[] tests;

    public Request (String packageId, String jsScript, String functionName, Test[] tests) {
        this.packageId = packageId;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.tests = tests;
    }
    
}
