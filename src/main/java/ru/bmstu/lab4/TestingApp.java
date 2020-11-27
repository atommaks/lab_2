package ru.bmstu.lab4;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;

public class TestingApp {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Testing");
        final Http http = Http.get(system);
        final ActorMaterializer materializer =  
    }
}
