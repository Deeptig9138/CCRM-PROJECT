package edu.ccrm.domain;

import java.time.LocalDate;

public class Instructor extends Person {
    private String department;
    private String title;

    public Instructor(String id, String name, String email, LocalDate dob, String department, String title) {
        super(id, name, email, dob);
        this.department = department;
        this.title = title;
    }

    @Override public String getRole() { return "Instructor"; }
    public String getDepartment() { return department; }
    public String getTitle() { return title; }
    public void setDepartment(String department) { this.department = department; }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Department=%s, Title=%s", department, title);
    }
}