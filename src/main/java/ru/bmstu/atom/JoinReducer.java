package ru.bmstu.atom;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class JoinReducer extends Reducer<IDKey, String, Text, Text> {
    @Override
    protected void reduce(IDKey key, Iterable<String> values, Context context) throws IOException, InterruptedException {
        Iterator<String> iter = values.iterator();
        String name = iter.next();
        float min = Float.MAX_VALUE, max = Float.MIN_VALUE, avg = (float) 0.0;
        long count = 0;
        while (iter.hasNext()) {
            float currentTime = Float.parseFloat(iter.next());
            if (currentTime < min) {
                min = currentTime;
            }
            if (currentTime > max) {
                max = currentTime;
            }
            avg = avg * count + currentTime;
            count++;
            avg /= (float) count;
        }
        context.write(new Text(key.getAirportId().toString()), );
    }
}
