package com.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockClass {
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 3; i++) {
                increment();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
    }

    private static void increment() {
        lock.lock();
            Counter.count++;
            System.out.print(STR."\{Counter.count} ");
            //with lock -> 1 2 3 4 5 6 7 8 9
        lock.unlock();
    }

    static class Counter {
        static int count = 0;
    }
}
