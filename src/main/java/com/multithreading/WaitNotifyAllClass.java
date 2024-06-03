package com.multithreading;

import lombok.AllArgsConstructor;

public class WaitNotifyAllClass {
    public static void main(String[] args) throws InterruptedException {
        Market2 market = new Market2();
        Thread consumer1 = new Thread(new Consumer2(market), "Consumer1");
        Thread consumer2 = new Thread(new Consumer2(market), "Consumer2");
        Thread producer = new Thread(new Producer2(market), "Producer");
        consumer1.start();
        consumer2.start();
        producer.start();

        // Joining threads to ensure main thread waits for their completion
        consumer1.join();
        consumer2.join();
        producer.join();
    }
}

@AllArgsConstructor
class Consumer2 implements Runnable {
    private final Market2 market;

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            try {
                market.getBread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

@AllArgsConstructor
class Producer2 implements Runnable {
    private final Market2 market;

    @Override
    public void run() {
        for (int i = 0; i < 8; i++) {
            try {
                market.putBread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Market2 {
    private int breadCount = 0;

    synchronized void getBread() throws InterruptedException {
        while (breadCount < 1) {
            wait();
        }
        breadCount--;
        System.out.println(STR."""
                \{Thread.currentThread().getName()} bought a bread
                Quantity of bread in the market is \{breadCount}
                """);
        notifyAll();
    }

    synchronized void putBread() throws InterruptedException {
        while (breadCount >= 4) {
            wait();
        }
        breadCount++;
        System.out.println(STR."""
                Producer produced one more bread
                Quantity of bread in the market is \{breadCount}
                """);
        notifyAll();
    }
}

