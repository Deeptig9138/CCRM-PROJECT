package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.*;

public class StudentService {
    private final Map<String, Student> students = new HashMap<>();
    public void addStudent(Student s) {
        if (students.containsKey(s.getId())) throw new IllegalArgumentException("Student with ID exists: " + s.getId());
        students.put(s.getId(), s);
    }
    public Optional<Student> findById(String id) { return Optional.ofNullable(students.get(id)); }
    public List<Student> getAllStudents() { return new ArrayList<>(students.values()); }
    public void deactivateStudent(String id) { Student s = students.get(id); if (s != null) s.deactivate(); }
    public void removeStudent(String id) { students.remove(id); }
    public void displayAllStudents() { students.values().forEach(System.out::println); }
}