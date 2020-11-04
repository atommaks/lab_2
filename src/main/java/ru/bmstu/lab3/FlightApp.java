package ru.bmstu.lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;


public class FlightApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: FlightApp <input file_flight_info> <input file_airport_info> <output path>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> flightInfoRDD = sc.textFile(args[0]);
        JavaRDD<String> airportInfoRDD = sc.textFile(args[1]);
        JavaPairRDD<Tuple2<Long, Long>, FlightData> flightInfoPairRDD = flightInfoRDD
                .filter(AirportSparkFunctions.airportFlightsFilterFunction)
                .mapToPair(AirportSparkFunctions.airportFlightsKeyData);
        JavaPairRDD<Long, String> airportInfoPairRDD =  airportInfoRDD
                .filter(AirportSparkFunctions.airportNamesFilterFunction)
                .mapToPair(AirportSparkFunctions.airportNamesKeyData);
        JavaPairRDD<Tuple2<Long, Long> ,FlightData> reducedFlightInfo = flightInfoPairRDD
                .reduceByKey(AirportSparkFunctions.airportFlightsUniqueKeyData);
        public static Function<String, Boolean> airportNamesFilterFunction = new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                if (airportNamesLinesFileCount == 0) {
                    airportNamesLinesFileCount++;
                    return false;
                }
                return true;
            }
        };
        JavaPairRDD<String, String> result = reducedFlightInfo
                .mapToPair(AirportSparkFunctions.getAirportResultData(sc.broadcast(airportInfoPairRDD.collectAsMap())));
        result.saveAsTextFile(args[2]);
    }
}
