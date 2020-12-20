package ru.bmstu.lab6;

import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.time.Duration;

import static akka.http.javadsl.server.Directives.*;
import static ru.bmstu.lab6.AnonymRequestsApp.LOGGER;
import static ru.bmstu.lab6.AnonymRequestsApp.HOST;

public class HttpServer {
    private static final String URL_ARG = "url";
    private static final String COUNT_ARG = "count";
    private static final String URL_ADDRES_PTR = "http://%s:%s?url=%s&count=%d";
    private static final String SEND_REQUEST_PTR = "Sending request to http://%s:%s?url=%s&count=%d";
    private static final String FINISH_REQUEST_PTR = "Finished with %s";
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private Http http;
    private ActorRef actor;

    public HttpServer(Http http, ActorRef actor) {
        this.http = http;
        this.actor = actor;
    }

    public Route createRoute() {
        return route(get(() ->
                parameter(URL_ARG, url ->
                        parameter(COUNT_ARG, count -> {
                            if (Integer.parseInt(count) <= 0) {
                                LOGGER.info(String.format(FINISH_REQUEST_PTR, url));
                                return completeWithFuture(http.singleRequest(HttpRequest.create(url)));
                            }

                            return completeWithFuture(Patterns.ask(actor, new Server(url), TIMEOUT)
                            .thenApply(port -> (String)port)
                            .thenCompose(port -> {
                                LOGGER.info(String.format(SEND_REQUEST_PTR, HOST, port, url, Integer.parseInt(count) - 1));
                                return http.singleRequest(HttpRequest.create(String.format(URL_ADDRES_PTR, HOST, port, url, Integer.parseInt(count) - 1)));
                            }));
                        }))));
    }
}
