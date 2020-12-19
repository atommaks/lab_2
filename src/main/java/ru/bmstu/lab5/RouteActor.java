package ru.bmstu.lab5;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class RouteActor extends AbstractActor {
    private final HashMap<String, Integer> storage = new HashMap<>();

    public RouteActor () {}

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(GetMessage.class, msg -> getSender().tell(storage.getOrDefault(msg.getUrl(), -1), ActorRef.noSender()))
                .match(StoreMessage.class, msg -> storage.putIfAbsent(msg.getUrl(), msg.getTime()))
                .build();
    }
}
