package ru.sorokin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sorokin.account.AccountService;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<Integer, User> users;
    private final AccountService accountService;
    private int idCounter;

    @Autowired
    public UserService(AccountService accountService) {
        this.users = new HashMap<>();
        this.accountService = accountService;
        this.idCounter = 0;
    }

    public void createUser(String login) {
        validateLogin(login);
        idCounter++;
        User user = new User(idCounter, login);
        users.put(idCounter, user);
        accountService.createAccount(idCounter, false);

        System.out.printf("User created: %s\n", user);
    }

    public Map<Integer, User> getAllUsers() {
        return users;
    }

    public User getUserById(int userId) {
        return users.get(userId);
    }

    public void checkUserExistence(int userId) {
        boolean exists = users.containsKey(userId);
        if (!exists) {
            throw new IllegalArgumentException("Error: user id %d - does not exists".formatted(userId));
        }
    }

    private void validateLogin(String login) {
        // TODO: по-хорошему нужны кастомные ошибки
        if (login.isBlank()) {
            throw new IllegalArgumentException("Error: login is blank");
        }

        boolean exists = users.values().stream().anyMatch(u -> u.getLogin().equals(login));
        if (exists) {
            throw new IllegalArgumentException("Error: login %s - already exists".formatted(login));
        }
    }
}
