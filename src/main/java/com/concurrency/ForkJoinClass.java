package com.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Класс SumTask:
 * SumTask наследуется от RecursiveTask<Long> и переопределяет метод compute, где определяется
 * логика разделения и объединения задач.
 * Если диапазон элементов (размер задачи) меньше или равен THRESHOLD, задача выполняется напрямую.
 * Если задача слишком велика, она делится на две подзадачи: leftTask и rightTask.
 * leftTask запускается с помощью fork(), а rightTask выполняется непосредственно.
 * Результаты подзадач объединяются с помощью join() и возвращаются.
 *
 * Главный метод main:
 * Создается массив с 10,000 элементами.
 * Создается пул потоков ForkJoinPool.
 * Создается и запускается основная задача SumTask.
 * Результат вычисления выводится на экран.*/
public class ForkJoinClass {

    // Task to sum elements of a given array range
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 1000;
        private final int[] array;
        private final int start;
        private final int end;

        SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // Compute directly if the task is small enough
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Split the task into smaller tasks
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                // Fork the left task
                leftTask.fork();

                // Compute the right task
                long rightResult = rightTask.compute();

                // Join the left task and get the result
                long leftResult = leftTask.join();

                // Combine results
                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        // Example array
        int[] array = new int[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        // Create a ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Create the main task
        SumTask task = new SumTask(array, 0, array.length);

        // Execute the task
        long result = pool.invoke(task);

        // Print the result
        System.out.println("Sum: " + result);
    }
}

