package ru.bmstu.lab2;

public class StringTools {
    private static final String COMMA_DELIMITER = ",";

    public StringTools() {}

    public static String[] getColumns(String columns) {
        return columns.split(COMMA_DELIMITER);
    }

    public static String removeQuotes(String str) {
        return str.replaceAll("\"", "");
    }
}
