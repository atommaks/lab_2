package ru.bmstu.atom;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class IDKey implements WritableComparable<IDKey> {
    private Integer userId;
    private Text data;

    public IDKey(Integer userId, Text data) {
        super();
        this.userId = userId;
        this.data = data;
    }

    
}
