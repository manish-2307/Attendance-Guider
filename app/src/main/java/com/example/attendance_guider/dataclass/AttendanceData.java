package com.example.attendance_guider.dataclass;

public class AttendanceData {

    String email;
    String subject_name;
    Float attendance;

    public AttendanceData(String email,String subject_name, Float attendance) {
        this.email=email;
        this.subject_name = subject_name;
        this.attendance = attendance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public void setAttendance(Float attendance) {
        this.attendance = attendance;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public Float getAttendance() {
        return attendance;
    }
}
