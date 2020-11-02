package ru.bmstu.lab3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class AirportSparkFunctions {
    PairFunction<String, LongWritable, Text> airportNamesKeyData = new PairFunction<String, LongWritable, Text>() {
        @Override
        public Tuple2<LongWritable, Text> call(String line) {
            String[] columns = StringTools.splitWithCommas(line);
            LongWritable airportCode = new LongWritable(Integer.parseInt(StringTools.removeQuotes(columns[AIRPORT_CODE_COLUMN_NUMBER])));
            Text airportName = new Text(StringTools.concatWords(columns, 1, columns.length));
            return new Tuple2<>(airportCode, airportName);
        }
    };
}
