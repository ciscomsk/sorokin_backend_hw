package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.account.AccountService;
import ru.sorokin.console.ConsoleInput;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;

@Component
public class AccountDeposit implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    @Autowired
    public AccountDeposit(AccountService accountService, ConsoleInput consoleInput) {
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }

    @Override
    public void execute() {
        int accountId = consoleInput.readInt("Enter account id: ");
        int amount = consoleInput.readInt("Enter amount: ");
        accountService.deposit(accountId, amount);
    }
}
