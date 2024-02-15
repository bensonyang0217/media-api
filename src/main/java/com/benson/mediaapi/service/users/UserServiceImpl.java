package com.benson.mediaapi.service.users;


import com.benson.mediaapi.model.User;
import com.benson.mediaapi.repository.UserRepository;
import com.benson.mediaapi.vo.AuthReqVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Override
    public String login(AuthReqVO authReqVO) {
        return null;
    }

    @Override
    public Boolean register(User user) {
        String encryptedPassword = "test";
        user.setPassword(encryptedPassword);
        User userSaved = userRepository.save(user);
        return userSaved.getId() != null;
    }

}
