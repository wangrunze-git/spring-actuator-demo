package com.example.service.impl;

import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("user1");
        user.setAge(23);
        user.setNote("note1");
        return user;
    }

}
