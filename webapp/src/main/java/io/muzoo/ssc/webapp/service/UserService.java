package io.muzoo.ssc.webapp.service;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    // find user by usernaem
    // delete user

    private Map<String, User> users = new HashMap<>();

    {
        users.put("gigadot", new User("gigadot", "12345"));
        users.put("admin", new User("admin", "12345"));
    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    public boolean checkIfUserExists(String username) {
        return users.containsKey(username);
    }
}
