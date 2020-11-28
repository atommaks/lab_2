package ru.bmstu.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class StoreActor extends AbstractActor {
    private HashMap<String, TestResults> storage;
    private static final String RUN_ACTOR_START_MSG_FORMAT = "PackageID: %s. Message: Starting running tests!\n";

    public StoreActor() {
        storage = new HashMap<>();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> {
                    TestingApp.LOGGER.info(String.format(RUN_ACTOR_START_MSG_FORMAT, m.getPackageID()));
                    storage.put(m.getPackageID(), m.getResults());
                })
                .match(ResultMessage.class, m -> sender().tell(new StoreMessage(m.getPackageID(), storage.get(m.getPackageID())), self()))
                .build();
    }
}
