package ru.bmstu.lab7;


import org.apache.log4j.BasicConfigurator;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Scanner;
import java.util.logging.Logger;

public class Client {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/logs/lab6.log";
    private static final String CLIENT_ADDRESS = "tcp://localhost:1969";
    private static final String EXIT_CMD = "quit";
    private static final int TIMEOUT = 5000;
    private static final int TYPE = 1;
    private static final Scanner in = new Scanner(System.in);
    private static ZContext context;
    private static ZMQ.Socket client;
    public final static Logger LOGGER = Logger.getLogger("lab7");

    public static void main(String[] args) {
        BasicConfigurator.configure();
        context = new ZContext(TYPE);
        createAndConnectSocket();

        while (true) {
            String cmd = in.nextLine();
            if (cmd.equals(EXIT_CMD)) {
                break;
            }
            client.send(cmd);
            String output = client.recvStr();
            if (output != null) {

            }
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
