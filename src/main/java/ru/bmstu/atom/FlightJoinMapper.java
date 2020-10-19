package ru.bmstu.atom;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlightJoinMapper extends Mapper<LongWritable, TupleWritable, IDKey, Text> {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 10;
    private static final int DELAY_COLUMN_NUMBER = 17;

    public void map(LongWritable key, TupleWritable value, OutputCollector<IDKey, Text> output, Reporter reporter) throws IOException {
        IDKey airportCode = (IDKey) value.get(AIRPORT_CODE_COLUMN_NUMBER);
        Text delay = (Text) value.get(DELAY_COLUMN_NUMBER);
        output.collect(airportCode, delay);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        Pattern ptr = Pattern.compile(PATTERN);
        Matcher matcher = ptr.matcher(line);
        while (matcher.find()) {
            context.write(new Text(matcher.group().toLowerCase()), new IntWritable(1));
        }
    }
}
