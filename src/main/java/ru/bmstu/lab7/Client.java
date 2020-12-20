package ru.bmstu.lab7;


import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Scanner;

public class Client {
    private static final String CLIENT_ADDRESS = "tcp://localhost:1969";
    private static final int TIMEOUT = 5000;
    private static final int TYPE = 1;
    private static final Scanner in = new Scanner(System.in);
    private static ZContext context;
    private static ZMQ.Socket client;

    public static void main(String[] args) {
        context = new ZContext(TYPE);
        createAndConnectSocket();
        while (true) {

        }
        context.destroySocket(client);
        context.destroy();
    }

    private static void createAndConnectSocket() {
        ZMQ.Socket socket = context.createSocket(SocketType.REQ);
        socket.setReceiveTimeOut(TIMEOUT);
        socket.connect(CLIENT_ADDRESS);
        client = socket;
    }
}
