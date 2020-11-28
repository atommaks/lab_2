package ru.bmstu.lab4;

public class StoreMessage {
    private String packageID;
    private String functionName;
    private TestResults results;

    public StoreMessage (String packageID, String functionName, TestResults results) {
        this.packageID = packageID;
        this.functionName = functionName;
        this.results = results;
    }

    @Override
    public String toString() {

    }
}
