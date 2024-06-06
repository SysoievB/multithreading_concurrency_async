package com.multithreading;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ThreadLocalClass {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<User> userContext = new ThreadLocal<>();
        Runnable task = () -> {
            userContext.set(new User("Vasia", 23));
            System.out.println(STR."User`s name is - \{userContext.get().getName()} and age - \{userContext.get().getAge()}");
            userContext.remove();
            System.out.println(STR."\{userContext.get()}");
        };

        Thread thread = new Thread(task);
        thread.start();
        thread.join();
    }
}
@Getter
@AllArgsConstructor
class User {
    String name;
    int age;
}