package utils;

import interfaces.Application;
import interfaces.ParsedInput;

import java.util.Scanner;

public class ConsoleListener {
    private static boolean interrupted = false;
    private Application application;
    private Scanner scanner;

    public ConsoleListener(Application application) throws Exception {
        this.application = application;
        scanner = new Scanner(System.in);
        while (!interrupted) {
            String input = scanner.nextLine().trim();
            if (input.equals("quit")) {
                interrupted = true;
                continue;
            }
            try {
                ParsedInput parsedInput = application.parseInput(input);
                application.handleCommand(parsedInput);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
        scanner.close();
    }
}
