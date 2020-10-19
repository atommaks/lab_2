package ru.bmstu.atom;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.join.CompositeInputFormat;

import java.io.IOException;

public class DataJoinApp {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: DataJoinApp <input file_1> <input file_2> <output path>");
            System.exit(-1);
        }

        JobConf conf = new JobConf(JoinJob.class);
        conf.setJobName("map join");
        conf.setInputFormat(CompositeInputFormat.class);
        FileOutputFormat.setOutputPath(conf, new Path(args[2]));
        conf.set("mapred.join.expr", CompositeInputFormat.compose("inner",
                KeyValueTextInputFormat.class,
                args[0],
                args[1]
            ));
        conf.setMapperClass(MapJoinMapper.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
        JobClient.runJob(conf);
    }
}