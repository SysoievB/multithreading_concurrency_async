package com.concurrency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.Semaphore;

public class SemaphoreClass {
    public static void main(String[] args) {
        Semaphore callBox = new Semaphore(2);

        new Person("Vasia", callBox).start();
        new Person("John", callBox).start();
        new Person("Ann", callBox).start();
        new Person("Henrik", callBox).start();
    }
}
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
class Person extends Thread {
    String name;
    Semaphore callBox;

    @Override
    public void run() {
        System.out.println(STR."\{name} waiting");
        try {
            callBox.acquire();
            System.out.println(STR."\{name} is using phone");
            Thread.sleep(2000);
            System.out.println(STR."\{name} finished call");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            callBox.release();
        }
    }
}