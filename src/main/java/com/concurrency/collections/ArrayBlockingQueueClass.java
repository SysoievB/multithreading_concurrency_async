package com.concurrency.collections;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueClass {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        //queue.add(5);//java.lang.IllegalStateException: Queue full
        queue.offer(5);//will not be added
        System.out.println(queue);
    }
}
