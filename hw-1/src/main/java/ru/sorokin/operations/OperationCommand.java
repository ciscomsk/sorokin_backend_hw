package ru.sorokin.operations;

public interface OperationCommand {
    ConsoleOperationType getOperationType();

    void execute();
}
