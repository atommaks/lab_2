package ru.bmstu.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class StoreActor extends AbstractActor {
    private HashMap<String, TestResults> storage;
    private static final String STORE_ACTOR_STORE_FORMAT = "[StoreActor] PackageID: %s, ";
    private static final String STORE_ACTOR_GET_FORMAT = "";

    public StoreActor() {
        storage = new HashMap<>();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> {
                    storage.put(m.getPackageID(), m.getResults());
                })
                .match(ResultMessage.class, m -> sender().tell(new StoreMessage(m.getPackageID(), storage.get(m.getPackageID())), self()))
                .build();
    }
}
