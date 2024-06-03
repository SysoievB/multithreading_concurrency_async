package com.multithreading;

import lombok.AllArgsConstructor;

public class WaitNotifyWithLockClass {
    public static void main(String[] args) throws InterruptedException {
        Market1 market = new Market1();
        Thread consumer = new Thread(new Consumer1(market));
        Thread producer = new Thread(new Producer1(market));
        consumer.start();
        producer.start();
    }
}

@AllArgsConstructor
class Consumer1 implements Runnable {
    private final Market1 market;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                market.getBread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

@AllArgsConstructor
class Producer1 implements Runnable {
    private final Market1 market;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                market.putBread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Market1 {
    private static final Object LOCK = new Object();
    private int breadCount = 0;

    void getBread() throws InterruptedException {
        synchronized (LOCK) {
            while (breadCount < 1) {
                LOCK.wait();
            }
            breadCount--;
            System.out.println(STR."""
                Customer bought a bread
                Quantity of bread in the market is \{breadCount}
                """);
            LOCK.notify();
        }
    }

    synchronized void putBread() throws InterruptedException {
        synchronized (LOCK) {
            while (breadCount >= 5) {
                LOCK.wait();
            }
            breadCount++;
            System.out.println(STR."""
                Producer produced one more bread
                Quantity of bread in the market is \{breadCount}
                """);
            LOCK.notify();
        }
    }
}

