package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Auth;
import com.mobiweb.msm.repositories.AuthRepo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    AuthRepo authRepo;

    @Override
    public Auth create(Auth auth) {

        try {
            auth.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
            auth.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            return authRepo.save(auth);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
