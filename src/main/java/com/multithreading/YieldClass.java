package com.multithreading;

public class YieldClass extends Thread {

    public YieldClass() {
        this.start();
    }

    public void run() {
        System.out.println(STR."\{Thread.currentThread().getName()} уступает свое место другим");
        Thread.yield();//the same as -> Thread.sleep(0);
        System.out.println(STR."\{Thread.currentThread().getName()} has finished executing.");
    }

    public static void main(String[] args) {
        new YieldClass();
        new YieldClass();
        new YieldClass();
    }
}
