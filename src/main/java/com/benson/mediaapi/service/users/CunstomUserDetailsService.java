package com.benson.mediaapi.service.users;

import com.benson.mediaapi.config.CustomUserDetails;
import com.benson.mediaapi.model.User;
import com.benson.mediaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CunstomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User user = userRepository.findByUsername(username);
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(user.getId());
        userDetails.setUsername(username);
        userDetails.setPassword(user.getPassword());
        return userDetails;
    }
}
