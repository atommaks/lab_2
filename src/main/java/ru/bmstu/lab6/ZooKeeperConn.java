package ru.bmstu.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static ru.bmstu.lab6.AnonymRequestsApp.LOGGER;

public class ZooKeeperConn implements Watcher {
    final private static String SERVERS = "/servers";
    private ZooKeeper zoo;
    private ActorRef storage;

    public ZooKeeperConn(ZooKeeper zoo, ActorRef storage) throws KeeperException, InterruptedException {
        this.zoo = zoo;
        this.storage = storage;
        sendServers();
    }

    private void sendServers() throws KeeperException, InterruptedException {
        List<String> servers = zoo.getChildren(SERVERS, this);
        storage.tell(new ServersList(servers), ActorRef.noSender());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        LOGGER.info(watchedEvent.toString());
        try {
            sendServers();
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createConnection(String localhost, String port) throws KeeperException, InterruptedException {
        zoo.create(SERVERS + "/" + localhost + ":" + port,
                port.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
        storage.tell(new Server(localhost + ":" + port), ActorRef.noSender());
    }

}