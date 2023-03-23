package com.example.application.data.service;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service 
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username){
        return this.userRepository.findByUsernameLike(username);
    }
}