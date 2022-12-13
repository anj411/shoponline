package com.shoponline.service;

import java.util.List;

import com.shoponline.model.User;

public interface UserService {

    public void initRoleAndUser();

    public User registerNewUser(User user);

    public String getEncodedPassword(String password);

    public User saveUser(User user);

    public List<User> getAllUser();

    public User findUserByUserName(String username);
}
