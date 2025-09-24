package edu.ccrm.service;

import edu.ccrm.domain.Course;
import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private final Map<String, Course> courses = new HashMap<>();
    public void addCourse(Course c) {
        if (courses.containsKey(c.getCourseCode())) throw new IllegalArgumentException("Course exists: " + c.getCourseCode());
        courses.put(c.getCourseCode(), c);
    }
    public Optional<Course> findByCode(String code) { return Optional.ofNullable(courses.get(code)); }
    public List<Course> getAllCourses() { return new ArrayList<>(courses.values()); }
    public void removeCourse(String code) { courses.remove(code); }
    public List<Course> filterByInstructor(String instructorId) {
        return courses.values().stream()
                .filter(c -> instructorId != null && instructorId.equals(c.getInstructorId()))
                .collect(Collectors.toList());
    }
    public List<Course> filterByDepartment(String department) {
        return courses.values().stream()
                .filter(c -> department != null && department.equalsIgnoreCase(c.getDepartment()))
                .collect(Collectors.toList());
    }
    public List<Course> filterBySemester(String semesterName) {
        return courses.values().stream()
                .filter(c -> c.getSemester() != null && c.getSemester().name().equalsIgnoreCase(semesterName))
                .collect(Collectors.toList());
    }
    public void displayAllCourses() { courses.values().forEach(System.out::println); }
}