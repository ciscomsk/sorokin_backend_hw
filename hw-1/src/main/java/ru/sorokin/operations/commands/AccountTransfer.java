package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.account.AccountService;
import ru.sorokin.console.ConsoleInput;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;

@Component
public class AccountTransfer implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    @Autowired
    public AccountTransfer(AccountService accountService, ConsoleInput consoleInput) {
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }

    @Override
    public void execute() {
        int accountIdFrom = consoleInput.readInt("Enter source account id: ");
        int accountIdTo = consoleInput.readInt("Enter source target account id: ");
        int amount = consoleInput.readInt("Enter amount: ");
        accountService.transfer(accountIdFrom, accountIdTo, amount);
    }
}
