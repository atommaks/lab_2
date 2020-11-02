package ru.bmstu.lab3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class FlightApp {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 0;
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

        PairFunction<String, LongWritable, Text> airportNamesKeyData = new PairFunction<String, LongWritable, Text>() {
            @Override
            public Tuple2<LongWritable, Text> call(String line) {
                String[] columns = StringTools.splitWithCommas(line);
                LongWritable airportCode = new LongWritable(Integer.parseInt(StringTools.removeQuotes(columns[AIRPORT_CODE_COLUMN_NUMBER])));
                Text airportName = new Text(StringTools.concatWords(columns, 1, columns.length));
                return new Tuple2<>(airportCode, airportName);
            }
        };

        PairFunction<String, Tuple2<LongWritable, LongWritable>, FlightData> airportFlightsKeyData = new PairFunction<String, Tuple2<LongWritable, LongWritable>, FlightData>() {
            @Override
            public Tuple2<Tuple2<LongWritable, LongWritable>, FlightData> call(String line) {
                String[] columns = StringTools.splitWithCommas(line);
                Integer originAirportCode = Integer.parseInt(StringTools.removeQuotes(columns[ORIGIN_AIRPORT_COLUMN_NUMBER]));
                Integer destAirportCode = Integer.parseInt(StringTools.removeQuotes(columns[DEST_AIRPORT_COLUMN_NUMBER]));
                String delay = columns[DELAY_COLUMN_NUMBER];
                if (!delay.isEmpty()) {
                    return new Tuple2<>(new Tuple2<>(new LongWritable(originAirportCode), new LongWritable(destAirportCode)),
                            new FlightData(Float.parseFloat(delay), NOT_ABORTED_FLIGHT_FLAG));
                }

                return new Tuple2<>(new Tuple2<>(new LongWritable(originAirportCode), new LongWritable(destAirportCode)),
                        new FlightData(Float.parseFloat(delay), ABORTED_FLIGHT_FLAG));
            }
        };

        Function2<FlightData, FlightData, FlightData> airportFlightsUniqueKeyData = new Function2<FlightData, FlightData, FlightData>() {
            @Override
            public FlightData call(FlightData fd1, FlightData fd2){
                float d1 = fd1.getDelay(), d2 = fd2.getDelay();
                float newDelay = d1 > d2 ? d1 : d2;
                int afc1 = fd1.getAbortedFlightCount(), afc2 = fd2.getAbortedFlightCount();
                int dfc1 = fd1.getDelayedFlightCount(), dfc2 = fd2.getDelayedFlightCount();
                int fc1 = fd1.getFlightCount(), fc2 = fd2.getFlightCount();

                return new FlightData(newDelay, afc1 + afc2, dfc1 + dfc2, fc1 + fc2);
            }
        };

        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> flightInfoRDD = sc.textFile(args[0]);
        JavaRDD<String> airportInfoRDD = sc.textFile(args[1]);
        JavaPairRDD<Tuple2<LongWritable, LongWritable>, FlightData> flightInfoPairRDD = flightInfoRDD.mapToPair(airportFlightsKeyData);
        JavaPairRDD<LongWritable, Text> airportInfoPairRDD = airportInfoRDD.mapToPair(airportNamesKeyData);
        JavaPairRDD<Tuple2<LongWritable, LongWritable> ,FlightData> reducedFlightInfo = flightInfoPairRDD.reduceByKey(airportFlightsUniqueKeyData);
        
    }
}
