package ru.bmstu.lab7;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;

import static ru.bmstu.lab7.Server.SERVER_ADDRESS;

public class Storage {
    private static final int TYPE = 1;
    private static final int POLLER_SIZE = 1;


    public static void main(String[] args) {
        ZContext context = new ZContext(TYPE);
        ZMQ.Socket socket = context.createSocket(SocketType.DEALER);
        socket.connect(SERVER_ADDRESS);
        ZMQ.Poller poller = context.createPoller(POLLER_SIZE);
        poller.register(socket, ZMQ.Poller.POLLIN);

        ArrayList
    }
}
