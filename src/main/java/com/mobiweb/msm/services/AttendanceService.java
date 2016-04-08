package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Attendance;

public interface AttendanceService {

    void create(Attendance attendance);

    void update(long id, Attendance attendance);

    Attendance retrieve(long id);

    Attendance delete(long id);

    void markAttendance(String username);
}
