package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightJoinMapper extends Mapper<LongWritable, Text, IDKey, Text> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 10;
    private static final int DELAY_COLUMN_NUMBER = 18;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (!key.equals(new LongWritable(0))) {
            String[] columns = value.toString().replaceAll(" ", "").split(",");
//            System.out.println();
//            System.out.println("--------------------------------------------");
//            for (int i = 0; i < columns.length; i++) {
//                System.out.println(i + ": " + columns[i]);
//            }
//            System.out.println();
//            System.out.println("--------------------------------------------");
            Integer airportId = Integer.parseInt(columns[AIRPORT_CODE_COLUMN_NUMBER].replaceAll("\"", ""));
            String delay = columns[DELAY_COLUMN_NUMBER];
            if (!delay.isEmpty()) {
                context.write(new IDKey(airportId, true, delay), new Text(delay));
            }
        }
    }
}