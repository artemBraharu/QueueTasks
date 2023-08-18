package com.example.queue1.auth.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String login;
    private String password;
}
