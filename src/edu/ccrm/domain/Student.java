package edu.ccrm.domain;

import java.time.LocalDate;

public class Student extends Person {
    private String regNo;
    private String program;
    private boolean active = true;

    public Student(String id, String name, String email, LocalDate dob, String regNo, String program) {
        super(id, name, email, dob);
        this.regNo = regNo;
        this.program = program;
    }

    @Override public String getRole() { return "Student"; }
    public String getRegNo() { return regNo; }
    public String getProgram() { return program; }
    public boolean isActive() { return active; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public void setProgram(String program) { this.program = program; }
    public void deactivate() { this.active = false; }

    @Override
    public String toString() {
        return super.toString() + String.format(", RegNo=%s, Program=%s, Active=%s", regNo, program, active);
    }
}