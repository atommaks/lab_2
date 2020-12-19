package ru.bmstu.lab6;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RouteActor extends AbstractActor {
    private List<String> servers = Collections.emptyList();
    private Random random = new Random();

    public RouteActor() {}

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(ServersList.class, msg -> servers = msg.getServers())
                .match(Server.class, msg -> getSender().tell(servers.get(random.nextInt(servers.size())), ActorRef.noSender()))
    }
}
