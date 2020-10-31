package ru.bmstu.lab2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightJoinMapper extends Mapper<LongWritable, Text, IDKey, Text> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 14;
    private static final int DELAY_COLUMN_NUMBER = 18;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (!key.equals(new LongWritable(0))) {
            String[] columns = getColumns(value.toString());
            Integer airportId = Integer.parseInt(removeQuotes(columns[AIRPORT_CODE_COLUMN_NUMBER]));
            String delay = columns[DELAY_COLUMN_NUMBER];
            if (!delay.isEmpty()) {
                context.write(new IDKey(airportId, true), new Text(delay));
            }
        }
    }

    private static String[] getColumns(String columns) {
        return columns.split(COMMA_DELIMITER);
    }

    private static String removeQuotes(String str) {
        return str.replaceAll("\"", "");
    }
}