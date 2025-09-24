package edu.ccrm.domain;

public class Course {
    private final String courseCode;
    private final String title;
    private final int creditHours;
    private final String instructorId;
    private final Semester semester;
    private final String department;

    private Course(Builder b) {
        this.courseCode = b.courseCode;
        this.title = b.title;
        this.creditHours = b.creditHours;
        this.instructorId = b.instructorId;
        this.semester = b.semester;
        this.department = b.department;
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCreditHours() { return creditHours; }
    public String getInstructorId() { return instructorId; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format("Course[Code=%s, Title=%s, Credits=%d, Dept=%s, Sem=%s, Instr=%s]",
                courseCode, title, creditHours,
                department == null ? "N/A" : department,
                semester == null ? "N/A" : semester.name(),
                instructorId == null ? "N/A" : instructorId);
    }

    public static class Builder {
        private String courseCode;
        private String title;
        private int creditHours;
        private String instructorId;
        private Semester semester;
        private String department;

        public Builder courseCode(String c) { this.courseCode = c; return this; }
        public Builder title(String t) { this.title = t; return this; }
        public Builder creditHours(int ch) { this.creditHours = ch; return this; }
        public Builder instructorId(String id) { this.instructorId = id; return this; }
        public Builder semester(Semester s) { this.semester = s; return this; }
        public Builder department(String d) { this.department = d; return this; }

        public Course build() {
            assert courseCode != null && !courseCode.isEmpty() : "courseCode required";
            assert creditHours > 0 : "creditHours must be > 0";
            return new Course(this);
        }
    }
}