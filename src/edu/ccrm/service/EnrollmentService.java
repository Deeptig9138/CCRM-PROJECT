package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final Map<String, Enrollment> enrollments = new HashMap<>();
    private final StudentService studentService;
    private final CourseService courseService;
    private final int maxCreditsPerSemester;

    public EnrollmentService(StudentService ss, CourseService cs) { this(ss, cs, 18); }
    public EnrollmentService(StudentService ss, CourseService cs, int maxCreditsPerSemester) {
        this.studentService = ss; this.courseService = cs; this.maxCreditsPerSemester = maxCreditsPerSemester;
    }

    public Enrollment enroll(String enrollmentId, String studentId, String courseCode, Semester semester)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        boolean exists = enrollments.values().stream()
                .anyMatch(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode)
                        && e.getSemester() == semester);
        if (exists) throw new DuplicateEnrollmentException("Already enrolled in course for semester.");

        int currentCredits = enrollments.values().stream()
                .filter(e -> e.getStudentId().equals(studentId) && e.getSemester() == semester)
                .mapToInt(e -> courseService.findByCode(e.getCourseCode()).map(Course::getCreditHours).orElse(0))
                .sum();
        int courseCredits = courseService.findByCode(courseCode).map(Course::getCreditHours).orElse(0);
        if (currentCredits + courseCredits > maxCreditsPerSemester)
            throw new MaxCreditLimitExceededException("Exceed max credits: " + maxCreditsPerSemester);

        Enrollment en = new Enrollment(enrollmentId, studentId, courseCode, semester);
        enrollments.put(enrollmentId, en);
        return en;
    }

    public void unenroll(String enrollmentId) { enrollments.remove(enrollmentId); }
    public List<Enrollment> findByStudent(String studentId) {
        return enrollments.values().stream().filter(e -> e.getStudentId().equals(studentId)).collect(Collectors.toList());
    }
    public void recordMarks(String enrollmentId, double marks) {
        Enrollment e = enrollments.get(enrollmentId);
        if (e == null) throw new IllegalArgumentException("Enrollment not found: " + enrollmentId);
        e.setGrade(Grade.fromMarks(marks));
    }
    public List<Enrollment> getAllEnrollments() { return new ArrayList<>(enrollments.values()); }

    public double computeGPAForStudent(String studentId) {
        List<Enrollment> studentEnrolls = enrollments.values().stream()
                .filter(e -> e.getStudentId().equals(studentId) && e.getGrade() != null)
                .collect(Collectors.toList());
        double totalPoints = 0.0; int totalCredits = 0;
        for (Enrollment e : studentEnrolls) {
            Course c = courseService.findByCode(e.getCourseCode()).orElse(null);
            if (c == null) continue;
            totalPoints += e.getGrade().getPoints() * c.getCreditHours();
            totalCredits += c.getCreditHours();
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public Map<Grade, Long> gradeDistribution() {
        return enrollments.values().stream().map(Enrollment::getGrade).filter(Objects::nonNull)
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));
    }

    public void displayEnrollments() { enrollments.values().forEach(System.out::println); }
}