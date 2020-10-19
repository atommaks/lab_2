package ru.bmstu.atom;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightJoinMapper extends Mapper<LongWritable, Text, IDKey, FloatWritable> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 10;
    private static final int DELAY_COLUMN_NUMBER = 18;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (!key.equals(new LongWritable(0))) {
            String[] columns = value.toString().split(",");
            Integer airportId = Integer.parseInt(columns[AIRPORT_CODE_COLUMN_NUMBER]);
            FloatWritable delay = new FloatWritable(Float.parseFloat(columns[DELAY_COLUMN_NUMBER]));
            Context.write(new IDKey(airportId, true, value), delay);
        }
    }
}
