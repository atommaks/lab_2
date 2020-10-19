package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, IDKey, Text> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 0;
    private static final int AIRPORT_DESCRIPTION_COLUMN_NUMBER_ONE = 1;
    private static final int AIRPORT_DESCRIPTION_COLUMN_NUMBER_TWO = 2;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (!key.equals(new LongWritable(0))) {
            String[] columns = value.toString().replaceAll(" ", "").split(",");
            Integer airportCode = Integer.parseInt(columns[AIRPORT_CODE_COLUMN_NUMBER].replaceAll("\"", ""));
            String name = "";
            for (int i = 1; i < columns.length; i++) {
                name += columns[i];
            }
            name = name.replaceAll("\"", "");
            context.write(new IDKey(airportCode, false, name), new Text(name));
        }
    }
}