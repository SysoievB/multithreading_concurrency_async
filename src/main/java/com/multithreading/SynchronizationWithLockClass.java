package com.multithreading;

public class SynchronizationWithLockClass {
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 3; i++) {
                new SynchronizationWithLockClass().increment();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
    }

    private void increment() {
        synchronized (LOCK) {
            Counter.count++;
            System.out.print(STR."\{Counter.count} ");
            //without synchronized -> 3 4 5 2 6 7 3 8 9
            //with synchronized -> 1 2 3 4 5 6 7 8 9
        }
    }

     static class Counter {
        static int count = 0;
    }
}
