package ru.bmstu.lab7;


import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {
    private static final String CLIENT_ADDRESS = "tcp://localhost:1969";
    private static final int TIMEOUT = 5000;
    private static ZContext context;
    private static ZMQ.Socket client;

    public static void main(String[] args) {

    }
}
