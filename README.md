# 🎓 Campus Course & Records Manager (CCRM)
A **Java SE console-based application** to manage **students, courses, enrollments, grades, and backups** for a campus.  
Built with **OOP principles**, **Java 8+ features**, and **design patterns** like Singleton and Builder.

---

## 🌟 Features
- **Student Management**  
  Add, update, deactivate, and view students with profiles and transcripts.

- **Course Management**  
  Create, update, and search courses using Stream API filters.

- **Enrollment & Grading**  
  Enroll students, record grades, and compute GPA with transcripts.

- **File Operations**  
  - Import & export data using CSV files (`/data` folder).  
  - Backup data into timestamped folders (`/backup` folder).  
  - Recursively calculate folder sizes and list files.

- **Reports**
  - GPA distribution.
  - Top students by performance.

- **Menu-driven CLI**  
  Easy-to-use console menu for all operations.

---

## 🛠️ Technologies Used
- **Language:** Java 17+
- **IDE for Setup Screenshots:** Eclipse  
  *(Project can also be coded and run on VS Code)*  
- **Design Patterns:** Singleton, Builder
- **Java APIs Used:**
  - Streams
  - NIO.2
  - Date/Time API
  - Exception Handling

---

## 🚀 Getting Started

### **1. Clone the Repository**
```bash
git clone https://github.com/your-username/ccrm.git
cd ccrm
```

### **2. Project Requirements**
Java Development Kit (JDK 17 or higher)

Verify installation:
```
java -version
```
Any IDE like Eclipse or VS Code.

### **3. Project Setup**
Open the src folder in your IDE.
Ensure project follows this package structure:
```
edu.ccrm.cli
edu.ccrm.domain
edu.ccrm.service
edu.ccrm.io
edu.ccrm.util
edu.ccrm.config
```

Place your initial CSV files:
data/exports/
  students.csv
  courses.csv

### **4. Running the Application**
Compile and run the Main.java class:
```
javac -d bin src/edu/ccrm/cli/Main.java
java -cp bin edu.ccrm.cli.Main
```

### **5. CLI Menu Options**

When you run the program, you'll see options like:
```
===== CCRM Menu =====
1. Manage Students
2. Manage Courses
3. Enrollment & Grading
4. Import / Export Data
5. Backup & Show Backup Size
6. Reports
7. Exit
```

📥 CSV Data Format
```
students.csv
id,regNo,fullName,email,status
1,2023001,Amit Sharma,amit@example.com,ACTIVE
2,2023002,Deepti Gupta,deepti@example.com,ACTIVE
```

courses.csv
```
code,title,credits,instructor,semester,department
CS101,Intro to Programming,4,Dr. Verma,SPRING,Computer Science
MA102,Calculus I,3,Prof. Singh,FALL,Mathematics
```

**Recursive features:**
Calculate total folder size.
List files with depth-first traversal.

📊 Sample Reports
```
Example: GPA Distribution generated using Streams API.
===== GPA Distribution =====
4.0 - 2 students
3.5 - 3 students
3.0 - 1 student
```

🧪 Assertions
Enable assertions to validate invariants:
```
java -ea edu.ccrm.cli.Main
```

Example inside code:
```
assert course.getCredits() > 0 : "Credits must be positive";
```

**THEORY.md:**
All theoretical answers are included in THEORY.md.

**🗺️ Mapping Table (Quick View)**
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
## 🤝 Acknowledgements
- Oracle Java Documentation – https://docs.oracle.com/
- Baeldung Java Guides – https://www.baeldung.com/
- Classroom notes and guidance.

---
