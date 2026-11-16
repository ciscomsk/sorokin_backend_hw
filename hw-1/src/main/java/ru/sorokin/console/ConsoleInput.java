package ru.sorokin.console;

import org.springframework.stereotype.Component;
import ru.sorokin.operations.ConsoleOperationType;

import java.util.Scanner;

// TODO: тут по идее должен быть ConsoleIOService тк вывод сейчас делегируется сервисам/командам/OperationsConsoleListener
// TODO: правильно ли валидировать пользовательский ввод здесь - не пробрасывая ошибки в OperationsConsoleListener
@Component
public class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        String input;
        while (true) {
            System.out.println("Enter command: ");
            input = scanner.nextLine().trim();
            if (isValidCommand(input)) {
                return input;
            }

            System.out.println("Unknown command");
        }
    }

    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input - please enter a valid integer");
            }
        }
    }

    // TODO: возможно валидацию допустимых команд стоило разместить в ConsoleOperationType
    private boolean isValidCommand(String command) {
        return ConsoleOperationType.getCommandsList().contains(command);
    }
}
