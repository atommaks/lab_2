package ru.bmstu.lab6;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.stream.ActorMaterializer;

import java.util.concurrent.CompletionStage;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AnonymRequestsApp {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/lab6.log";
    private static final String HOST = "localhost";
    private static int PORT;
    public final static Logger LOGGER = Logger.getLogger("lab6");

    public static void main(String[] args) throws Exception{
        FileHandler fh = new FileHandler(PATH_TO_LOG_FILE);
        LOGGER.addHandler(fh);

        LOGGER.info("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef actor = system.actorOf(Props.create(RouteActor.class));
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(HOST, PORT),
                materializer
        );
        LOGGER.info("Server online at http://" + HOST + ":" + PORT + "\n Press any button to stop...");
        System.in.read();
        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }
}
