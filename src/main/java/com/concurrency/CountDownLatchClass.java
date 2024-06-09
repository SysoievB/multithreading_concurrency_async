package com.concurrency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchClass {
    public static void main(String[] args) {
        CountDownLatch callBox = new CountDownLatch(2);

        new Person1("Vasia", callBox).start();
        new Person1("John", callBox).start();
        new Person1("Ann", callBox).start();
        new Person1("Henrik", callBox).start();
    }
}

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
class Person1 extends Thread {
    String name;
    CountDownLatch callBox;

    @Override
    public void run() {
        System.out.println(STR."\{name} waiting");
        try {
            callBox.countDown();
            System.out.println(STR."\{name} is using phone");
            Thread.sleep(2000);
            System.out.println(STR."\{name} finished call");
            callBox.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
