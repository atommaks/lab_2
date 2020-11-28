package ru.bmstu.lab4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.entity;

public class HttpServer {
    private ActorSystem system;
    private ActorRef route;

    public HttpServer(ActorSystem system) {
        this.system = system;
        route = this.system
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
