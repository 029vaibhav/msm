package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Message;
import com.mobiweb.msm.models.enums.Role;

import java.util.List;

public interface MessageService {

    void create(Message Message);

    void update(Message Message);

    Message retrieve(long id);

    Message delete(long id);

    List<Message> getMessageByRole(Role role);
}
