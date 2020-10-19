package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportJoinMapper extends Mapper<LongWritable, Text, IDKey, FloatWritable> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 0;
    private static final int AIRPORT_DESCRIPTION_COLUMN_NUMBER = 1;

    public void map(LongWritable key, TupleWritable value, OutputCollector<IDKey, Text> output, Reporter reporter) throws IOException {
        IDKey airportCode = (IDKey) value.get(AIRPORT_CODE_COLUMN_NUMBER);
        Text airportName = (Text) value.get(AIRPORT_DESCRIPTION_COLUMN_NUMBER);
        output.collect(airportCode, airportName);
    }
}
