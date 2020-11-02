package ru.bmstu.lab3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class FlightApp {
    private static final int AIRPORT_CODE_COLUMN_NUMBER = 0;

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
                
            }
        };

        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> flightInfoRDD = sc.textFile(args[0]);
        JavaRDD<String> airportInfoRDD = sc.textFile(args[1]);
        flightInfoRDD.mapToPair(airportFlightsKeyData);
        airportInfoRDD.mapToPair(airportNamesKeyData);

    }
}
