package com.sims.ppob.repository;

import com.sims.ppob.entity.Users;

public interface UserRepository {

    void save(Users users);

    Users login(String email);
}
