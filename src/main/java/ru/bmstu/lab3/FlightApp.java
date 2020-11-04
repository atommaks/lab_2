package ru.bmstu.lab3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

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
        JavaPairRDD<Tuple2<Long, Long>, FlightData> flightInfoPairRDD = flightInfoRDD
                .filter(AirportSparkFunctions.filterFunction)
                .mapToPair(AirportSparkFunctions.airportFlightsKeyData);
        JavaPairRDD<Long, String> airportInfoPairRDD =  airportInfoRDD
                .filter(AirportSparkFunctions.filterFunction)
                .mapToPair(AirportSparkFunctions.airportNamesKeyData);
        JavaPairRDD<Tuple2<Long, Long> ,FlightData> reducedFlightInfo = flightInfoPairRDD
                .reduceByKey(AirportSparkFunctions.airportFlightsUniqueKeyData);
        final Broadcast<Map<Long, String>> airportInfoBroadcasted = sc.broadcast(airportInfoPairRDD.collectAsMap());
        JavaPairRDD<String, String> result = reducedFlightInfo
                .mapToPair(AirportSparkFunctions.getAirportResultData(airportInfoBroadcasted));
        result.saveAsTextFile(args[2]);
    }
}
