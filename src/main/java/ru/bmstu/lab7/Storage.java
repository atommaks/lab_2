package ru.bmstu.lab7;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import static ru.bmstu.lab7.Server.SERVER_ADDRESS;
import static ru.bmstu.lab7.Server.TIMEOUT;
import static ru.bmstu.lab7.Server.GET_CMD;

public class Storage {
    private static final int TYPE = 1;
    private static final int POLLER_SIZE = 1;
    private final static long NOTICE_TIME = 1000;
    private final static String NOTICE_MSG_PTR = "notice id:%s; %d -> %d";
    private final static String id = UUID.randomUUID().toString();
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

        while (poller.poll(TIMEOUT) != -1) {
            long res = System.currentTimeMillis() - time;
            if (res >= NOTICE_TIME) {
                socket.send(String.format(NOTICE_MSG_PTR, id, start, finish));
                time = System.currentTimeMillis();
            }
            if (poller.pollin(ZERO_POLL_INDEX)) {
                ZMsg msg = ZMsg.recvMsg(socket);
                String message = msg.getLast().toString().toLowerCase(Locale.ROOT);
                if (message.equals(GET_CMD))
            }
        }
    }

    private static void executeGetCmd(ZMsg msg, String message, ArrayList<>)
}
