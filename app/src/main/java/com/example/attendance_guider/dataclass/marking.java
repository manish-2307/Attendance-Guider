package com.example.attendance_guider.dataclass;

public class marking {

    private String email;
    private String date;
    private String subject_name;
    private String mark;

    public marking(String email, String date, String subject_name, String mark) {
        this.email = email;
        this.date = date;
        this.subject_name = subject_name;
        this.mark = mark;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getMark() {
        return mark;
    }
}
