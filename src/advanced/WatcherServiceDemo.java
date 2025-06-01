package advanced;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Demonstrates usage of Java's WatchService API to monitor a directory for file changes.
 * The service watches for file creation, modification, and deletion events.
 * Shuts down when "Testing.txt" is deleted.
 */
public class WatcherServiceDemo {
    public static void main(String[] args) {
        // Try-with-resources ensures WatchService is closed automatically
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path directory = Paths.get("."); // Watch current directory

            // Register directory with the watch service for create, modify, and delete events
            directory.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE
            );

            // Event processing loop
            while (true) {
                WatchKey watchKey;
                try {
                    // Wait for a watch key to be available (blocking)
                    watchKey = watchService.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                // Process all events for the watch key
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path context = (Path) event.context();

                    System.out.printf("Event type: %s ---- context: %s%n", kind.name(), context);

                    // If "Testing.txt" is deleted, shut down the watcher
                    if ("Testing.txt".equals(context.getFileName().toString())
                            && kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("WatchService is shutting down");
                        return;
                    }
                }

                // Reset the key to receive further event notifications
                boolean valid = watchKey.reset();
                if (!valid) {
                    break; // Directory is inaccessible
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
