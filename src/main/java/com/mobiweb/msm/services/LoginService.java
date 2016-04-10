package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Auth;
import com.mobiweb.msm.models.LoginDetails;

public interface LoginService {

    LoginDetails getLoginDetails(String username);

    Auth getAuthToken(String username, String password);

    void logOut(String username);

    boolean verifyToken();

}
