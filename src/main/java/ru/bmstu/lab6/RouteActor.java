package ru.bmstu.lab6;

import akka.actor.AbstractActor;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RouteActor extends AbstractActor {
    private List<String> servers = Collections.emptyList();
    Random random = new Random();
    @Override
    public Receive createReceive() {
        return null;
    }
}
