package ru.bmstu.atom;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.join.TupleWritable;

public class AirportJoinMapper extends MapReduceBase implements Mapper<LongWritable, TupleWritable, LongWritable, FloatWritable> {
}
