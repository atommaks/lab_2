package ru.bmstu.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class RunActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(RunMessage.class, m -> {
                    TestResults results = new TestResults();
                    results.runTests(m);
                    sender().tell(new StoreMessage(m.getPackageId(), m.getFunctionName(), results), self());
                })
                .build();
    }
}