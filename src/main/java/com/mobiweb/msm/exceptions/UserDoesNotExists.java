package com.mobiweb.msm.exceptions;

public class UserDoesNotExists extends RuntimeException {

    public UserDoesNotExists(String message) {
        super(message);
    }
}
