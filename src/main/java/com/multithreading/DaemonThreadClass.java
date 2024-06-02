package com.multithreading;

/**
 * Daemon threads are designed to run in the background, performing tasks such as garbage
 * collection, monitoring, or other auxiliary functions that do not require immediate attention.
 * The JVM does not wait for daemon threads to finish before it terminates. When all user
 * (non-daemon) threads have completed, the JVM exits, even if there are daemon threads still running.
 * Daemon threads are terminated abruptly when the JVM shuts down. They do not get a chance to complete
 * their tasks or perform any cleanup operations.
 * Commonly used for services that should not block JVM termination, like periodic clean-ups, monitoring
 * services, or background tasks.*/

public class DaemonThreadClass {
    public static void main(String[] args) {
        Thread userThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("User thread is running...");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread daemonThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Daemon thread is running...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        daemonThread.setDaemon(true);
        userThread.start();
        daemonThread.start();

        try {
            userThread.join(); // Wait for the user thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread is finishing...");
    }
}
