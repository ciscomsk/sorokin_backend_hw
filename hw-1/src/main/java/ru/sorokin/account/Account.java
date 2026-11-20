package ru.sorokin.account;

public class Account {
    private final int id;
    private final int userId;
    private int moneyAmount;

    public Account(int id, int userId) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void deposit(int amount) {
        moneyAmount += amount;
    }

    public void withdraw(int amount) {
        moneyAmount -= amount;
    }
}
