package ru.bmstu.atom;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

public class IDKey implements WritableComparable<IDKey> {
    private Integer airportId;
    private boolean isFlight;
    private Text data;

    public IDKey(Integer airportId, boolean isFlight, Text data) {
        super();
        this.airportId = airportId;
        this.isFlight = isFlight;
        this.data = data;
    }

    public Integer getAirportId() {
        return airportId;
    }

    public Text getData() {
        return data;
    }

    @Override
    public void write(){

    }

    @Override
    public void readFields(){

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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IDKey other = (IDKey) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (airportId == null) {
            if (other.airportId != null) {
                return false;
            }
        } else if (!airportId.equals(other.airportId)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(IDKey obj) {

    }
}
