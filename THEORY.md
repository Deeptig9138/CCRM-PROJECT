# 📘 Campus Course & Records Manager (CCRM) – Theory Documentation

This document covers all theoretical requirements for the **CCRM Java SE Project**.  
It includes explanations, comparisons, and mapping of project concepts to the syllabus.

---

## **1. Evolution of Java**
Java has evolved significantly since its introduction. Below is a short timeline:

| Year | Version | Key Features |
|------|----------|--------------|
| **1995** | Java 1.0 | Initial release by Sun Microsystems. |
| **1997** | Java 1.1 | Inner classes, JDBC, JavaBeans. |
| **1999** | Java 1.2 (Java 2) | Swing GUI, Collections Framework. |
| **2004** | Java 5.0 | Generics, Annotations, Enhanced for-loop, Enums. |
| **2011** | Java 7 | NIO.2, try-with-resources, dynamic language support. |
| **2014** | Java 8 | Lambda expressions, Streams API, Date/Time API. |
| **2017** | Java 9 | Modular system (Project Jigsaw). |
| **2019** | Java 11 | HTTP Client, Local variable syntax for lambda. |
| **2021** | Java 17 (LTS) | Pattern matching, sealed classes. |
| **2025** | Java 22+ | Continuous improvements in performance and features. |

---

## **2. Java Editions – ME vs SE vs EE**

| **Feature**       | **Java ME (Micro Edition)**                | **Java SE (Standard Edition)**         | **Java EE (Enterprise Edition)**           |
|--------------------|-------------------------------------------|----------------------------------------|-------------------------------------------|
| **Purpose**        | Embedded & mobile devices.                 | General-purpose desktop & console apps. | Large-scale, enterprise web applications. |
| **Devices**        | Mobile phones, IoT devices.                | PCs, laptops, basic systems.            | Servers, cloud systems.                    |
| **APIs Included**  | Limited libraries.                         | Full core APIs (Collections, Streams, etc.). | Includes SE + advanced APIs like Servlets, JSP, EJB. |
| **Example Usage**  | Smart appliances, small mobile apps.       | Our **CCRM console app**, desktop apps. | Banking systems, e-commerce platforms.    |

**Our project uses Java SE.**

---

## **3. Java Architecture: JDK, JRE, JVM**

### **JDK (Java Development Kit)**
- Tools needed for **developing** Java applications.
- Includes compiler (`javac`), JRE, and debugging tools.

### **JRE (Java Runtime Environment)**
- Provides environment to **run** Java programs.
- Contains JVM + core libraries.

### **JVM (Java Virtual Machine)**
- Converts bytecode into machine code.
- Platform-independent → "Write once, run anywhere."

**Interaction:**
Source Code (.java) → Compiler → Bytecode (.class) → JVM → Machine Code

---

## **4. Installing & Configuring Java on Windows**

**Steps:**
1. Download **JDK** from [Oracle's official site](https://www.oracle.com/java/technologies/downloads/).
2. Install by running the downloaded `.exe` file.
3. Set **Environment Variables**:
   - Add JDK `bin` folder path to **PATH**.
   - Example: `C:\Program Files\Java\jdk-17\bin`.
4. Verify installation:
   ```bash
   java -version

---

## **5. Using Eclipse IDE**

**Steps to create and run a new project:**
1. Open Eclipse → File → New → Java Project.
2. Give project name (e.g., CCRM).
3. Create packages:
   ```bash
   edu.ccrm.cli
   edu.ccrm.domain
   edu.ccrm.service
   edu.ccrm.io
   edu.ccrm.util
   edu.ccrm.config
4. Create the Main.java class inside cli.

Run project: Run → Run Configurations → Select Main Class → Run.

--- 

## **6. Errors vs Exceptions**
| **Errors** |	**Exceptions** | 
|------------|----------------|
| Critical problems, not recoverable. | Issues that can be handled gracefully. |
| Caused by system-level failures. | Caused by incorrect user input or logic. |
| Example: OutOfMemoryError |	Example: NullPointerException, IOException. |

***In our project:***
- Errors: Handled by JVM, not part of the code.
- Exceptions: DuplicateEnrollmentException & MaxCreditLimitExceededException

---

## **7. Assertions in Java**
Assertions verify assumptions made by the program.

Syntax:
```bash
assert condition : "Error message if false";
```
Enabling Assertions:
```bash
java -ea Main
```
Example:
```bash
assert student != null : "Student cannot be null";
```

---

## **8. Interfaces vs Abstract Classes**
| **Aspect** | **Interface** | **Abstract Class** |
|------------|---------------|--------------------|
| Multiple inheritance	| ✅ Yes	| ❌ No |
| Contains variables	| Only `public static final`	| Instance + static variables |
| Use case	| Define capabilities (e.g., Searchable)	| Share common behavior (e.g., Person) | 
| Example in project	| `Searchable` | `Person` | 

---

## **9. Mapping Table – Syllabus Topics to Code**
| **Topic** | **File/Class/Method** |
|-----------|-----------------------|
| Encapsulation | `Student.java`, private fields + getters/setters |
| Inheritance	| `Person.java → Student.java, Instructor.java` |
| Abstraction	| `Person.java` (abstract methods) |
| Polymorphism	| `TranscriptService.java` |
| Interfaces	| `Searchable.java`, `Persistable.java` |
| Nested Classes	| `Course.java` (static nested class `Builder`) |
| Enums	| `Semester.java`, `Grade.java` |
| Custom Exceptions	| `DuplicateEnrollmentException.java`, `MaxCreditLimitExceededException.java` |
| Singleton	| `AppConfig.java` |
| Builder Pattern	| `Course.Builder` |
| Stream API	| `ReportsService.java` |
| Recursion	| `RecursionUtil.java` |
| File I/O (NIO.2)	| `CSVImporter.java`, `CSVExporter.java`, `BackupService.java` |

---

## **10. Acknowledgements**
This project was developed as part of the Java SE coursework.

References used:
- Oracle Java Documentation
- Baeldung Java Guides
- Classroom materials and personal notes.

---
