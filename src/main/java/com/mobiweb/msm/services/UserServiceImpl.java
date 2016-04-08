package com.mobiweb.msm.services;

import com.mobiweb.msm.models.User;
import com.mobiweb.msm.models.enums.Role;
import com.mobiweb.msm.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;


    @Override
    public void create(User user) {

        User userFromUsername = getUserFromUsername(user.getUsername());
        if (userFromUsername == null) {
            userRepo.save(user);
        } else {
            // username already exists
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
            return userRepo.save(userFromUsername);
        } catch (Exception e) {
            // user not updated
            throw new RuntimeException();
        }
    }

    @Override
    public User updateGcmID(String username, String gcmID) {


        try {
            User user = getUserFromUsername(username);
            if (user != null) {
                user.setRegId(gcmID);
                userRepo.save(user);
                return user;
            } else {
                //user doesn't exists
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw e;
        }


    }

    @Override
    public User retrieve(long id) {

        try {
            User User = userRepo.getOne(id);
            return User;
        } catch (Exception e) {
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

                // username doesnt exist exception
                return null;
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
                // throw username doesnt exists exception

                return null;
            }
        } catch (Exception e) {
            // username doesn't exists
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
