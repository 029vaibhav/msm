package com.mobiweb.msm.exceptions;

public class DuplicateUser extends RuntimeException {

    public DuplicateUser(String message) {
        super(message);
    }
}
