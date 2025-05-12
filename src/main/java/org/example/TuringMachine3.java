package org.example;

import java.util.*;

public class TuringMachine3 {
    public static void main(String[] args) {
        System.out.println("Tryb 1: Standardowy");
        System.out.println("Tryb 2: Zamiana na konkretną wartość");
        System.out.print("Wybierz tryb maszyny: ");
        Scanner mode = new Scanner(System.in);
        int machineMode = mode.nextInt();
        ArrayList<String> read = new ArrayList<>();
        int currentPosition = 0;
        int newPosition = 0;

        switch (machineMode) {
            case 1:
                System.out.print("Dane wejściowe: ");
                Scanner scanner1 = new Scanner(System.in);
                String entryData1 = scanner1.nextLine();
                System.out.printf("%-25s %-12s %-12s %-20s %-15s %-10s\n",
                        "Taśma z głowicą", "Odczyt", "Stan", "Operacja", "Zapis", "Nowy stan");
                for (int i = 0; i < entryData1.length(); i++){
                    char letter = entryData1.charAt(i);
                    if (letter == '0') {
                        read.add(i, String.valueOf('1'));
                        if (currentPosition==1){
                            newPosition = 0;
                        } else {
                            newPosition = 1;
                        }
                    } else if (letter == '1') {
                        read.add(i, String.valueOf('0'));
                        if (currentPosition==1){
                            newPosition = 0;
                        } else {
                            newPosition = 1;
                        }
                    }
                    System.out.printf("%-25s %-12s %-12s %-20s %-15s %-10s\n",
                            entryData1.substring(0, i) + "["+entryData1.charAt(i)+"]" + entryData1.substring(i+1), entryData1.charAt(i), "q"+currentPosition, read.get(i)+","+"q"+newPosition+",R", read.get(i), "q"+newPosition);
                    if (currentPosition!=newPosition){
                        currentPosition = newPosition;
                    }
                }
                System.out.print("\nDane wyjściowe: ");
                for (int i = 0; i < entryData1.length(); i++){
                    System.out.print(read.get(i));
                }
                System.out.print("\n");
                break;
            case 2:
                System.out.print("Wpisz znak do wartościowania: ");
                Scanner scannerInput = new Scanner(System.in);
                String input = scannerInput.nextLine();
                char znak = input.charAt(0);
                String targetLetter = String.format("%8s", Integer.toBinaryString(znak)).replace(' ', '0');
                System.out.print("Dane wejściowe: ");
                Scanner scanner2 = new Scanner(System.in);
                String entryData2 = scanner2.nextLine();
                System.out.printf("%-25s %-12s %-12s %-20s %-15s %-10s\n",
                        "Taśma z głowicą", "Odczyt", "Stan", "Operacja", "Zapis", "Nowy stan");
                for (int i = 0; i < entryData2.length(); i++){
                    char letter = entryData2.charAt(i);
                    char letterInTarget = targetLetter.charAt(i);
                    if (letter == '0') {
                        read.add(i, String.valueOf(letterInTarget));
                        if (letter == letterInTarget) {
                            if (currentPosition==1){
                                newPosition = 1;
                            } else {
                                newPosition = 0;
                            }
                        } else {
                            if (currentPosition==1){
                                newPosition = 0;
                            } else {
                                newPosition = 1;
                            }
                        }
                    } else if (letter == '1') {
                        read.add(i, String.valueOf(letterInTarget));
                        if (letter == letterInTarget) {
                            if (currentPosition==1){
                                newPosition = 1;
                            } else {
                                newPosition = 0;
                            }
                        } else {
                            if (currentPosition==1){
                                newPosition = 0;
                            } else {
                                newPosition = 1;
                            }
                        }
                    }
                    System.out.printf("%-25s %-12s %-12s %-20s %-15s %-10s\n",
                            entryData2.substring(0, i) + "["+entryData2.charAt(i)+"]" + entryData2.substring(i+1), entryData2.charAt(i), "q"+currentPosition, read.get(i)+","+"q"+newPosition+",R", read.get(i), "q"+newPosition);
                    if (currentPosition!=newPosition){
                        currentPosition = newPosition;
                    }
                }
                System.out.print("\nDane wyjściowe: ");
                for (int i = 0; i < entryData2.length(); i++){
                    System.out.print(read.get(i));
                }
                System.out.print("\n");
                break;
        }
    }
}