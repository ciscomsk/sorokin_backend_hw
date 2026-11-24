package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.account.AccountService;
import ru.sorokin.console.ConsoleInput;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;

@Component
public class AccountTransferCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    @Autowired
    public AccountTransferCommand(AccountService accountService, ConsoleInput consoleInput) {
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        int fromAccountId = consoleInput.readPositiveInt("Enter source account id:", "source account id");
        int toAccountId = consoleInput.readPositiveInt("Enter target account id:", "target account id");
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("source and target account id must be different");
        }

        int amount = consoleInput.readPositiveInt("Enter amount:", "amount");
        accountService.transfer(fromAccountId, toAccountId, amount);
        System.out.println("Transfer completed from account " + fromAccountId + " to account " + toAccountId + ".");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
