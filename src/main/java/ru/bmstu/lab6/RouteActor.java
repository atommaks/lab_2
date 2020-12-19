package ru.bmstu.lab6;

import akka.actor.AbstractActor;

import java.util.Collections;
import java.util.List;

public class RouteActor extends AbstractActor {
    private List<String> servers = Collections.emptyList();
    
    @Override
    public Receive createReceive() {
        return null;
    }
}
