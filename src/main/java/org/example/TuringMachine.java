package org.example;

import java.util.*;

public class TuringMachine {
    private final List<Character> tape;
    private int headPosition;
    private String currentState;
    private final Map<String, Transition> transitionTable;

    public TuringMachine(String input) {
        tape = new ArrayList<>();
        for (char c : input.toCharArray()) {
            tape.add(c);
        }
        tape.add('_');
        headPosition = 0;
        currentState = "q0";

        transitionTable = new HashMap<>();

        transitionTable.put("q0_1", new Transition('0', 'R', "q1"));
        transitionTable.put("q0_0", new Transition('1', 'R', "q0"));
        transitionTable.put("q2__", new Transition('_', 'N', "q2"));
        transitionTable.put("q1_1", new Transition('0', 'R', "q1"));
        transitionTable.put("q1_0", new Transition('1', 'R', "q0"));

    }

    public void run() {
        String finalState = "q2";

        System.out.printf("%-25s %-12s %-12s %-20s %-15s %-10s\n",
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
                System.out.printf("%-25s %-12s %-12s %-20s %-15s %-10s\n",
                        tapeWithHead, currentSymbol, currentState, "brak instrukcji", "", "");
                break;
            }

            System.out.printf("%-25s %-12s %-12s %-20s %-15s %-10s\n",
                    tapeWithHead,
                    currentSymbol,
                    currentState,
                    String.format("%c,%s,%c", t.write, t.nextState, t.move),
                    t.write,
                    t.nextState
            );

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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj wejście (ciąg 0 i 1): ");
        TuringMachine tm = new TuringMachine(scanner.nextLine());
        tm.run();
    }
}