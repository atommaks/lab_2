package ru.bmstu.lab6;

import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.route;

public class HttpServer {

    public HttpServer() {}

    public static Route createRoute() {
        return route(get())
    }
}
