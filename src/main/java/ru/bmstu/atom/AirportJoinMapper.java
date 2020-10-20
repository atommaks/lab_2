package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, IDKey, Text> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 0;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (!key.equals(new LongWritable(0))) {
            String[] columns = value.toString().split(",");
            Integer airportCode = Integer.parseInt(columns[AIRPORT_CODE_COLUMN_NUMBER].replaceAll("\"", ""));
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < columns.length; i++) {
                builder.append(columns[i]);
            }
            String name = builder.toString().replaceAll("\"", "");
            if (!name.isEmpty()) {
                context.write(new IDKey(airportCode, false), new Text(name));
            }
        }
    }
}