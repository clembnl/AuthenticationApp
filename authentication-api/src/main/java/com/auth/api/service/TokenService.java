package com.auth.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.api.model.Token;
import com.auth.api.model.User;
import com.auth.api.repository.TokenRepository;
import com.auth.api.repository.UserRepository;

@Service
public class TokenService {
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	UserRepository userRepository;
	
    public void saveConfirmationToken(Token token) {
        tokenRepository.save(token);
    }

    public Token getToken(User user) {
        return userRepository.findById(user.getId()).get().getToken();
    }

    public void deleteToken(Token token) {
    	tokenRepository.delete(token);
    }

}
