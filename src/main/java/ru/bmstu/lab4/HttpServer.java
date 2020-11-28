package ru.bmstu.lab4;

import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.entity;

public class HttpServer {
    public HttpServer(ActorSystem system) {

    }

    public void createRoute() {
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
