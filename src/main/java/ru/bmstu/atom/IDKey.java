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
        return airportId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((airportId == null) ? 0 : airportId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        
    }
}
