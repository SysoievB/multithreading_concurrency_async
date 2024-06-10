package com.concurrency.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedCollections {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> source = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> target = Collections.synchronizedList(new ArrayList<>());
        Runnable task = () -> {
            target.addAll(source);
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(target);
    }
}
