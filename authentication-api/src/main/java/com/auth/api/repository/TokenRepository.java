package com.auth.api.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.auth.api.model.Token;

public interface TokenRepository extends MongoRepository<Token, String>{

}
