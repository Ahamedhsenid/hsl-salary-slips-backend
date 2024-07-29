package com.hsenid.Paypro.repository;


import com.hsenid.Paypro.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Integer>{
    Optional<User> findByEmail(String email);
}