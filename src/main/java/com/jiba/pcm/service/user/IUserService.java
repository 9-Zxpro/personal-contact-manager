package com.jiba.pcm.service.user;

import com.jiba.pcm.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);
    User getUserById(String id);
    boolean geUserByUserName(String userName);
    User updateUser(User user);
    void deleteUser(String id);
    boolean isUserByEmail(String email);
    List<User> getAllUsers();
}
