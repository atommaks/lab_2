package ru.bmstu.lab6;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.log4j.BasicConfigurator;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CompletionStage;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AnonymRequestsApp {
    private static final String PATH_TO_LOG_FILE = "/home/atom/IdeaProjects/lab_2/logs/lab6.log";
    private static final String ZOOKEEPER_HOST = "127.0.0.1:2181";
    public static final String HOST = "localhost";
    public static int PORT;
    public final static Logger LOGGER = Logger.getLogger("lab6");

    public static void main(String[] args) throws Exception{
        BasicConfigurator.configure();
        PORT = Integer.parseInt(args[0]);
        FileHandler fh = new FileHandler(PATH_TO_LOG_FILE);
        LOGGER.addHandler(fh);

        LOGGER.info("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef actor = system.actorOf(Props.create(RouteActor.class));
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        Watcher empty = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        };
        ZooKeeper zoo = new ZooKeeper(ZOOKEEPER_HOST, 2500, empty);
        final Http http = Http.get(system);
        ZooKeeperConn conn = new ZooKeeperConn(zoo, actor);
        conn.createConnection(HOST, String.valueOf(PORT));
        HttpServer server = new HttpServer(http, actor);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = server.createRoute().flow(system, materializer);
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