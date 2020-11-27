package ru.bmstu.lab4;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class TestingApp {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Testing");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        Server server = new Server(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = 
    }
}
