package ru.bmstu.lab6;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.time.Duration;

public class ZooKeeperConn {
    private static final String HOST = "localhost:2181";
    private ZooKeeper keeper;

    public static Watcher watcher = watchedEvent -> {
        if (watchedEvent.getType() == Watcher.Event)
    };

    public ZooKeeperConn() throws IOException, KeeperException, InterruptedException {
        keeper = new ZooKeeper(HOST, (int)Duration.ofSeconds(5).getSeconds() * 1000, watcher);
        keeper.create("/servers/" + AnonymRequestsApp.PORT, (AnonymRequestsApp.PORT + "").getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        WatchedEvent event = new WatchedEvent(Watcher.Event.EventType.NodeCreated, Watcher.Event.KeeperState.SyncConnected, "");
        watcher.process(event);

    }
}