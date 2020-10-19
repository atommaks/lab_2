package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightJoinMapper extends Mapper<LongWritable, Text, IDKey, Text> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 10;
    //private static final int DELAY_COLUMN_NUMBER = 17;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] columns = value.toString().split(",");
        Integer airportId = Integer.parseInt(columns[AIRPORT_CODE_COLUMN_NUMBER]);
        Context.write();
    }
}
