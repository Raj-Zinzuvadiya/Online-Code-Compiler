package com.onlinecodecompiler.security.service;

import com.onlinecodecompiler.security.model.User;
import com.onlinecodecompiler.security.model.UserPrincipal;
import com.onlinecodecompiler.security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        User user = userRepo.findByEmailId(emailId);
        System.out.println(user);
        if(user == null){
            throw new UsernameNotFoundException("User Not found");
        }
        return new UserPrincipal(user);


    }
}
