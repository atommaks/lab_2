package ru.bmstu.lab4;

import akka.actor.AbstractActor;

import static akka.http.javadsl.server.Directives.*;

public class RouteActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return null;
    }

    public void createRoute() {
        route (
                path ( "execute", () ->
                        route (
                                
                        )

                )
        )
    }
}
