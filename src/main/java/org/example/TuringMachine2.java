package org.example;

import java.util.*;

public class TuringMachine2 {
    private final List<Character> tape;
    private int headPosition;
    private String currentState;
    private final Map<String, Transition> transitionTable;

    public TuringMachine2() {
        tape = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            tape.add('_'); // Przygotuj pustą taśmę o długości 8
        }

        headPosition = 0;
        currentState = "q0";
        transitionTable = new HashMap<>();

        // Docelowy ciąg do zapisania: 01100001
        char[] target = {'0', '1', '1', '0', '0', '0', '0', '1'};

        // Dodaj przejścia: q0 -> q1 -> q2 -> ... -> q8
        for (int i = 0; i < target.length; i++) {
            String state = "q" + i;
            String nextState = "q" + (i + 1);
            char writeSymbol = target[i];

            // Obsłuż dowolny znak na taśmie
            transitionTable.put(state + "_", new Transition(writeSymbol, 'R', nextState));
            transitionTable.put(state + "0", new Transition(writeSymbol, 'R', nextState));
            transitionTable.put(state + "1", new Transition(writeSymbol, 'R', nextState));
        }

        // Stan końcowy q8 — nie wykonuje dalszych operacji
        transitionTable.put("q8_", new Transition('_', 'N', "q8"));
        transitionTable.put("q8_0", new Transition('_', 'N', "q8"));
        transitionTable.put("q8_1", new Transition('_', 'N', "q8"));
    }

    public void run() {
        String finalState = "q8";

        System.out.printf("%-30s %-10s %-10s %-20s %-10s %-12s\n",
                "Taśma z głowicą", "Odczyt", "Stan", "Operacja", "Zapis", "Nowy stan");

        while (!currentState.equals(finalState)) {
            char currentSymbol = tape.get(headPosition);
            String key = currentState + "_" + currentSymbol;
            Transition t = transitionTable.get(key);

            StringBuilder tapeWithHead = new StringBuilder();
            for (int i = 0; i < tape.size(); i++) {
                if (i == headPosition) {
                    tapeWithHead.append("[").append(tape.get(i)).append("]");
                } else {
                    tapeWithHead.append(" ").append(tape.get(i)).append(" ");
                }
            }

            if (t == null) {
                System.out.printf("%-30s %-10s %-10s %-20s %-10s %-12s\n",
                        tapeWithHead, currentSymbol, currentState, "brak instrukcji", "", "");
                break;
            }

            System.out.printf("%-30s %-10s %-10s %-20s %-10s %-12s\n",
                    tapeWithHead,
                    currentSymbol,
                    currentState,
                    String.format("%c,%s,%c", t.write, t.nextState, t.move),
                    t.write,
                    t.nextState
            );

            // Zapisz znak i przesuń głowicę
            tape.set(headPosition, t.write);
            if (t.move == 'R') headPosition++;
            else if (t.move == 'L') headPosition--;
            currentState = t.nextState;
        }

        System.out.println("\nWynik końcowy: " + tapeToString());
    }

    private String tapeToString() {
        StringBuilder sb = new StringBuilder();
        for (char c : tape) {
            if (c != '_') sb.append(c);
        }
        return sb.toString();
    }

    private static class Transition {
        char write;
        char move;
        String nextState;

        Transition(char write, char move, String nextState) {
            this.write = write;
            this.move = move;
            this.nextState = nextState;
        }
    }

    public static void main(String[] args) {
        TuringMachine2 tm = new TuringMachine2();
        tm.run();
    }
}