package ru.bmstu.lab6;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.time.Duration;

import static akka.http.javadsl.server.Directives.*;

public class HttpServer {
    private static final String URL_ARG = "url";
    private static final String COUNT_ARG = "count";
    private static final String URL_ADDRES_PTR = "http://%s:%s?url=%s&count=%d";
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private Http http;
    private ActorRef actor;

    public HttpServer(Http http, ActorRef actor) {
        this.http = http;
        this.actor = actor
    }

    public Route createRoute() {
        return route(get(() ->
                parameter(URL_ARG, url ->
                        parameter(COUNT_ARG, count -> {
                            if (Integer.parseInt(count) <= 0) {
                                return completeWithFuture(http.singleRequest(HttpRequest.create(url)));
                            }

                            return completeWithFuture(Patterns.ask(actor, new Server(url), TIMEOUT)
                            .thenApply(port -> (String)port)
                            .thenCompose(port -> http.singleRequest(HttpRequest.create(String.format(URL_ADDRES_PTR, AnonymRequestsApp.HOST, port, url, Integer.parseInt(count) - 1)))))
                        }))));
    }
}
