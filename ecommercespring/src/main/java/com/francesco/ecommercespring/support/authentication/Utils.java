package com.francesco.ecommercespring.support.authentication;


import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@UtilityClass
public class Utils {

    public Jwt getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getToken();
        }
        throw new IllegalArgumentException("Invalid authentication type. Expected JwtAuthenticationToken.");
    }

    public String getAuthServerId() {
        Jwt jwt = getPrincipal();
        return jwt.getSubject();
    }

    public String getName() {
        Jwt jwt = getPrincipal();
        return jwt.getClaim("sub");
    }

    public String getUsername() {
        Jwt jwt = getPrincipal();
        return jwt.getClaimAsString("preferred_username");
    }

    public String getnome() {
        Jwt jwt = getPrincipal();
        return jwt.getClaimAsString("given_name");
    }

    public String getcognome() {
        Jwt jwt = getPrincipal();
        return jwt.getClaimAsString("family_name");
    }




    public String getEmail() {
        Jwt jwt = getPrincipal();
        return jwt.getClaimAsString("email");
    }
}
