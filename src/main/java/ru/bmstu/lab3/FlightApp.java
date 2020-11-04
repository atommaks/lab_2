package ru.bmstu.lab3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;


public class FlightApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: FlightApp <input file_flight_info> <input file_airport_info> <output path>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<LongWritable, Text> flightInfoRDD = sc.hadoopFile(args[0], TextInputFormat.class, LongWritable.class, Text.class);
        //JavaRDD<String> flightInfoRDD = sc.textFile(args[0]);
        JavaRDD<String> airportInfoRDD = sc.textFile(args[1]);
        JavaPairRDD<Tuple2<LongWritable, LongWritable>, FlightData> flightInfoPairRDD =
                flightInfoRDD.mapToPair(AirportSparkFunctions.airportFlightsKeyData);
        JavaPairRDD<LongWritable, Text> airportInfoPairRDD =
                airportInfoRDD.mapToPair(AirportSparkFunctions.airportNamesKeyData);
        JavaPairRDD<Tuple2<LongWritable, LongWritable> ,FlightData> reducedFlightInfo =
                flightInfoPairRDD.reduceByKey(AirportSparkFunctions.airportFlightsUniqueKeyData);
        JavaPairRDD<String, String> result =
                reducedFlightInfo.mapToPair(AirportSparkFunctions.getAirportResultData(sc.broadcast(airportInfoPairRDD.collectAsMap())));
        result.saveAsTextFile(args[2]);
    }
}
