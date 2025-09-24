package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.service.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class MainApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final AppConfig cfg = AppConfig.getInstance();

    // Services
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
    private static final CSVService csvService = new CSVService();
    private static final BackupService backupService = new BackupService();

    public static void main(String[] args) {
        System.out.println("Welcome to Campus Course & Records Manager (CCRM)");
        System.out.println("Note: Assertions enabled? Run with -ea to activate assertion checks.");

        // ensure data folder and sample CSVs exist; load data
        try {
            csvService.createSampleDataIfMissing();
            csvService.importStudents(cfg.getDataFolder().resolve("students.csv"), studentService);
            csvService.importCourses(cfg.getDataFolder().resolve("courses.csv"), courseService);
            // ensure export folder exists (so backup can copy)
            if (java.nio.file.Files.notExists(cfg.getExportFolder())) java.nio.file.Files.createDirectories(cfg.getExportFolder());
        } catch (IOException e) {
            System.out.println("Initialization error: " + e.getMessage());
        }

        // Anonymous inner class: shutdown hook to export on exit
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    csvService.exportStudents(studentService);
                    csvService.exportCourses(courseService);
                    System.out.println("\n[ShutdownHook] Exported students and courses on exit.");
                } catch (Exception e) {
                    System.out.println("\n[ShutdownHook] Failed to export on exit: " + e.getMessage());
                }
            }
        }));

        // Labeled loop used once
        mainLoop:
        while (true) {
            printMainMenu();
            int choice = readInt();
            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> courseMenu();
                case 3 -> enrollmentMenu();
                case 4 -> ioMenu();
                case 5 -> reportsMenu();
                case 9 -> { // quick help: show campus note
                    System.out.println("Platform note: Java SE is for desktop/server apps; Java EE (Jakarta EE) is for enterprise servers; Java ME is for embedded/IoT.");
                }
                case 0 -> {
                    System.out.println("Exiting program. (Exports will run automatically).");
                    break mainLoop; // labeled break
                }
                default -> System.out.println("Invalid choice");
            }
        }

        // close scanner (shutdown hook still runs)
        sc.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Enrollments & Grades");
        System.out.println("4. Import/Export & Backup");
        System.out.println("5. Reports");
        System.out.println("9. Platform note (ME vs SE vs EE)");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    /* ---------- Students ---------- */
    private static void studentMenu() {
        System.out.println("\n--- Student Menu ---");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Deactivate Student");
        System.out.println("0. Back");
        int ch = readInt();
        switch (ch) {
            case 1 -> addStudent();
            case 2 -> studentService.displayAllStudents();
            case 3 -> {
                System.out.print("Enter student id: ");
                String id = sc.nextLine().trim();
                studentService.deactivateStudent(id);
                System.out.println("Deactivated (if existed).");
            }
            case 0 -> {}
            default -> System.out.println("Invalid");
        }
    }

    private static void addStudent() {
        try {
            System.out.print("ID: "); String id = sc.nextLine().trim();
            System.out.print("Name: "); String name = sc.nextLine().trim();
            System.out.print("Email: "); String email = sc.nextLine().trim();
            System.out.print("DOB (YYYY-MM-DD): "); LocalDate dob = LocalDate.parse(sc.nextLine().trim());
            System.out.print("RegNo: "); String reg = sc.nextLine().trim();
            System.out.print("Program: "); String program = sc.nextLine().trim();
            var s = new Student(id, name, email, dob, reg, program);
            studentService.addStudent(s);
            System.out.println("Student added.");
        } catch (Exception e) { System.out.println("Failed to add student: " + e.getMessage()); }
    }

    /* ---------- Courses ---------- */
    private static void courseMenu() {
        System.out.println("\n--- Course Menu ---");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("0. Back");
        int ch = readInt();
        switch (ch) {
            case 1 -> addCourse();
            case 2 -> courseService.displayAllCourses();
            case 0 -> {}
            default -> System.out.println("Invalid");
        }
    }

    private static void addCourse() {
        try {
            System.out.print("Course Code: "); String code = sc.nextLine().trim();
            System.out.print("Title: "); String title = sc.nextLine().trim();
            System.out.print("Credits: "); int credits = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Department: "); String dept = sc.nextLine().trim();
            System.out.print("Semester (SPRING/SUMMER/FALL) or blank: "); String semS = sc.nextLine().trim();
            Semester sem = semS.isEmpty() ? null : Semester.valueOf(semS.toUpperCase());
            System.out.print("InstructorId (optional): "); String instr = sc.nextLine().trim();
            Course c = new Course.Builder()
                    .courseCode(code).title(title).creditHours(credits)
                    .department(dept).semester(sem).instructorId(instr.isEmpty() ? null : instr)
                    .build();
            courseService.addCourse(c);
            System.out.println("Course added.");
        } catch (Exception e) { System.out.println("Failed to add course: " + e.getMessage()); }
    }

    /* ---------- Enrollments ---------- */
    private static void enrollmentMenu() {
        System.out.println("\n--- Enrollment Menu ---");
        System.out.println("1. Enroll student");
        System.out.println("2. Unenroll (by enrollment id)");
        System.out.println("3. Record marks for enrollment");
        System.out.println("4. View All Enrollments");
        System.out.println("0. Back");
        int ch = readInt();
        switch (ch) {
            case 1 -> enrollStudent();
            case 2 -> {
                System.out.print("Enrollment id to remove: "); String id = sc.nextLine().trim();
                enrollmentService.unenroll(id); System.out.println("Removed (if existed).");
            }
            case 3 -> {
                System.out.print("Enrollment id: "); String id = sc.nextLine().trim();
                System.out.print("Marks (0-100): "); double marks = Double.parseDouble(sc.nextLine().trim());
                try { enrollmentService.recordMarks(id, marks); System.out.println("Marks recorded."); }
                catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
            }
            case 4 -> enrollmentService.displayEnrollments();
            case 0 -> {}
            default -> System.out.println("Invalid");
        }
    }

    private static void enrollStudent() {
        try {
            System.out.print("Enrollment Id: "); String eid = sc.nextLine().trim();
            System.out.print("Student Id: "); String sid = sc.nextLine().trim();
            System.out.print("Course Code: "); String cc = sc.nextLine().trim();
            System.out.print("Semester (SPRING/SUMMER/FALL): "); Semester sem = Semester.valueOf(sc.nextLine().trim().toUpperCase());
            Enrollment en = enrollmentService.enroll(eid, sid, cc, sem);
            System.out.println("Enrolled: " + en);
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException dex) {
            System.out.println("Business rule: " + dex.getMessage());
        } catch (Exception e) {
            System.out.println("Failed to enroll: " + e.getMessage());
        }
    }

    /* ---------- IO & Backup ---------- */
    private static void ioMenu() {
        System.out.println("\n--- IO Menu ---");
        System.out.println("1. Import students from CSV (data/students.csv)");
        System.out.println("2. Import courses from CSV (data/courses.csv)");
        System.out.println("3. Export students & courses to data/export/");
        System.out.println("4. Create backup of export folder");
        System.out.println("5. List backups");
        System.out.println("0. Back");
        int ch = readInt();
        switch (ch) {
            case 1 -> {
                try { csvService.importStudents(cfg.getDataFolder().resolve("students.csv"), studentService); System.out.println("Import done."); }
                catch (IOException e) { System.out.println("Import failed: " + e.getMessage()); }
            }
            case 2 -> {
                try { csvService.importCourses(cfg.getDataFolder().resolve("courses.csv"), courseService); System.out.println("Import done."); }
                catch (IOException e) { System.out.println("Import failed: " + e.getMessage()); }
            }
            case 3 -> {
                try {
                    Path s = csvService.exportStudents(studentService);
                    Path c = csvService.exportCourses(courseService);
                    System.out.println("Exported to: " + s + " and " + c);
                } catch (IOException e) { System.out.println("Export failed: " + e.getMessage()); }
            }
            case 4 -> {
                try {
                    Path backup = backupService.createBackup();
                    long size = backupService.getBackupSize(backup);
                    System.out.println("Backup created at: " + backup);
                    System.out.println("Backup size (bytes): " + size);
                } catch (IOException e) { System.out.println("Backup failed: " + e.getMessage()); }
            }
            case 5 -> {
                try { backupService.listBackups(); } catch (IOException e) { System.out.println("Failed to list: " + e.getMessage()); }
            }
            case 0 -> {}
            default -> System.out.println("Invalid");
        }
    }

    /* ---------- Reports ---------- */
    private static void reportsMenu() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. GPA for a student");
        System.out.println("2. Grade distribution");
        System.out.println("0. Back");
        int ch = readInt();
        switch (ch) {
            case 1 -> {
                System.out.print("Student id: "); String sid = sc.nextLine().trim();
                double gpa = enrollmentService.computeGPAForStudent(sid);
                System.out.println("GPA: " + String.format("%.3f", gpa));
            }
            case 2 -> {
                Map<Grade, Long> dist = enrollmentService.gradeDistribution();
                dist.forEach((g, c) -> System.out.println(g + " -> " + c));
            }
            case 0 -> {}
            default -> System.out.println("Invalid");
        }
    }

    private static int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { return -1; }
    }
}