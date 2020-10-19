package ru.bmstu.atom;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupComparator extends WritableComparator {
    public GroupComparator() {
        super(IDKey.class, true);
    }

    @Override
    public int compare(WritableComparable wc1, WritableComparable wc2) {
        
    }
}
