package com.multithreading;

import lombok.AllArgsConstructor;

public class LockStarvationClass {
    public static void main(String[] args) throws InterruptedException {
        com.multithreading.Market2 market = new com.multithreading.Market2();
        Thread consumer1 = new Thread(new com.multithreading.Consumer2(market), "Consumer1");
        Thread consumer2 = new Thread(new com.multithreading.Consumer2(market), "Consumer2");
        Thread consumer3 = new Thread(new com.multithreading.Consumer2(market), "Consumer3");
        Thread producer = new Thread(new com.multithreading.Producer2(market), "Producer");
        consumer1.setPriority(Thread.MAX_PRIORITY);
        consumer2.setPriority(Thread.MIN_PRIORITY);
        consumer3.setPriority(Thread.MAX_PRIORITY);

        consumer1.start();
        consumer2.start();
        consumer3.start();
        producer.start();

        // Joining threads to ensure main thread waits for their completion
        consumer1.join();
        consumer2.join();
        consumer3.join();
        producer.join();
    }
}

@AllArgsConstructor
class Consumer3 implements Runnable {
    private final Market3 market;

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
class Producer3 implements Runnable {
    private final Market3 market;

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

class Market3 {
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



