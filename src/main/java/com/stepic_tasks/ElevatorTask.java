package com.stepic_tasks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.stepic_tasks.Destination.SECOND_FLOOR;
import static com.stepic_tasks.Destination.GROUND_FLOOR;
import static com.stepic_tasks.Destination.FIRST_FLOOR;

/**
 * Есть лифт, грузоподъемность которого 400 кг. На этажах могут заходить разные люди.
 * Если суммарная масса людей превышает 400 кг, то человек должен ждать следующего лифта,
 * если не превышает, то может зайти и выйти на нужном ему этаже.
 */
public class ElevatorTask {
    public static void main(String[] args) {
        List<Employee> elevatorWaiters = List.of(
                new Employee("Vasia", 90, SECOND_FLOOR),
                new Employee("Lena", 70, SECOND_FLOOR),
                new Employee("Ivan", 110, FIRST_FLOOR),
                new Employee("Olga", 130, GROUND_FLOOR),
                new Employee("Alex", 80, SECOND_FLOOR),
                new Employee("Anna", 50, GROUND_FLOOR)
        );

        ExecutorService executorService = Executors.newFixedThreadPool(5);

            elevatorWaiters.forEach(employee -> {
            executorService.submit(() -> {
                try {
                    new Elevator().enterElevator(employee);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            });});

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Elevator {
    private static final int WEIGHT_CAPACITY = 400;
    private final List<Employee> currentPassengers = new ArrayList<>();
    private static int currentWeight = 0;

    synchronized void enterElevator(Employee employee) throws InterruptedException {
        if (currentWeight + employee.getWeight() <= WEIGHT_CAPACITY) {
            currentPassengers.add(employee);
            currentWeight += employee.getWeight();
            System.out.println(STR."\{employee.getName()} entered the elevator. Current total weight: \{currentWeight} kg");
        } else {
            System.out.println(STR."\{employee.getName()} has to wait for the next elevator. Current total weight: \{currentWeight} kg");
        }
    }

    synchronized void exitElevator(Employee employee) {
        currentPassengers.remove(employee);
        currentWeight -= employee.getWeight();
        System.out.println(STR."\{employee.getName()} exited the elevator. Current total weight: \{currentWeight} kg");
    }
}

enum Destination {
    GROUND_FLOOR, FIRST_FLOOR, SECOND_FLOOR
}

@ToString
@Getter
@AllArgsConstructor
class Employee {
    String name;
    int weight;
    Destination destination;
}
