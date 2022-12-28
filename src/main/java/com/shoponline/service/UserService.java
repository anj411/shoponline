package com.shoponline.service;

import java.util.List;

import com.shoponline.model.User;

/**
 * This is the abstraction for User Service
 * Operations related to User such as register new user, save user, get Users, find user by username etc
 * will be called from here.
 */
public interface UserService {

    public void initRoleAndUser();

    /**
     * this service operation when called
     * will register a new User.
     * @param user
     * @return
     */
    public User registerNewUser(User user);

    /**
     * this service operation when called
     * will return an encoded String of password.
     * @param password
     * @return
     */
    public String getEncodedPassword(String password);

    /**
     * this service operation when called
     * will save a new user and will return
     * the same user.
     * @param user
     * @return
     */
    public User saveUser(User user);

    /**
     * this service operation when called
     * will return the list of all users.
     * @return
     */
    public List<User> getAllUser();

    /**
     * this service operation when called
     * will return the user mapped with the
     * passed username.
     * @param username
     * @return
     */
    public User findUserByUserName(String username);
}
