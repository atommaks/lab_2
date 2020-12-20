package ru.bmstu.lab7;

import org.apache.log4j.BasicConfigurator;

import java.util.logging.Logger;

public class Server {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/logs/lab7.log";
    public final static Logger LOGGER = Logger.getLogger("lab6");

    public static void main(String[] args) {
        BasicConfigurator.configure();
    }
}
