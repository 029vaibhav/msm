package com.mobiweb.msm.services;

import com.mobiweb.msm.exceptions.DuplicateUser;
import com.mobiweb.msm.exceptions.error.ErrorMessage;
import com.mobiweb.msm.exceptions.error.UserDoesNotExists;
import com.mobiweb.msm.models.User;
import com.mobiweb.msm.models.enums.Role;
import com.mobiweb.msm.repositories.UserRepo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    UserRepo userRepo;

    @Override
    public void create(User user) {

        User oneByUsername = userRepo.findOneByUsername(user.getUsername());
        if (oneByUsername == null) {
            user.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
            user.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            userRepo.save(user);
        } else {
            throw new DuplicateUser(ErrorMessage.DUPLICATE_USER);
        }
    }

    @Override
    public void update(User User) {

    }

    @Override
    public User patch(String accountId, HashMap<String, String> data) {

        User userFromUsername = getUserFromUsername(accountId);
        System.out.println(userFromUsername.getUsername());
        try {
            if (userFromUsername == null) {
                // user doesnt exists
                throw new RuntimeException();
            }
            Iterator i = data.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry data2 = (Map.Entry) i.next();
                Field field = User.class.getField((String) data2.getKey());
                field.set(userFromUsername, data2.getValue());
            }
            userFromUsername.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            return userRepo.save(userFromUsername);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public User updateGcmID(String username, String gcmID) {
        try {
            User user = getUserFromUsername(username);
            if (user != null) {
                user.setRegId(gcmID);
                user.setModified(DateTime.now().withZone(DateTimeZone.UTC));
                userRepo.save(user);
                return user;
            } else {
                //user doesn't exists
                throw new UserDoesNotExists(ErrorMessage.N0_USER);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }


    }

    @Override
    public User retrieve(long id) {

        try {
            User User = userRepo.getOne(id);
            return User;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @Override
    public User delete(long id) {

        try {
            User User = userRepo.getOne(id);
            userRepo.delete(id);
            return User;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User deleteUsername(String username) {

        try {
            User oneByUsername = getUserFromUsername(username);
            if (oneByUsername != null) {
                oneByUsername.setDeleted(1);
                userRepo.save(oneByUsername);
                return oneByUsername;
            } else {
                throw new UserDoesNotExists(ErrorMessage.N0_USER);
            }

        } catch (Exception e) {
            throw e;
            // username doesn't exists
        }

    }

    @Override
    public User getUserFromUsername(String username) {

        try {
            User oneByUsername = userRepo.findOneByUsername(username);
            if (oneByUsername != null) {
                return oneByUsername;
            } else {
                throw new UserDoesNotExists(ErrorMessage.N0_USER);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAllByDeleted(0);
    }

    @Override
    public List<User> getAllUserByRole(Role role) {
        return userRepo.findAllByRoleAndDeleted(role, 0);
    }
}
