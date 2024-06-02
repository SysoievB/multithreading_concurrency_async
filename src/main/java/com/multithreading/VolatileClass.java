package com.multithreading;

import static java.lang.Thread.currentThread;

public class VolatileClass {
    volatile static boolean isRunning = true;//without volatile program does not stop

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            int count = 0;
            while (isRunning) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
            }
            System.out.println(STR."\{currentThread().getName()} finished counting with result=\{count}");
        };

        Thread thread = new Thread(task);
        Thread threadForReading = new Thread(() -> {
            System.out.println(STR."\{currentThread().getName()} just read value \{isRunning}");
        });
        thread.start();
        threadForReading.start();

        Thread.sleep(2000);//main thread goes to nap

        isRunning = false;
        thread.join();//Thread-0 finished counting with result=18
    }
}
