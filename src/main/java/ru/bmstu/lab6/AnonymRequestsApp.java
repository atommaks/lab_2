package ru.bmstu.lab6;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import ru.bmstu.lab5.RouteActor;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AnonymRequestsApp {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/lab6.log";
    public final static Logger LOGGER = Logger.getLogger("lab6");

    public static void main(String[] args) throws Exception{
        FileHandler fh = new FileHandler(PATH_TO_LOG_FILE);
        LOGGER.addHandler(fh);

        LOGGER.info("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef actor = system.actorOf(Props.create(RouteActor.class));
        final Http http = Http.get(system);
    }
}
