package com.mobiweb.msm.services;

import com.mobiweb.msm.models.User;
import com.mobiweb.msm.models.enums.Role;

import java.util.HashMap;
import java.util.List;

public interface UserService {

    void create(User User);

    void update(User User);

    User updateGcmID(String username, String gcmID);

    User retrieve(long id);

    User delete(long id);

    User deleteUsername(String username);

    User getUserFromUsername(String username);

    List<User> getAllUser();

    List<User> getAllUserByRole(Role role);

    User patch(String accountId, HashMap<String, String> data);
}

