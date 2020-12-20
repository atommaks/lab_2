package ru.bmstu.lab6;

import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class ZooKeeperConn {
    private static final String HOST = "localhost:2181";
    private static ZooKeeper keeper;
    private static ActorRef actor;

    public static Watcher watcher = watchedEvent -> {
        if (watchedEvent.getType() == Watcher.Event.EventType.NodeCreated ||
                watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted ||
                watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged) {
            List<String> servers = Collections.emptyList();
            try {
                for (String s : keeper.getChildren("/servers", null)) {
                    byte[] port = keeper.getData("/servers/" + s, false, null);
                    servers.add(new String(port));
                }
                actor.tell(new ServersList(servers));

            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public ZooKeeperConn(ActorRef actor) throws IOException, KeeperException, InterruptedException {
        this.actor = actor;
        keeper = new ZooKeeper(HOST, (int)Duration.ofSeconds(5).getSeconds() * 1000, watcher);
        keeper.create("/servers/" + AnonymRequestsApp.PORT, (AnonymRequestsApp.PORT + "").getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        WatchedEvent event = new WatchedEvent(Watcher.Event.EventType.NodeCreated, Watcher.Event.KeeperState.SyncConnected, "");
        watcher.process(event);
    }
}