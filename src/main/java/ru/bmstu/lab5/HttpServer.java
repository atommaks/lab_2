package ru.bmstu.lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class HttpServer {

    public static Flow<HttpRequest, HttpResponse, NotUsed> createFlow(Http http, ActorSystem system,
                                                                      ActorMaterializer materializer, ActorRef actor) {
        return Flow.of(HttpRequest.class)
                .map((r) -> {
                    
                })
    }
}
