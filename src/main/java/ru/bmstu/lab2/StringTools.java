package ru.bmstu.lab2;

public class StringTools {
    private static final String DELIMITER = ",";

    public StringTools() {}

    public static String[] splitWithCommas(String columns) {
        return columns.split(DELIMITER);
    }

    public static String removeQuotes(String str) {
        return str.replaceAll("\"", "");
    }
}
