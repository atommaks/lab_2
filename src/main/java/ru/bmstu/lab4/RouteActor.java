package ru.bmstu.lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.BalancingPool;

public class RouteActor extends AbstractActor {
    private final static int POOL_SIZE = 2;
    private ActorRef balanceActor;

    public RouteActor () {}

    @Override
    public void preStart() {
        balanceActor = getContext().actorOf(new BalancingPool(POOL_SIZE).props(Props.create(RunActor.class)));
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(RunMessage.class, msg -> balanceActor.tell())
    }
}
