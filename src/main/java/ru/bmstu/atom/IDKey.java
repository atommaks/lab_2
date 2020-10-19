package ru.bmstu.atom;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IDKey implements WritableComparable<IDKey> {
    private Integer airportId;
    private Text data;

    public IDKey(Integer airportId, Text data) {
        super();
        this.airportId = airportId;
        this.data = data;
    }

    public Integer getAirportId() {
        return airport;
    }

    public Text getData() {
        return data;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(airportId);
        out.writeChars(data.toString());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        airportId = in.readInt();
        data = new Text(in.readLine());
    }
}
