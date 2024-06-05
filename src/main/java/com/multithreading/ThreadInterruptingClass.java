package com.multithreading;

public class ThreadInterruptingClass extends Thread {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main starts");

        InterruptedThread thread = new InterruptedThread();
        thread.start();
        //thread.interrupt();//We decide to stop Thread-0
        Thread.sleep(3000);
        thread.interrupt();//prints sum number

        thread.join();
        System.out.println("main ends");
    }

    static class InterruptedThread extends Thread {
        private int sum = 0;

        @Override
        public void run() {
            for (int i = 0; i < 100000000; i++) {
                if (isInterrupted()) {
                    System.out.println(STR."We decide to stop \{Thread.currentThread().getName()}");
                    return;
                }
                sum = sum + i;
            }

            System.out.println(sum);
        }
    }
}
