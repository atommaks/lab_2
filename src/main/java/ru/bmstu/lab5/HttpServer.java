package ru.bmstu.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import static ru.bmstu.lab5.CacheTestingApp.LOGGER;

public class HttpServer {
    private static final String URL_ARG = "testUrl";
    private static final String COUNT_ARG = "count";
    private static final String INFO_MSG_PTR = "%s %s";


    public static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(Http http, ActorSystem system,
                                                                      ActorMaterializer materializer, ActorRef actor) {
        return Flow.of(HttpRequest.class)
                .map((r) -> {
                    Query query = r.getUri().query();
                    String url = query.get(URL_ARG).get();
                    int count = Integer.parseInt(query.get(COUNT_ARG).get());
                    LOGGER.info();
                })
                .mapAsync()
    }
}
