package com.example.attendance_guider.dataclass;

public class subject {

    private String name;
    private String attended;
    private String total;
    private String email;
    private String attendance;
    private String lecture;
    private String criteria;

    public subject(String name, String attended, String total, String email, String attendance,String lecture,String criteria) {
        this.name = name;
        this.attended = attended;
        this.total = total;
        this.email = email;
        this.attendance = attendance;
        this.lecture=lecture;
        this.criteria=criteria;
    }



    public String getName() {
        return name;
    }

    public String getAttended() {
        return attended;
    }

    public String getTotal() {
        return total;
    }

    public String getEmail() {
        return email;
    }

    public String getAttendance() {
        return attendance;
    }

    public String getLecture() {
        return lecture;
    }

    public String getCriteria() {
        return criteria;
    }



}
