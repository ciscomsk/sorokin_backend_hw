package ru.sorokin.operations.commands;

import org.springframework.stereotype.Component;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;

@Component
public class Exit implements OperationCommand {
    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.EXIT;
    }

    @Override
    public void execute() {
        System.out.println("MiniBank stopped.");
    }
}
