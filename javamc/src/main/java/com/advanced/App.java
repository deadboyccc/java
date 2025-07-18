package com.advanced;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) {
        System.out.println("Main thread started");

        // Create an asynchronous task
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Simulate long task
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello from CompletableFuture!";
        });

        // Attach a continuation
        future.thenAccept(result -> {
            System.out.println("Result: " + result);
        });

        // You can also chain operations:
        future.thenApply(result -> result.toUpperCase())
                .thenAccept(upper -> System.out.println("Uppercase result: " + upper));

        future.thenApply(result -> result.toCharArray())
                .thenAccept(charArr -> System.out.println("char[] :" + Arrays.toString(charArr)));
        // Wait for all async operations to complete (main thread must stay alive)
        try {
            future.get(); // Wait for original task
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread ended");
    }
}
