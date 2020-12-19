package ru.bmstu.lab6;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AnonymRequestsApp {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/lab5.log";
    public final static Logger LOGGER = Logger.getLogger("lab6");

    public static void main(String[] args) throws Exception{
        FileHandler fh = new FileHandler(PATH_TO_LOG_FILE);
        LOGGER.addHandler(fh);

    }
}
