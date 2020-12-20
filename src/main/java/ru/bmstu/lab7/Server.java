package ru.bmstu.lab7;

import org.apache.log4j.BasicConfigurator;
import org.zeromq.ZMQ;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Server {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/logs/lab7.log";
    private static ZMQ.Socket


    public final static Logger LOGGER = Logger.getLogger("lab6");
    public final static String SERVER_ADDRESS = "tcp://localhost:1970";
    public static final int TIMEOUT = 5000;
    public final static String GET_CMD = "get";
    public final static String PUT_CMD = "put";
    public final static String NOTICE_CMD = "notice";

    public static void main(String[] args) throws Exception{
        BasicConfigurator.configure();
        FileHandler fh = new FileHandler(PATH_TO_LOG_FILE);
        LOGGER.addHandler(fh);
    }
}
