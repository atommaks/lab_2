package ru.bmstu.lab3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

import static ru.bmstu.lab3.AirportSparkFunctions.*;

public class FlightApp {
    private static final int ORIGIN_AIRPORT_COLUMN_NUMBER = 11;
    private static final int DEST_AIRPORT_COLUMN_NUMBER = 14;
    private static final int DELAY_COLUMN_NUMBER = 18;
    private static final boolean ABORTED_FLIGHT_FLAG = true;
    private static final boolean NOT_ABORTED_FLIGHT_FLAG = false;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: FlightApp <input file_flight_info> <input file_airport_info> <output path>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> flightInfoRDD = sc.textFile(args[0]);
        JavaRDD<String> airportInfoRDD = sc.textFile(args[1]);
        JavaPairRDD<Tuple2<LongWritable, LongWritable>, FlightData> flightInfoPairRDD = flightInfoRDD.mapToPair(airportFlightsKeyData);
        JavaPairRDD<LongWritable, Text> airportInfoPairRDD = airportInfoRDD.mapToPair(airportNamesKeyData);
        JavaPairRDD<Tuple2<LongWritable, LongWritable> ,FlightData> reducedFlightInfo = flightInfoPairRDD.reduceByKey(airportFlightsUniqueKeyData);
        Broadcast<Map<LongWritable, Text>> airportInfoBroadcasted = sc.broadcast(airportInfoPairRDD.collectAsMap());

        PairFunction<Tuple2<Tuple2<LongWritable, LongWritable>, FlightData>, String, String> airportResultData = new PairFunction<Tuple2<Tuple2<LongWritable, LongWritable>, FlightData>, String, String>() {
            @Override
            public Tuple2<String, String> call(Tuple2<Tuple2<LongWritable, LongWritable>, FlightData> e) {
                String originName = airportInfoBroadcasted.value().get(e._1._1).toString();
                String destName = airportInfoBroadcasted.value().get(e._1._2).toString();
                String key = originName + " -> " + destName;
                String value = String.format("Delay: %f, Ratio: %.2f%%", e._2.getDelay(), e._2.getRatio());

                return new Tuple2<>(key, value);
            }
        };

        JavaPairRDD<String, String> result = reducedFlightInfo.mapToPair(airportResultData);
        result.saveAsTextFile(args[2]);
    }
}
