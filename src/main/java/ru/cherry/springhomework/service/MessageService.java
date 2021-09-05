package ru.cherry.springhomework.service;

public interface MessageService {
    void sendMessage(String message);
    String getMessage();
    Long getLongMessage();
}
