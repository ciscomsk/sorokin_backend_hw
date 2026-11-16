package ru.sorokin.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {
    private final ConsoleInput consoleInput;
    private final Map<ConsoleOperationType, OperationCommand> commands;

    @Autowired
    public OperationsConsoleListener(
            ConsoleInput consoleInput,
            List<OperationCommand> commands
    ) {
        this.consoleInput = consoleInput;

        this.commands = commands.stream()
                .collect(Collectors.toMap(OperationCommand::getOperationType, command -> command));
    }

    public void start() {
        System.out.println("MiniBank started. Type EXIT to stop.");
        String availableCommands = ConsoleOperationType.getAvailableCommandsAsString();
        System.out.println("Available commands: " + availableCommands);

        String input;
        do {
            input = consoleInput.readCommand();
            OperationCommand command = commands.get(ConsoleOperationType.valueOf(input));
            try {
                command.execute();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!"EXIT".equals(input));
    }
}
