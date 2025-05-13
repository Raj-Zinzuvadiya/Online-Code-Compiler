package com.onlinecodecompiler.security.service;

import com.onlinecodecompiler.security.model.User;
import com.onlinecodecompiler.security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    final private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public User register(User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public String verify(User user)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));
        if(authentication.isAuthenticated())
        {
            return jwtService.generateToken(user.getEmailId());
        }
        return "fail";

    }
}
