package ru.bmstu.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class RunActor extends AbstractActor {
    private static final String RUN_ACTOR_START_MSG_FORMAT = "PackageID: %s. Message: Starting running tests!\n";
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(RunMessage.class, m -> {
                    TestingApp.LOGGER.info(String.format(RUN_ACTOR_START_MSG_FORMAT, m.getPackageId()));
                    TestResults results = new TestResults();
                    results.runTests(m);
                    sender().tell(new StoreMessage(m.getPackageId(), results), self());
                })
                .build();
    }
}