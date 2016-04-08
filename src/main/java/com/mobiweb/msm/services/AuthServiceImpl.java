package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Auth;
import com.mobiweb.msm.repositories.AuthRepo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthRepo authRepo;


    @Override
    public Auth create(Auth auth) {

        auth.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
        auth.setModified(DateTime.now().withZone(DateTimeZone.UTC));
        return authRepo.save(auth);
    }

    @Override
    public void update(long id, Auth Auth) {

    }

    @Override
    public Auth retrieve(long id) {

        try {
            Auth Auth = authRepo.getOne(id);
            return Auth;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public List<Auth> retrieveByUsername(String username) {

        return authRepo.findAllByUsername(username);

    }

    @Override
    public Auth delete(long id) {

        try {
            Auth Auth = authRepo.getOne(id);
            authRepo.delete(id);
            return Auth;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Auth retrieveByAuth(String authToken) {

        Auth auth = authRepo.findOneByAuth(authToken);
        if (auth != null) {
            return auth;
        }
        return null;
    }
}
