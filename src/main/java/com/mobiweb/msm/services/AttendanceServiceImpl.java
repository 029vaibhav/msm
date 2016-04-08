package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Attendance;
import com.mobiweb.msm.models.User;
import com.mobiweb.msm.repositories.AttendanceRepo;
import com.mobiweb.msm.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    UserRepo userRepo;


    @Override
    public void create(Attendance attendance) {

        attendanceRepo.save(attendance);
    }



    @Override
    public void update(long id, Attendance attendance) {

    }

    @Override
    public Attendance retrieve(long id) {

        try {
            Attendance attendance = attendanceRepo.getOne(id);
            return attendance;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Attendance delete(long id) {

        try {
            Attendance attendance = attendanceRepo.getOne(id);
            attendanceRepo.delete(id);
            return attendance;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void markAttendance(String username) {

        User oneByUsername = userRepo.findOneByUsername(username);

    }
}
