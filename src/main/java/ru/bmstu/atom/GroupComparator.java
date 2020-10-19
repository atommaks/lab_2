package ru.bmstu.atom;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupComparator extends WritableComparator {
    public GroupComparator() {
        super(IDKey.class, true);
    }

    @Override
    public int compare(WritableComparable wc1, WritableComparable wc2) {
        IDKey key1 = (IDKey)wc1;
        IDKey key2 = (IDKey)wc2;
        return 
    }
}
