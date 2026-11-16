package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.console.ConsoleInput;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;
import ru.sorokin.user.UserService;

@Component
public class UserCreate implements OperationCommand {
    private final UserService userService;
    private final ConsoleInput consoleInput;

    @Autowired
    public UserCreate(UserService userService, ConsoleInput consoleInput) {
        this.userService = userService;
        this.consoleInput = consoleInput;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }

    @Override
    public void execute() {
        String login = consoleInput.readString("Enter login: ");
        userService.createUser(login);
    }
}
