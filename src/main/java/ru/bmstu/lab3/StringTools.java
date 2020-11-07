package ru.bmstu.lab3;

public class StringTools {
    private static final String DELIMITER = ",";

    public StringTools() {}

    public static String[] splitWithCommas(String columns) {
        return columns.split(DELIMITER);
    }

    public static String removeQuotes(String str) {
        return str.replaceAll("\"", "");
    }

    public static String concatWords (String[] columns, int start, int end) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(columns[i]);
        }
        return removeQuotes(builder.toString());
    }
}