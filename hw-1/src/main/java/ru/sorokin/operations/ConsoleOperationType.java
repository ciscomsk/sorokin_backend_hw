package ru.sorokin.operations;

import java.util.Arrays;
import java.util.List;

public enum ConsoleOperationType {
    USER_CREATE,
    SHOW_ALL_USERS,
    ACCOUNT_CREATE,
    ACCOUNT_DEPOSIT,
    ACCOUNT_WITHDRAW,
    ACCOUNT_TRANSFER,
    ACCOUNT_CLOSE,
    EXIT;

    public static String getAvailableCommandsAsString() {
        return String.join(", ", getCommandsList());
    }

    public static List<String> getCommandsList() {
        return Arrays.stream(ConsoleOperationType.values()).map(Enum::name).toList();
    }
}
