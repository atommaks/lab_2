package ru.bmstu.atom;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IDKey implements WritableComparable<IDKey> {
    private Integer airportId;
    private boolean isFlight;
    private String data;

    public IDKey(Integer airportId, boolean isFlight, String data) {
        super();
        this.airportId = airportId;
        this.isFlight = isFlight;
        this.data = data;
    }

    public Integer getAirportId() {
        return airportId;
    }

    public String getData() {
        return data;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(airportId);
        out.writeBoolean(isFlight);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        airportId = in.readInt();
        isFlight = in.readBoolean();
    }


    @Override
    public int compareTo(IDKey obj) {
        return 0;
    }
}
