package com.async.tasks;

import java.util.Scanner;
import java.util.function.IntFunction;

class CurryProduct {

    public static int calc(int x, int y, int z) {
        // Curry it: f(x, y, z) = x + y^2 + z^3
        IntFunction<IntFunction<IntFunction<Integer>>> f =
                 v1 -> v2  -> v3 -> v1 + (int) Math.pow(v2, 2) + (int) Math.pow(v3, 3);

        return f.apply(x).apply(y).apply(z);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] values = scanner.nextLine().split(" ");

        int x = Integer.parseInt(values[0]);
        int y = Integer.parseInt(values[1]);
        int z = Integer.parseInt(values[2]);

        System.out.println(calc(x, y, z));
    }
}