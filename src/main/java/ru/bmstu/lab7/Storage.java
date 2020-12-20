package ru.bmstu.lab7;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Storage {
    private static final int TYPE = 1;

    public static void main(String[] args) {
        ZContext context = new ZContext(TYPE);
        ZMQ.Socket socket = context.createSocket()
    }
}
