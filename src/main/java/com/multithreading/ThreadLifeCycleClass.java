package com.multithreading;

import lombok.SneakyThrows;
import lombok.val;

import static java.lang.Thread.currentThread;

public class ThreadLifeCycleClass implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadLifeCycleClass());
        System.out.println(STR."Before start() \{thread.getName()} has state -> \{thread.getState().name()}");

        thread.start();
        Thread.sleep(3000);
        System.out.println(STR."After start() \{thread.getName()} has state -> \{thread.getState().name()}");
    }
    @SneakyThrows
    @Override
    public void run() {
        System.out.println(STR."run() beginning \{currentThread().getName()} has state -> \{currentThread().getState().name()}");

        val result = 2*3 + 10;
        System.out.println(result);

        System.out.println(STR."run() ending \{currentThread().getName()} has state -> \{currentThread().getState().name()}");
    }
}
