package ru.bmstu.lab5;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class CacheTestingApp {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/lab5.log";
    public final static Logger LOGGER = Logger.getLogger("lab5");

    public static void main(String[] args) throws Exception {
        FileHandler fh = new FileHandler(PATH_TO_LOG_FILE);
        LOGGER.addHandler(fh);

        LOGGER.info("start!");
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        //final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = ;

    }
}
