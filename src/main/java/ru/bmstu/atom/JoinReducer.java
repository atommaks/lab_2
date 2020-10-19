package ru.bmstu.atom;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class JoinReducer extends Reducer<IDKey, Text, Text, Text> {
    @Override
    protected void reduce(IDKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Iterator<Text> iter = values.iterator();
        String name = iter.next().toString();
        System.out.println(name);
        float min = Float.MAX_VALUE, max = (float) 0.0, avg = (float) 0.0;
        long count = 0;
        while (iter.hasNext()) {
            float currentTime = Float.parseFloat(iter.next().toString());
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
        if (count > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("Name: ");
            builder.append(name);
            builder.append(", min: ");
            builder.append(min);
            builder.append(", max: ");
            builder.append(max);
            builder.append(", avg: ");
            builder.append(avg);
            //String res = "Name: " + name + ", min: " + min + ", max: " + max + ", avg: " + avg;
            System.out.println(builder.toString());
            context.write(new Text(key.getAirportId().toString()), new Text(builder.toString()));
        }
    }
}
