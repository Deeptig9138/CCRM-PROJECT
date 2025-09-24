package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVService {
    private final AppConfig cfg = AppConfig.getInstance();

    // Create sample CSVs if missing
    public void createSampleDataIfMissing() throws IOException {
        Path data = cfg.getDataFolder();
        if (Files.notExists(data)) Files.createDirectories(data);

        Path students = data.resolve("students.csv");
        if (Files.notExists(students)) {
            List<String> lines = List.of(
                    "id,name,email,dob,regNo,program",
                    "S1,John Doe,john@example.com,2000-01-15,REG2001,Computer Science",
                    "S2,Jane Smith,jane@example.com,2001-05-30,REG2002,Electronics"
            );
            Files.write(students, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        }

        Path courses = data.resolve("courses.csv");
        if (Files.notExists(courses)) {
            List<String> lines = List.of(
                    "code,title,credits,department,semester,instructorId",
                    "CS101,Intro to CS,4,CS,SPRING,INST1",
                    "EE201,Circuits,3,EE,FALL,INST2"
            );
            Files.write(courses, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        }
    }

    // imports students
    public void importStudents(Path csvPath, StudentService studentService) throws IOException {
        if (Files.notExists(csvPath)) throw new NoSuchFileException(csvPath.toString());
        try (Stream<String> lines = Files.lines(csvPath, StandardCharsets.UTF_8)) {
            List<String> data = lines.skip(1).collect(Collectors.toList());
            for (String line : data) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                Student s = new Student(parts[0].trim(), parts[1].trim(), parts[2].trim(),
                        LocalDate.parse(parts[3].trim()), parts[4].trim(), parts[5].trim());
                try { studentService.addStudent(s); } catch (Exception ex) { /* skip duplicates */ }
            }
        }
    }

    // imports courses
    public void importCourses(Path csvPath, CourseService courseService) throws IOException {
        if (Files.notExists(csvPath)) throw new NoSuchFileException(csvPath.toString());
        try (Stream<String> lines = Files.lines(csvPath, StandardCharsets.UTF_8)) {
            List<String> data = lines.skip(1).collect(Collectors.toList());
            for (String line : data) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length < 6) continue;
                Semester sem = null;
                String semStr = p[4].trim();
                try { if (!semStr.isEmpty()) sem = Semester.valueOf(semStr.toUpperCase()); }
                catch (Exception ignored) {}
                Course c = new Course.Builder()
                        .courseCode(p[0].trim())
                        .title(p[1].trim())
                        .creditHours(Integer.parseInt(p[2].trim()))
                        .department(p[3].trim())
                        .semester(sem)
                        .instructorId(p[5].trim())
                        .build();
                try { courseService.addCourse(c); } catch (Exception ex) { /* skip duplicates */ }
            }
        }
    }

    // export students
    public Path exportStudents(StudentService ss) throws IOException {
        Path out = cfg.getExportFolder();
        if (Files.notExists(out)) Files.createDirectories(out);
        Path file = out.resolve("students_export.csv");
        String header = "id,name,email,dob,regNo,program";
        List<String> lines = ss.getAllStudents().stream()
                .map(s -> String.join(",",
                        s.getId(),
                        s.getName(),
                        s.getEmail(),
                        s.getDateOfBirth().toString(),
                        s.getRegNo(),
                        s.getProgram()))
                .collect(Collectors.toList());
        lines.add(0, header);
        Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return file;
    }

    // export courses
    public Path exportCourses(CourseService cs) throws IOException {
        Path out = cfg.getExportFolder();
        if (Files.notExists(out)) Files.createDirectories(out);
        Path file = out.resolve("courses_export.csv");
        String header = "code,title,credits,department,semester,instructorId";
        List<String> lines = cs.getAllCourses().stream()
                .map(c -> String.join(",",
                        c.getCourseCode(),
                        c.getTitle(),
                        String.valueOf(c.getCreditHours()),
                        c.getDepartment() == null ? "" : c.getDepartment(),
                        c.getSemester() == null ? "" : c.getSemester().name(),
                        c.getInstructorId() == null ? "" : c.getInstructorId()))
                .collect(Collectors.toList());
        lines.add(0, header);
        Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return file;
    }
}