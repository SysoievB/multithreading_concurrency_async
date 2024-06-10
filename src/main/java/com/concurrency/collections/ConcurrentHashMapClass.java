package com.concurrency.collections;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapClass {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "Vasia");
        map.put(2, "Petia");
        map.put(3, "Lena");
        map.put(4, "Luna");

        Runnable task = () -> {
            Iterator<Integer> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Integer i = iterator.next();
                System.out.println(STR."\{i} : \{map.get(i)}");
            }
        };

        Runnable task1 = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.put(12, "John");
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task1);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        map.forEach((k, v) -> System.out.println(STR."\{k} : \{v}"));
    }
}
