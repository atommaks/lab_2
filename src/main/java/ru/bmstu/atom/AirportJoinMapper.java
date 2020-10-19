package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, IDKey, Text> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 0;
    private static final int AIRPORT_DESCRIPTION_COLUMN_NUMBER = 1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (!key.equals(new LongWritable(0))) {
            String[] columns = value.toString().replaceAll(" ", "").split(",");
            System.out.println();
            System.out.println("--------------------------------------------");
            for (int i = 0; i < columns.length; i++) {
                System.out.println(i + ": " + columns[i]);
            }
            System.out.println();
            System.out.println("--------------------------------------------");
            Integer airportCode = Integer.parseInt(columns[AIRPORT_CODE_COLUMN_NUMBER].replaceAll("\"", ""));
            String name = columns[AIRPORT_DESCRIPTION_COLUMN_NUMBER];
            context.write(new IDKey(airportCode, false, name), new Text(name));
        }
    }
}