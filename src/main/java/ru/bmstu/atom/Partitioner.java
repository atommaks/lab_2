package ru.bmstu.atom;

public class Partitioner<K, V> extends org.apache.hadoop.mapreduce.Partitioner<K, V> {
    @Override
    public int getPartition(K key, V value, int numPartitions) {
        return (key.hashCode() & Integer.MAX_VALUE) % numPartitions;
    }
}
