package ru.bmstu.lab5;

public class StoreMessage {
    private String url;
    private int time;

    public StoreMessage(String url, int time) {
        this.url = url;
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public int getTime() {
        return time;
    }
}
