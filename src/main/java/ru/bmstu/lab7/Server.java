package ru.bmstu.lab7;

import org.apache.log4j.BasicConfigurator;
import org.zeromq.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static ru.bmstu.lab7.Client.CLIENT_ADDRESS;
import static ru.bmstu.lab7.Storage.*;

public class Server {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/logs/lab7.log";
    private static final String PUT_SUCCESS_MSG = "PUT Succeded";
    private static final String NOT_EXISTING_CMD = "Such command doesn't exist";
    private static final String NOT_EXISTIG_ITEM = "Not existing!";
    private static final int POLLER_SIZE = 2;
    private static final int CLIENT_SOCKET = 0;
    private static final int SERVER_SOCKET = 1;
    private static ZMQ.Socket clientSocket;
    private static ZMQ.Socket serverSocket;
    private static ZMQ.Poller poller;
    private static ArrayList<Cache> caches;

    public final static Logger LOGGER = Logger.getLogger("lab6");
    public final static String SERVER_ADDRESS = "tcp://localhost:1970";
    public final static int TIMEOUT = 5000;
    public final static int TYPE = 1;
    public final static String GET_CMD = "get";
    public final static String PUT_CMD = "put";
    public final static String NOTICE_CMD = "notice";

    public static void main(String[] args) throws Exception{
        BasicConfigurator.configure();
        FileHandler fh = new FileHandler(PATH_TO_LOG_FILE);
        LOGGER.addHandler(fh);

        ZContext context = new ZContext(TYPE);
        clientSocket = context.createSocket(SocketType.ROUTER);
        clientSocket.bind(CLIENT_ADDRESS);

        serverSocket = context.createSocket(SocketType.ROUTER);
        serverSocket.bind(SERVER_ADDRESS);

        poller = context.createPoller(POLLER_SIZE);
        poller.register(clientSocket, ZMQ.Poller.POLLIN);
        poller.register(serverSocket, ZMQ.Poller.POLLIN);

        caches = new ArrayList<>();

        long time = System.currentTimeMillis();
        while (poller.poll(TIMEOUT) != -1) {
            if (System.currentTimeMillis() - time >= TIMEOUT) {
                Collections.shuffle(caches);
                time = System.currentTimeMillis();
            }

            if (poller.pollin(CLIENT_SOCKET)) {
                clientRunning();
            }

            if (poller.pollin(SERVER_SOCKET)) {
                serverRunning();
            }
        }

        context.destroySocket(clientSocket);
        context.destroySocket(serverSocket);
        context.destroy();
    }

    private static void clientRunning() {
        ZMsg msg = ZMsg.recvMsg(clientSocket);
        String message = msg.getLast().toString().toLowerCase(Locale.ROOT);
        if (message.startsWith(GET_CMD)) {
            try {
                executeGetCmd(msg, message);
            } catch (Exception e) {
                msg.getLast().reset(String.format(ERROR_MSG_PTR, e.getMessage()));
                msg.send(clientSocket);
            }
        } else if (message.startsWith(PUT_CMD)) {
            executePutCmd(msg, message);
            msg.getLast().reset(PUT_SUCCESS_MSG);
            msg.send(clientSocket);
        } else {
            msg.getLast().reset(NOT_EXISTING_CMD);
            msg.send(clientSocket);
        }
    }

    private static void serverRunning() {
        ZMsg msg = ZMsg.recvMsg(serverSocket);
        ZFrame frame = msg.unwrap();
        String message = msg.getLast().toString().toLowerCase(Locale.ROOT);
        if (message.startsWith(NOTICE_CMD)) {
            executeNoticeCmd(message, frame);
        } else {
            msg.send(clientSocket);
        }
    }

    private static void executeNoticeCmd(String msg, ZFrame frame) {
        String[] split = msg.split(DELIMITER);
        String id = split[1];
        long start = Integer.parseInt(split[2]);
        long finish = Integer.parseInt(split[3]);
        for (int i = 0; i < caches.size() + 1; i++) {
            if (i == caches.size()) {
                caches.add(new Cache(id, start, finish, frame));
            } else if (caches.get(i).getId().equals(id)) {
                caches.get(i).setStart(start);
                caches.get(i).setFinish(finish);
                caches.get(i).setTime(System.currentTimeMillis());
            }
        }
    }

    private static void executeGetCmd(ZMsg msg, String message) {
        long key = Integer.parseInt(message.split(DELIMITER)[KEY_INDEX]);
        boolean doesExist = false;
        for (Cache cache : caches) {
            boolean isFresh = System.currentTimeMillis() - cache.getTime() <= TIMEOUT;
            if (cache.getStart() <= key && cache.getFinish() >= key && isFresh) {
                cache.getFrame().send(serverSocket, ZFrame.REUSE | ZFrame.MORE);
                msg.send(serverSocket, false);
                doesExist = true;
                break;
            }
        }
        if (!doesExist) {
            msg.getLast().reset(NOT_EXISTIG_ITEM);
            msg.send(clientSocket);
        }
    }

    private static void executePutCmd(ZMsg msg, String message) {
        String[] split = message.split(DELIMITER);
        long key = Integer.parseInt(split[KEY_INDEX]);
        for (Cache cache : caches) {
            if (cache.getStart() <= key && cache.getFinish() >= key) {
                cache.getFrame().send(serverSocket, ZFrame.REUSE | ZFrame.MORE);
                msg.send(serverSocket, false);
            }
        }
    }
}
