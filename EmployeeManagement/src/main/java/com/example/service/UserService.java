package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.model.User;
import com.example.repo.UserRepo;

@Service
public class UserService {
    
//    private final UserRepo userRepo;
//    private final PasswordEncoder passwordEncoder; // ✅ Inject PasswordEncoder

//    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
//        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
	@Autowired
	UserRepo userRepo;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public boolean saveUser(User user) {
        // ✅ Encode the password before saving
        user.setPassword(encoder.encode(user.getPassword()));

        userRepo.save(user);
        return true;
    }
}
