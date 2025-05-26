package com.laboratorna;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDAO {
    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public User createUser(User user) {
        int id = idCounter.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return user;
    }
}
