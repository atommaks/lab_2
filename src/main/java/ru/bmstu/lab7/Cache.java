package ru.bmstu.lab7;

import org.zeromq.ZFrame;

public class Cache {
    private String id;
    private long start;
    private long finish;
    private long time;
    private ZFrame frame;

    public Cache(String id, long start, long finish, ZFrame frame) {
        this.id = id;
        this.start = start;
        this.finish = finish;
        this.frame = frame;
        this.time = System

    }
}
