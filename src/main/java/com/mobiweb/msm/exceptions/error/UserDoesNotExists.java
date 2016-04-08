package com.mobiweb.msm.exceptions.error;

public class UserDoesNotExists extends RuntimeException {

    public UserDoesNotExists(String message) {
        super(message);
    }
}
