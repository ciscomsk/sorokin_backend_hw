package ru.sorokin.user;

import ru.sorokin.account.Account;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private final String login;
    private final List<Account> accountList;

    public User(int id, String login) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accounts=" + accountList +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }
}
