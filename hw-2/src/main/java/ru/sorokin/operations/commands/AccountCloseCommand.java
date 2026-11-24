package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.account.Account;
import ru.sorokin.account.AccountService;
import ru.sorokin.console.ConsoleInput;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;
import ru.sorokin.user.User;
import ru.sorokin.user.UserService;

@Component
public class AccountCloseCommand implements OperationCommand {
    private final AccountService accountService;
    private final UserService userService;
    private final ConsoleInput consoleInput;

    @Autowired
    public AccountCloseCommand(AccountService accountService, UserService userService, ConsoleInput consoleInput) {
        this.accountService = accountService;
        this.userService = userService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        int accountId = consoleInput.readPositiveInt("Enter account id to close:", "account id");
        Account closedAccount = accountService.closeAccount(accountId);
        User user = userService.findUserById(closedAccount.getUserId());
        user.getAccountList().remove(closedAccount);
        System.out.println("Account " + accountId + " closed.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
