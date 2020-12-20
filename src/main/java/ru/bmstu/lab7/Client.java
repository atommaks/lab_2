package ru.bmstu.lab7;


import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Scanner;

import static ru.bmstu.lab7.Server.LOGGER;

public class Client {
    private static final String CLIENT_ADDRESS = "tcp://localhost:1969";
    private static final String EXIT_CMD = "quit";
    private static final String OUTPUT_MSG_PTR = "[Output]: %s\n";
    private static final String NO_OUTPUT_MSG = "No output!\n";
    private static final int TIMEOUT = 5000;
    private static final int TYPE = 1;
    private static final Scanner in = new Scanner(System.in);
    private static ZContext context;
    private static ZMQ.Socket client;

    public static void main(String[] args) {
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
                LOGGER.info(java.lang.String.format(OUTPUT_MSG_PTR, output));
            } else {
                LOGGER.warning(NO_OUTPUT_MSG);
                context.destroySocket();
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
