package ru.bmstu.lab5;

public class StoreMessage {
    private String url;
    private Integer time;

    public StoreMessage(String url, Integer time) {
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
