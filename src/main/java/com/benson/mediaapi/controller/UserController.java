package com.benson.mediaapi.controller;

import com.benson.mediaapi.model.User;
import com.benson.mediaapi.repository.UserRepository;
import com.benson.mediaapi.service.users.CunstomUserDetailsService;
import com.benson.mediaapi.service.users.UserService;
import com.benson.mediaapi.utils.AuthUtils;
import com.benson.mediaapi.utils.JwtUtils;
import com.benson.mediaapi.vo.AuthReqVO;
import com.benson.mediaapi.vo.AuthRespVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private static final Logger criticalLog = LoggerFactory.getLogger("CriticalLog");
    private static final Logger majorLog = LoggerFactory.getLogger("MajorLog");
    private static final Logger minorLog = LoggerFactory.getLogger("MinorLog");

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private CunstomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/token")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthReqVO user) throws Exception {
        String token = userService.login(user);
        AuthRespVO response = new AuthRespVO(token);
        criticalLog.error(token);
        majorLog.error(token);
        minorLog.error(token);
        return (ResponseEntity<?>) ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User userExist = userRepository.findByUsername(user.getUsername());
        Map<String, String> errMessage = new HashMap<>();
        errMessage.put("error", "username exist");
        if (userExist != null){
            return ResponseEntity.badRequest().body(errMessage);
        }
        boolean result = userService.register(user);
        if (!result){
            return ResponseEntity.badRequest().body("Fail");
        }
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("me")
    public ResponseEntity<?> getUserInfo(){
        int userId = AuthUtils.getUserId();
        Optional<User> user = userRepository.findById((long) userId);
        Map<String, String> userName = new HashMap<>();
        userName.put("name",user.get().getName());
        return ResponseEntity.ok(userName);
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
