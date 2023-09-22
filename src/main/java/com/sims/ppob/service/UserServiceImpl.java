package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.UserSaveRequest;
import com.sims.ppob.repository.UserRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final LocalDateTime newDate = LocalDateTime.now();

    @Autowired
    private Model model;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse save(Users users, UserSaveRequest request, BindingResult bindingResult) {
        ValidationService.validate(bindingResult);

        Users user = new Users();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setCreatedAt(newDate);
        user.setUpdatedAt(newDate);

        userRepository.insertQuery(user);

        return model.toUserResponse(user);
    }
}
