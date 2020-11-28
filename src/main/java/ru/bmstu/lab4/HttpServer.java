package ru.bmstu.lab4;

import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.entity;

public class HttpServer {
    public HttpServer(ActorSystem system) {

    }

    public Route getRoute() {
        route (
                path ( "run", () ->
                        route (
                                post(() ->
                                        entity(Jackson.unmarshaller(JsonFile.class), file -> {
                                            
                                        })
                                )
                        )

                )
        )
    }
}
