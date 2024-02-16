package com.benson.mediaapi.utils;

import com.benson.mediaapi.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static final int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            return Math.toIntExact(((CustomUserDetails) authentication.getPrincipal()).getUserId());
        }
        return -1;
    }
}
