package ru.bmstu.lab7;

import org.apache.log4j.BasicConfigurator;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static ru.bmstu.lab7.Client.CLIENT_ADDRESS;
import static ru.bmstu.lab7.Storage.ERROR_MSG_PTR;

public class Server {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/logs/lab7.log";
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

        }
    }

    private static void serverRunning() {

    }

    private static void executeGetCmd(ZMsg msg, String message) {

    }

    private static void executePutCmd(ZMsg msg, String message) {

    }
}
