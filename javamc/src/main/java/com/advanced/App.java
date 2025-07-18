package com.advanced;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class App {
    public static void main(String[] args) {
        System.out.println("Main thread started");

        // Create an asynchronous task using CompletableFuture.
        // supplyAsync() runs the lambda in a separate thread (from
        // ForkJoinPool.commonPool()).
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Simulate a long-running task (2 seconds delay)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello from CompletableFuture!"; // This value will be passed to continuations
        });

        // Attach a continuation that will run when the future completes.
        // thenAccept() consumes the result but does not return a new value.
        future.thenAccept(result -> {
            System.out.println("Result: " + result);
        });

        // Chain another operation: convert the result to uppercase, then print it.
        // thenApply() transforms the result and passes it to the next stage.
        future.thenApply(result -> result.toUpperCase())
                .thenAccept(upper -> System.out.println("Uppercase result: " + upper));

        // Another chain: convert the result to a char array, then print it.
        future.thenApply(result -> result.toCharArray())
                .thenAccept(charArr -> System.out.println("char[] :" + Arrays.toString(charArr)));

        // Wait for the original asynchronous task to complete.
        // This keeps the main thread alive so async operations can finish.
        try {
            future.get(5, TimeUnit.SECONDS); // Wait up to 5 seconds for the result
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread ended");
    }
}
