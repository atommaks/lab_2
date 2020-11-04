package ru.bmstu.lab3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.Map;


public class FlightApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: FlightApp <input file_flight_info> <input file_airport_info> <output path>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaPairRDD<LongWritable, Text> flightInfoRDD = sc.hadoopFile(args[0], TextInputFormat.class, LongWritable.class, Text.class);
        JavaPairRDD<LongWritable, Text> airportInfoRDD = sc.hadoopFile(args[1], TextInputFormat.class, LongWritable.class, Text.class);
        JavaPairRDD<Long, String> airportInfoPairRDD =  airportInfoRDD
                .filter(AirportTools.removeFirstLine)
                .mapToPair(AirportTools.airportNamesKeyData);
        final Broadcast<Map<Long, String>> airportInfoBroadcasted = sc.broadcast(airportInfoPairRDD.collectAsMap());
        JavaPairRDD<String, String> result = flightInfoRDD
                .filter(AirportTools.removeFirstLine)
                .mapToPair(AirportTools.airportFlightsKeyData)
                .reduceByKey(AirportTools.airportFlightsUniqueKeyData)
                .mapToPair(AirportTools.getAirportResultData(airportInfoBroadcasted));
        result.saveAsTextFile(args[2]);
    }
}
