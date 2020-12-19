package ru.bmstu.lab6;

import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.route;

public class HttpServer {
    private static final String URL_ARG = "url";
    private static final String COUNT_ARG = "count";

    public HttpServer() {}

    public static Route createRoute() {
        return route(get(() ->
                
                ));
    }
}
