package com.knightlore.server;

import java.util.Scanner;

public class InputHandler extends Thread {

    private boolean running;

    public void run(){
        running = true;
        Scanner scanner = new Scanner(System.in);

        while(running) {
            String input = scanner.nextLine();

            switch(input){

                case "test":
                    System.out.println("Test");

                    return;

                default:
                    System.out.println("Command not found");
                    return;
            }
        }
    }

    public void close(){
        running = false;
    }
}
