package com.multithreading;

public class DataRaceClass {

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 3; i++) {
                increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);
        thread1.start();
        thread2.start();
        thread3.start();
    }

    static synchronized void increment() {
        Counter.count++;
        System.out.print(STR."\{Counter.count} ");
        //without synchronized -> 3 4 5 2 6 7 3 8 9
        //with synchronized -> 1 2 3 4 5 6 7 8 9
    }

     static class Counter {
        static int count = 0;
    }
}
