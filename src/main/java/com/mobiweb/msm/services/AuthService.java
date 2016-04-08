package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Auth;

import java.util.List;

public interface AuthService {

    Auth create(Auth Auth);

    void update(long id, Auth Auth);

    Auth retrieve(long id);

    List<Auth> retrieveByUsername(String usename);

    Auth delete(long id);

    Auth retrieveByAuth(String authToken);

}
