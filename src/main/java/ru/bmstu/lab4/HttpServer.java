package ru.bmstu.lab4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.util.concurrent.Future;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.entity;

public class HttpServer {
    private ActorSystem system;
    private ActorRef route;
    private static final String PACKAGE_TEST_START_FORMAT = "Testing package %s";
    private static final String RUN_SEGMENT = "run";
    private static final String RESULT_SEGMENT = "result";
    private static final String PACKAGE_ID_PARAMETR = "packageID";
    private static final 

    public HttpServer(ActorSystem system) {
        this.system = system;
        route = this.system.actorOf(Props.create(RouteActor.class));
    }

    public Route getRoute() {
        route (
                path (RUN_SEGMENT, () ->
                        route (
                                post(() ->
                                        entity(Jackson.unmarshaller(JsonFile.class), file -> {
                                            route.tell(new RunMessage(
                                                    file.getPackageId(),
                                                    file.getJsScript(),
                                                    file.getFunctionName(),
                                                    file.getTests()), ActorRef.noSender());
                                            return complete(StatusCodes.OK, String.format(PACKAGE_TEST_START_FORMAT, file.getPackageId()));
                                        })))),
                path (RESULT_SEGMENT, () ->
                        route(
                                parameter(PACKAGE_ID_PARAMETR, packageID -> {
                                    Future<Object> future = Patterns.ask(route, new ResultMessage(packageID))
                                })
                        )
                )
        );
    }
}
