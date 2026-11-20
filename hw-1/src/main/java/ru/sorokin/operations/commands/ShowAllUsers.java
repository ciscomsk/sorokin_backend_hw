package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;
import ru.sorokin.user.UserService;

@Component
public class ShowAllUsers implements OperationCommand {
    private final UserService userService;

    @Autowired
    public ShowAllUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }

    @Override
    public void execute() {
        userService.getAllUsers().values().forEach(System.out::println);
    }
}
