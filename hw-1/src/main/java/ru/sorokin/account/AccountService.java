package ru.sorokin.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.sorokin.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class AccountService {
    private final Map<Integer, Account> accounts;
    private final AccountProperties accountProperties;
    private final UserService userService;
    private int idCounter;

    @Autowired
    public AccountService(
            AccountProperties accountProperties,
            // TODO: кажется надо пересматривать архитектуру приложения
            @Lazy UserService userService
    ) {
        this.accounts = new HashMap<>();
        this.accountProperties = accountProperties;
        this.userService = userService;
        this.idCounter = 0;
    }

    public void createAccount(int userId, boolean log) {
        userService.checkUserExistence(userId);
        idCounter++;
        Account account = new Account(idCounter, userId);
        account.setMoneyAmount(accountProperties.getDefaultAmount());
        accounts.put(idCounter, account);
        userService.getUserById(userId).addAccount(account);

        if (log) {
            System.out.printf("Account created: %s\n", account);
        }
    }

    public void deposit(int accountId, int amount) {
        validateDeposit(accountId, amount);
        Account account = accounts.get(accountId);
        account.deposit(amount);

        System.out.printf("Deposited %d to account %d. New balance: %d\n", accountId, amount, account.getMoneyAmount());
    }

    public void withdraw(int accountId, int amount) {
        validateWithdraw(accountId, amount);
        Account account = accounts.get(accountId);
        account.withdraw(amount);

        System.out.printf("Withdrew %d from account %d. New balance: %d\n", accountId, amount, account.getMoneyAmount());
    }

    public void transfer(int accountIdFrom, int accountIdTo, int amount) {
        validateTransfer(accountIdFrom, accountIdTo, amount);
        Account accountFrom = accounts.get(accountIdFrom);
        Account accountTo = accounts.get(accountIdTo);

        int transferAmount;
        String amountMessage;
        if (accountFrom.getUserId() == accountTo.getUserId()) {
            transferAmount = amount;
            amountMessage = "Amount: %d (no commission, same user)".formatted(amount);
        } else {
            transferAmount = (int) Math.round(amount * (1 - accountProperties.getTransferCommission()));
            amountMessage = "Amount: %d, commission: %d, recipient received: %d".formatted(amount, amount - transferAmount, transferAmount);
        }

        accountFrom.withdraw(amount);
        accountTo.deposit(transferAmount);

        System.out.printf(
                "Transfer completed from account %d to account %d. %s\n",
                accountIdFrom, accountIdTo, amountMessage
        );
    }

    public void close(int accountId) {
        validateClose(accountId);
        Account accountToClose = accounts.get(accountId);
        int transferAmount = accountToClose.getMoneyAmount();
        int userId = accountToClose.getUserId();
        List<Account> userAccounts = userService.getUserById(userId).getAccountList();
        Account accountToTransfer = userAccounts.getFirst();
        accountToTransfer.deposit(transferAmount);

        accounts.remove(accountId);
        userAccounts.remove(accountToClose);

        System.out.printf(
                "Account %d closed. Remaining balance %d transferred to account %d\n",
                accountToClose.getId(), transferAmount, accountToTransfer.getId()
        );
    }

    private void checkAccountExistence(int accountId) {
        boolean exists = accounts.containsKey(accountId);
        if (!exists) {
            throw new IllegalArgumentException("Error: account id: %d - does not exists".formatted(accountId));
        }
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Error: amount should be positive");
        }
    }

    private void checkSufficientBalance(int accountId, int withdrawAmount) {
        int balance = accounts.get(accountId).getMoneyAmount();

        if (withdrawAmount > balance) {
            throw new IllegalArgumentException(
                    "Error: insufficient funds on account: id=%d, moneyAmount=%d, attempted withdraw=%d".formatted(
                            accountId, balance, withdrawAmount
                    ));
        }
    }

    private void checkSelfTransfer(int accountIdFrom, int accountIdTo) {
        if (accountIdFrom == accountIdTo) {
            throw new IllegalArgumentException("Error: source and target account account id must be different");
        }
    }

    private void checkAccountCloseability(int accountId) {
        int userId = accounts.get(accountId).getUserId();
        int accountsNumber = userService.getUserById(userId).getAccountList().size();
        if (accountsNumber == 1) {
            throw new IllegalArgumentException("Error: it is impossible to close the only bank account");
        }
    }

    private void validateDeposit(int accountId, int amount) {
        checkAccountExistence(accountId);
        validateAmount(amount);
    }

    private void validateWithdraw(int accountId, int amount) {
        checkAccountExistence(accountId);
        validateAmount(amount);
        checkSufficientBalance(accountId, amount);
    }

    private void validateTransfer(int accountIdFrom, int accountIdTo, int amount) {
        Stream.of(accountIdFrom, accountIdTo).forEach(this::checkAccountExistence);
        checkSelfTransfer(accountIdFrom, accountIdTo);
        validateAmount(amount);
        checkSufficientBalance(accountIdFrom, amount);
    }

    private void validateClose(int accountId) {
        checkAccountExistence(accountId);
        checkAccountCloseability(accountId);
    }
}
