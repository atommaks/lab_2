package ru.bmstu.atom;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.join.TupleWritable;


import java.io.IOException;

public class FlightJoinMapper extends MapReduceBase implements Mapper<LongWritable, TupleWritable, LongWritable, FloatWritable> {
    @Override
    public void map(LongWritable key, TupleWritable value, OutputCollector<LongWritable, FloatWritable> output, Reporter reporter) throws IOException {
        LongWritable airportCode = (LongWritable) value.get(10);
        FloatWritable delay = (FloatWritable) value.get(17);
        output.collect(airportCode, delay);
    }
}
