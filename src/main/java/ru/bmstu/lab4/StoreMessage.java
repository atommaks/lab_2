package ru.bmstu.lab4;

public class StoreMessage {
    private String packageID;
    private String functionName;
    private TestResults results;
    private static final String TEST_RESULTS_FORMAT = "PackageID: %s, Results:\n %s";

    public StoreMessage (String packageID, String functionName, TestResults results) {
        this.packageID = packageID;
        this.functionName = functionName;
        this.results = results;
    }

    public String getPackageID() {
        return packageID;
    }

    public TestResults getResults() {
        return results;
    }

    @Override
    public String toString() {
        return String.format(TEST_RESULTS_FORMAT, packageID, results.toString());
    }
}
