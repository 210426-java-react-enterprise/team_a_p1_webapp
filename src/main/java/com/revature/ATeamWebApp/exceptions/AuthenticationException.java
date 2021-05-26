package com.revature.ATeamWebApp.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Could not authenticate using the provided credentials");
    }
}
