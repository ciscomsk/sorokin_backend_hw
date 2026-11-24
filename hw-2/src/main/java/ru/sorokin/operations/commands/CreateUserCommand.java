package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.console.ConsoleInput;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;
import ru.sorokin.user.User;
import ru.sorokin.user.UserService;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserService userService;
    private final ConsoleInput consoleInput;

    @Autowired
    public CreateUserCommand(UserService userService, ConsoleInput consoleInput) {
        this.userService = userService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        String login = consoleInput.readRequiredString("Enter login:", "login");
        User user = userService.createUser(login);
        System.out.println("User created: " + user);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
