package com.thomas.thomas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AuthenticationRequest {
    String email;
    String password;
}
