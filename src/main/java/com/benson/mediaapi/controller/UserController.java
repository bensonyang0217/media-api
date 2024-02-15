package com.benson.mediaapi.controller;

import com.benson.mediaapi.model.User;
import com.benson.mediaapi.service.users.CunstomUserDetailsService;
import com.benson.mediaapi.service.users.UserService;
import com.benson.mediaapi.utils.JwtUtils;
import com.benson.mediaapi.vo.AuthReqVO;
import com.benson.mediaapi.vo.AuthRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private CunstomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/token")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthReqVO user) throws Exception {
        String token = userService.login(user);
        AuthRespVO response = new AuthRespVO(token);
        return (ResponseEntity<?>) ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean result = userService.register(user);
        if (result !=true){
            return ResponseEntity.badRequest().body("Fail");
        }
        return ResponseEntity.ok("User registered successfully");
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
