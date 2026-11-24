package ru.sorokin.operations.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sorokin.account.AccountService;
import ru.sorokin.console.ConsoleInput;
import ru.sorokin.operations.ConsoleOperationType;
import ru.sorokin.operations.OperationCommand;

@Component
public class DepositCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    @Autowired
    public DepositCommand(AccountService accountService, ConsoleInput consoleInput) {
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        int accountId = consoleInput.readPositiveInt("Enter account id:", "account id");
        int amount = consoleInput.readPositiveInt("Enter amount:", "amount");
        accountService.deposit(accountId, amount);
        System.out.println("Deposited " + amount + " to account " + accountId + ".");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
