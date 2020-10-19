package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.join.TupleWritable;

import java.io.IOException;

public class AirportJoinMapper extends MapReduceBase implements Mapper<LongWritable, TupleWritable, LongWritable, Text> {
    @Override
    public void map(LongWritable key, TupleWritable value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
        LongWritable airportCode = (LongWritable) value.get(0);
        Text airportName = (Text) value.get(1);
        output.collect(airportCode, airportName);
    }
}
