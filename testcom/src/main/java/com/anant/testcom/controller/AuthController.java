package com.anant.testcom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anant.testcom.config.JwtProvider;
import com.anant.testcom.exception.UserException;
import com.anant.testcom.model.User;
import com.anant.testcom.repository.UserRepository;
import com.anant.testcom.request.LoginRequest;
import com.anant.testcom.response.AuthResponse;
import com.anant.testcom.service.CustomUserServiceImplementation;


@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomUserServiceImplementation customUserServiceImpl;
	
	public AuthController(UserRepository userRepository,
			JwtProvider jwtProvider,PasswordEncoder passwordEncoder,
			CustomUserServiceImplementation customUserServiceImpl) {
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
		this.passwordEncoder=passwordEncoder;
		this.customUserServiceImpl=customUserServiceImpl;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException{
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		User isEmailExist = userRepository.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new UserException("Email is Already Used with another Account");
		}
		
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		User savedUser = userRepository.save(createdUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signup Success");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse>loginUserHanlder(@RequestBody LoginRequest loginRequest) throws UserException{
		
		String username=loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(token,"Signin Success");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username...");
		}
		if(!passwordEncoder.matches(password,userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password...");
		}
		return new UsernamePasswordAuthenticationToken(password, userDetails);
	}
}
