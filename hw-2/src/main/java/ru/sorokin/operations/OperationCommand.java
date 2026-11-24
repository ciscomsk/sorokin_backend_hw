package ru.sorokin.operations;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}
