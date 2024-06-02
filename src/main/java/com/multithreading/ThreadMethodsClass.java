package com.multithreading;

import static java.lang.StringTemplate.STR;

public class ThreadMethodsClass extends Thread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(STR."\{currentThread().getName()} thread started main mehod execution");
        ThreadMethodsClass threadFirst = new ThreadMethodsClass();
        threadFirst.setName("First thread");
        threadFirst.setPriority(NORM_PRIORITY);
        threadFirst.setDaemon(false);
        threadFirst.start();

        ThreadMethodsClass threadSecond = new ThreadMethodsClass();
        threadSecond.setName("Second thread");
        threadSecond.setPriority(NORM_PRIORITY);
        threadSecond.start();

        threadSecond.join();//main thread waits until this thread finished its execution
        threadFirst.join();
        System.out.println(STR."\{currentThread().getName()} thread finished main mehod execution");
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(STR."\{currentThread().getName()}, is daemon=\{isDaemon()}, current state is \{currentThread().getState().name()} value equals \{i}");
        }
    }
}