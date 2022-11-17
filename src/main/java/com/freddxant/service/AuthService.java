/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freddxant.service;

//import com.iconplus.sakti.model.HeaderConstant;
//import com.iconplus.sakti.model.ResponseStatusConstant;
//import com.iconplus.sakti.model.response.ResponseModel;
//import com.iconplus.sakti.properties.ApiKeyProperties;
import com.freddxant.properties.ApiKeyProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 *
 * @author ferddxant
 */
@Slf4j
@Service
public class AuthService implements UserDetailsService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ApiKeyProperties apiKeyProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("[SECURITY-SERVICE] check authentication");
        String password = "";
        try {
            Map<String, Object> map = new HashMap<>();
            log.debug("[SECURITY-SERVICE] validate password");
            if (username.equals(apiKeyProperties.getUsername())) {
                password = passwordEncoder.encode(apiKeyProperties.getPassword());
            }
        } catch (Exception e) {
            log.error("ERROR VALIDATE PASSWORD", e.getLocalizedMessage());
            e.printStackTrace();
        }

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("REGISTERED"));

        return new User(username, password, authorities);

    }
    
    
    
}
