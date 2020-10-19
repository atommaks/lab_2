package ru.bmstu.atom;

import org.apache.hadoop.mapred.JobConf;

public class DataJoinApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: DataJoinApp <input file_1> <input file_2> <output path>");
            System.exit(-1);
        }

        JobConf conf = new JobConf(JoinJob.class);
        
    }
}