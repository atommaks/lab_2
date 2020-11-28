package ru.bmstu.lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.Patterns;
import akka.routing.BalancingPool;
import scala.concurrent.Future;

public class RouteActor extends AbstractActor {
    private final static int POOL_SIZE = 2;
    private ActorRef balanceActor;
    private ActorRef storageActor;

    public RouteActor () {}

    @Override
    public void preStart() {
        balanceActor = getContext().actorOf(new BalancingPool(POOL_SIZE).props(Props.create(RunActor.class)));
        storageActor = getContext().actorOf(Props.create(StoreActor.class));
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(RunMessage.class, msg -> balanceActor.tell(msg, self()))
                .match(StoreMessage.class, msg -> storageActor.tell(msg, self()))
                .match(ResultMessage.class, msg -> {
                    Future<Object> future = Patterns.ask()

                })
                .build();
    }
}
