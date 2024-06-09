package com.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerClass {
    static AtomicInteger counter = new AtomicInteger();

    public static void increment() {
        counter.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                AtomicIntegerClass.increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter);
    }
}
