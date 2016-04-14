package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Message;
import com.mobiweb.msm.models.User;
import com.mobiweb.msm.models.enums.Role;
import com.mobiweb.msm.repositories.MessageRepo;
import com.mobiweb.msm.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserService userService;


    @Override
    public void create(Message message) {

        try {
            message.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
            message.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            Message save = messageRepo.save(message);
            sendNotification(save.getMessage(), save);

        } catch (Exception e) {
            throw e;
        }
    }


    private void sendNotification(String message, Message messageObject) {
        List<User> allUserByRole = userService.getAllUserByRole(messageObject.getRole());
        List<String> registrationIDS = new ArrayList<>();
        allUserByRole.stream().forEach(user -> {
            if (user.getRegId() != null)
                registrationIDS.add(user.getRegId());
        });
        if (registrationIDS.size() > 0) {
            Constants.SendMessageToGCM(registrationIDS, message);
        }
    }

    @Override
    public void update(Message Message) {

    }

    @Override
    public Message retrieve(long id) {

        try {
            Message Message = messageRepo.getOne(id);
            return Message;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Message delete(long id) {

        try {
            Message Message = messageRepo.getOne(id);
            messageRepo.delete(id);
            return Message;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Message> getMessageByRole(Role role) {

        return messageRepo.findAllByRole(role);
    }
}
