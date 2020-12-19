package ru.bmstu.lab6;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import static akka.http.javadsl.server.Directives.*;

public class HttpServer {
    private static final String URL_ARG = "url";
    private static final String COUNT_ARG = "count";
    private Http http;
    ActorRef actor

    public HttpServer(Http http) {
        this.http = http;
    }

    public Route createRoute() {
        return route(get(() ->
                parameter(URL_ARG, url ->
                        parameter(COUNT_ARG, count -> {
                            if (Integer.parseInt(count) <= 0) {
                                return completeWithFuture(http.singleRequest(HttpRequest.create(url)));
                            }

                            return completeWithFuture(Patterns.ask())
                        }))));
    }
}
