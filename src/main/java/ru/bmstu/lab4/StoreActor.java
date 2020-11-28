package ru.bmstu.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class StoreActor extends AbstractActor {
    private HashMap<String, TestResults> storage;

    public StoreActor() {
        storage = new HashMap<>();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> {
                    storage.put(m.getPackageID(), m.getResults());
                })
                .match()
                .build();
    }
}
