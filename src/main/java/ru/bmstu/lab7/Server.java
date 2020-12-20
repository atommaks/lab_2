package ru.bmstu.lab7;

import org.apache.log4j.BasicConfigurator;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static ru.bmstu.lab7.Client.CLIENT_ADDRESS;

public class Server {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/logs/lab7.log";
    private static final int POLLER_SIZE = 2;
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

        
    }
}
