package com.auth.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.api.config.MessageStrings;
import com.auth.api.config.ResponseStatus;
import com.auth.api.dto.ResetPasswordDto;
import com.auth.api.dto.ResponseDto;
import com.auth.api.dto.SignInDto;
import com.auth.api.dto.SignInResponseDto;
import com.auth.api.dto.SignUpDto;
import com.auth.api.exception.CustomException;
import com.auth.api.model.Token;
import com.auth.api.model.Type;
import com.auth.api.model.User;
import com.auth.api.repository.UserRepository;
import com.auth.api.util.Helper;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenService tokenService;
	
	public List<User> getAllUsers(String token) {
		return userRepository.findAll();
	}
	
    public ResponseDto signUp(SignUpDto signupDto) throws CustomException {
        // Check to see if the current email address has already been registered.
        if (userRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        String tokenString = UUID.randomUUID().toString();
        // generate token for user
        final Token token = new Token(tokenString);
        //encrypt the password and get current DateTime
        User user = new User(signupDto.getUsername(), signupDto.getEmail(), passwordEncoder().encode(signupDto.getPassword()), LocalDateTime.now(), Type.USER, token);

        User createdUser;
        try {
            // save the User
            createdUser = userRepository.save(user);
            // save token in database
            tokenService.saveConfirmationToken(createdUser.getToken());
            // success in creating
            return new ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_CREATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }	
    
    public SignInResponseDto signIn(SignInDto signInDto) throws CustomException {
        // first find User by email
        User user = userRepository.findByEmail(signInDto.getEmail()).get();
        if(!Helper.notNull(user)){
            throw  new CustomException("user not present");
        }
        try {
            // check if password is right
            //if (!user.getPassword().equals(passwordEncoder().encode(signInDto.getPassword()))){
        	if (!passwordEncoder().matches(signInDto.getPassword(), user.getPassword())) {
                // passowrd does not match
                throw  new CustomException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
        try {
            // check if email is right
        	if (!signInDto.getEmail().equals(user.getEmail())) {
                // email does not match
                throw  new CustomException(MessageStrings.WRONG_EMAIL);
            }
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }

        Token token = tokenService.getToken(user);

        if(!Helper.notNull(token)) {
            // token not present
            throw new CustomException("token not present");
        }

        return new SignInResponseDto ("success", token.getToken());
    }    
    
    public ResponseDto updateUser(String userId, SignInDto signInDto, String tokenString) {
        // first find User by email
        User user = userRepository.findById(userId).get();
        if(!Helper.notNull(user)){
            throw  new CustomException("user not present");
        }
        
        Token token = tokenService.getToken(user);

        if(!Helper.notNull(token)) {
            // token not present
            throw new CustomException("token not present");
        }
        else if (token.getToken() != tokenString) {
        	throw new CustomException("wrong user");
        }
        
        User userUpdate = new User(signInDto.getUsername(), signInDto.getEmail(), passwordEncoder().encode(signInDto.getPassword()), user.getCreated(), user.getType(), user.getToken());
        try {
            // save the User
        	userRepository.save(userUpdate);
            // success in creating
            return new ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_UPDATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }        
    }
    
    public ResponseDto deleteUser(String userId, String tokenString) {
    	//find user by id
    	User user = userRepository.findById(userId).get();
        if(!Helper.notNull(user)){
            throw  new CustomException("user not present");
        }
        
        Token token = tokenService.getToken(user);

        if(!Helper.notNull(token)) {
            // token not present
            throw new CustomException("token not present");
        }
        else if (token.getToken() != tokenString) {
        	throw new CustomException("wrong user");
        }
        
        try {
            // delete the User
        	userRepository.delete(user);
            // delete token in database
            tokenService.deleteToken(token);
            // success in creating
            return new ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_DELETED);
        } catch (Exception e) {
            // handle signup error
        	throw new CustomException(e.getMessage());
        }  
    }
    
    public ResponseDto sendEmailPasswordLost(ResetPasswordDto resetPasswordDto) {
        // first find User by email
        User user = userRepository.findByEmail(resetPasswordDto.getEmail()).get();
        if(!Helper.notNull(user)){
            throw  new CustomException("user not present");
        }
        
        try {
        	String msg = "Please click <a href='https://localhost:8080/#/?email=" + resetPasswordDto.getEmail() + "'>here</a> to reset your password";
            new EmailService("smtp.mail.com", 25, "user", "password")
              .sendMail("to@mail.com", resetPasswordDto.getEmail(), 
            		  "AuthApp Reset Password", msg);
            return new ResponseDto(ResponseStatus.success.toString(), MessageStrings.EMAIL_SENT);
        } catch (Exception e) {
        	throw new CustomException(e.getMessage());
        }
    }
    
    public ResponseDto lostPassword(ResetPasswordDto resetPasswordDto) {
        // first find User by email
        User user = userRepository.findByEmail(resetPasswordDto.getEmail()).get();
        if(!Helper.notNull(user)){
            throw  new CustomException("user not present");
        }
        
        user.setPassword(passwordEncoder().encode(resetPasswordDto.getPassword()));
        try {
        	userRepository.save(user);
        	return new ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_UPDATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        } 
    }
  
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
