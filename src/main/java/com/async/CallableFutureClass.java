package com.async;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureClass {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CallableFactorial factorial = new CallableFactorial(6);
        Future<Integer> future = executorService.submit(factorial);
        System.out.println(future.get());
        executorService.shutdown();
    }

    @AllArgsConstructor
    static class CallableFactorial implements Callable<Integer> {
        private int factorial;

        @Override
        public Integer call() throws Exception {
            if (factorial <= 0) {
                throw new RuntimeException("Zero and values above are not allowed");
            }

            int result = 1;
            for (int i = 1; i <= factorial; i++) {
                result *= i;
            }
            return result;
        }
    }
}
