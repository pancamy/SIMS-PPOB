package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.UserLoginRequest;
import com.sims.ppob.model.UserLoginResponse;
import com.sims.ppob.model.UserRegisterRequest;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.repository.UserRepository;
import com.sims.ppob.utility.Model;
import com.sims.ppob.utility.Token;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final LocalDateTime newDate = LocalDateTime.now();

    private final Token token;

    private final Model model;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(Token token, Model model, UserRepository userRepository) {
        this.token = token;
        this.model = model;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void registration(UserRegisterRequest request, BindingResult bindingResult) {
        ValidationService.validate(bindingResult);

        Users user = new Users();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setProfile("/user-static-profile.jpg");
        user.setCreatedAt(newDate);
        user.setUpdatedAt(newDate);

        userRepository.save(user);

        model.toUserResponse(user);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request, BindingResult bindingResult) {
        ValidationService.validate(bindingResult);

        Users user = userRepository.login(request.getEmail());
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email atau password salah");
        }

        boolean checkPassword = BCrypt.checkpw(request.getPassword(), user.getPassword());
        if(!checkPassword){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email atau password salah");
        }

        Date now = new Date();
        long accessTokenExpirationTimeInMillis = 10 * 60 * 1000;

        Date accessTokenExpirationDate = new Date(now.getTime() + accessTokenExpirationTimeInMillis);
        String accessToken = token.getToken(user.getId(), accessTokenExpirationDate);

        return model.toUserLoginResponse(accessToken);
    }

    @Override
    public UserResponse getProfile(Users user) {
        return model.toUserResponse(user);
    }
}
