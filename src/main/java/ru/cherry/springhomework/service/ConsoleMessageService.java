package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleMessageService implements ru.cherry.springhomework.service.MessageService {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return scanner.nextLine();
    }

    @Override
    public Long getLongMessage() {
        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }
}
