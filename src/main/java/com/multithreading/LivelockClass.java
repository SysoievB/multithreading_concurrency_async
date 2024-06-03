package com.multithreading;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

public class LivelockClass {

    public static void main(String[] args) {
        val husband = new Diner("Husband");
        val wife = new Diner("Wife");

        val spoon = new Spoon(husband);

        Thread husbandThread = new Thread(() -> husband.eatWith(spoon, wife));
        Thread wifeThread = new Thread(() -> wife.eatWith(spoon, husband));

        husbandThread.start();
        wifeThread.start();
    }

    @AllArgsConstructor
    @Getter
    @Setter
    static class Spoon {
        private Diner owner;

        public synchronized void use() {
            System.out.printf("%s has eaten!", owner.name);
        }
    }

    @Getter
    static class Diner {
        private String name;
        private boolean isHungry;

        public Diner(String n) {
            name = n;
            isHungry = true;
        }

        public void eatWith(Spoon spoon, Diner spouse) {
            while (isHungry) {
                // Don't have the spoon, so wait patiently
                if (spoon.getOwner() != this) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        continue;
                    }
                    continue;
                }

                // If spouse is hungry, offer the spoon
                if (spouse.isHungry()) {
                    System.out.printf("%s: You eat first, dear %s!%n", name, spouse.getName());
                    spoon.setOwner(spouse);
                    continue;
                }

                // Spouse wasn't hungry, so finally eat
                spoon.use();
                isHungry = false;
                System.out.printf("%s: I am done eating!%n", name);
                spoon.setOwner(spouse);
            }
        }
    }
}

