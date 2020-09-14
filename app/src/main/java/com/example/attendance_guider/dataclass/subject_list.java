package com.example.attendance_guider.dataclass;

public class subject_list {

    private String name;
    private String attended;
    private String total;
    private String email;

    public subject_list(String name, String attended, String total, String email) {
        this.name = name;
        this.attended = attended;
        this.total = total;
        this.email = email;
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
}
