package com.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(STR."Thread is - \{currentThread().getName()} starts");
        Runnable task = () -> {
            System.out.println(STR."Thread is - \{currentThread().getName()}");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(task);
        }
        executorService.shutdown();//close
        executorService.awaitTermination(5, TimeUnit.SECONDS);//the same as join + sleep
        System.out.println(STR."Thread is - \{currentThread().getName()} ends");
    }
}
