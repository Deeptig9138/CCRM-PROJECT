package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final String enrollmentId;
    private final String studentId;
    private final String courseCode;
    private final Semester semester;
    private Grade grade;
    private final LocalDateTime enrollmentDate;

    public Enrollment(String enrollmentId, String studentId, String courseCode, Semester semester) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
        this.enrollmentDate = LocalDateTime.now();
    }

    public String getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public Semester getSemester() { return semester; }
    public Grade getGrade() { return grade; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setGrade(Grade grade) { this.grade = grade; }

    @Override
    public String toString() {
        return String.format("Enrollment[id=%s, student=%s, course=%s, sem=%s, grade=%s, date=%s]",
                enrollmentId, studentId, courseCode, semester, grade, enrollmentDate);
    }
}