package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
    private final String id; // immutable
    private String name;
    private String email;
    private LocalDate dateOfBirth;

    public Person(String id, String name, String email, LocalDate dateOfBirth) {
        assert id != null && !id.isEmpty() : "ID cannot be null or empty";
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public abstract String getRole();
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    @Override
    public String toString() {
        return String.format("%s [ID=%s, Name=%s, Email=%s, DOB=%s]",
                getRole(), id, name, email, dateOfBirth);
    }
}