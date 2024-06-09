package com.concurrency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerClass {
    public static void main(String[] args) {
        Exchanger<Action> exchanger = new Exchanger<>();
        List<Action> actions1 = List.of(Action.SCISSORS, Action.STONE, Action.PAPER, Action.STONE);
        List<Action> actions2 = List.of(Action.STONE, Action.SCISSORS, Action.STONE, Action.PAPER);

        new Friend("Vasia", actions1, exchanger).start();
        new Friend("Petia", actions2, exchanger).start();
    }
}

enum Action {
    STONE, SCISSORS, PAPER
}

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
class Friend extends Thread {
    String name;
    List<Action> actions;
    Exchanger<Action> exchanger;

    private void winner(Action my, Action friend) {
        if ((my == Action.STONE && friend == Action.SCISSORS)
                || (my == Action.SCISSORS && friend == Action.PAPER)
                || (my == Action.PAPER && friend == Action.STONE)) {
            System.out.println(STR."\{name} won");
        }
    }

    @Override
    public void run() {
        Action reply;
        for (Action action : actions) {
            try {
                reply = exchanger.exchange(action);
                winner(action, reply);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}