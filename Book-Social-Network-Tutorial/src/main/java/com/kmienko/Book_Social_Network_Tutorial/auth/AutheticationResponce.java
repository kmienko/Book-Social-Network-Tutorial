package com.kmienko.Book_Social_Network_Tutorial.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AutheticationResponce {

    private String token;
}
