package com.benson.mediaapi.service.users;


import com.benson.mediaapi.model.User;
import com.benson.mediaapi.repository.UserRepository;
import com.benson.mediaapi.utils.JwtUtils;
import com.benson.mediaapi.vo.AuthReqVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;



//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Override
    public String login(AuthReqVO authReqVO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReqVO.getUsername(), authReqVO.getPassword())
            );
        } catch (AuthenticationException e) {
            return "Invalid username/password";
        } catch (Exception e){
            return e.getMessage();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = JwtUtils.generateToken(authReqVO.getUsername());
        return token;
    }

    @Override
    public Boolean register(User user) {
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        User userSaved = userRepository.save(user);
        return userSaved.getId() != null;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
