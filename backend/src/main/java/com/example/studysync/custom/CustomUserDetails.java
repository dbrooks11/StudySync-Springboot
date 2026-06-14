package com.example.studysync.custom;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class CustomUserDetails extends User {

    private final Long id;

    public CustomUserDetails(Long id, String email, String password){
        super(email, password, Collections.emptyList());
        this.id = id;
    }
}
