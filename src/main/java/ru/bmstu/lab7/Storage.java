package ru.bmstu.lab7;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Arrays;

import static ru.bmstu.lab7.Server.SERVER_ADDRESS;

public class Storage {
    private static final int TYPE = 1;
    private static final int POLLER_SIZE = 1;
    private static final TIMEOUT =
    private static final int ZERO_POLL_INDEX = 0;


    public static void main(String[] args) {
        ZContext context = new ZContext(TYPE);
        ZMQ.Socket socket = context.createSocket(SocketType.DEALER);
        socket.connect(SERVER_ADDRESS);
        ZMQ.Poller poller = context.createPoller(POLLER_SIZE);
        poller.register(socket, ZMQ.Poller.POLLIN);

        ArrayList<String> caches = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
        long start = Integer.parseInt(args[0]);
        long finish = start + caches.size() - 1;
        long time = System.currentTimeMillis();

        while (poller.poll())
    }
}
