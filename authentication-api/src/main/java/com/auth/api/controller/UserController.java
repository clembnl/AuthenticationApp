package com.auth.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.api.dto.ResetPasswordDto;
import com.auth.api.dto.ResponseDto;
import com.auth.api.dto.SignInDto;
import com.auth.api.dto.SignInResponseDto;
import com.auth.api.dto.SignUpDto;
import com.auth.api.exception.CustomException;
import com.auth.api.model.User;
import com.auth.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public List<User> fetchAllUsers(@RequestParam("token") String token) throws CustomException {
		return userService.getAllUsers(token);
	}
	
	@PostMapping("/resetpassword")
	public ResponseDto resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws CustomException {
		return userService.sendEmailPasswordLost(resetPasswordDto);
		
	}
	
	@PostMapping("/setpasswordreset")
	public ResponseDto setPasswordReset(@RequestBody ResetPasswordDto resetPasswordDto) throws CustomException {
		return userService.lostPassword(resetPasswordDto);
	}
	
	@PostMapping("/signup")
	public ResponseDto signup(@RequestBody SignUpDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }
	
    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }
    
    @PutMapping("/update/{userID}")
    public ResponseDto update(@PathVariable("userID") String userId,
    								@RequestBody SignInDto signInDto,
    								@RequestParam("token") String token) throws CustomException {
    	return userService.updateUser(userId, signInDto, token);
    }
    
    @DeleteMapping("/delete/{userID}")
    public ResponseDto deleteUser(@PathVariable("userID") String userId,
    											  @RequestParam("token") String token) throws CustomException {
    	return userService.deleteUser(userId, token);
    }

}
