package com.multithreading;

import lombok.AllArgsConstructor;

public class WaitNotifyClass {
    public static void main(String[] args) throws InterruptedException {
        Market market = new Market();
        Thread consumer = new Thread(new Consumer(market));
        Thread producer = new Thread(new Producer(market));
        consumer.start();
        producer.start();
    }
}

@AllArgsConstructor
class Consumer implements Runnable {
    private final Market market;

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
class Producer implements Runnable {
    private final Market market;

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

class Market {
    private int breadCount = 0;

    synchronized void getBread() throws InterruptedException {
        while (breadCount < 1) {
            wait();
        }
        breadCount--;
        System.out.println(STR."""
                Customer bought a bread
                Quantity of bread in the market is \{breadCount}
                """);
        notify();
    }

    synchronized void putBread() throws InterruptedException {
        while (breadCount >= 5) {
            wait();
        }
        breadCount++;
        System.out.println(STR."""
                Producer produced one more bread
                Quantity of bread in the market is \{breadCount}
                """);
        notify();
    }
}
