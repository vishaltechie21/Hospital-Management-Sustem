# Hospital Management System (Java + MySQL)

This is a **console-based Hospital Management System** developed in **Java** using **MySQL** as the database. It allows for managing patients, doctors, and appointments through terminal interactions.

---

## 📦 Features

- ✅ Add new patients  
- 📋 View all patients  
- 👨‍⚕️ View all doctors  
- 📅 Book appointments (with availability check)  
- ❌ Prevent double-booking of doctors  
- 🎯 Data stored and retrieved from MySQL database  

---

## 🛠️ Tech Stack

| Layer       | Technology         |
|-------------|--------------------|
| Language    | Java (JDK 8+)      |
| Database    | MySQL              |
| Connector   | JDBC (MySQL Driver)|
| Runtime     | Command Line / Terminal |

---

## ⚙️ How to Run

### 1️⃣ Prerequisites

- Java JDK 8 or higher  
- MySQL Server  
- MySQL JDBC Driver (`mysql-connector-java-x.x.xx.jar`)  
- IntelliJ IDEA or any IDE (optional)

---

### 2️⃣ MySQL Setup

```sql
CREATE DATABASE hospital;
USE hospital;

CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL
);

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
````

---

### 3️⃣ Project Setup

1. Clone or download this repository
2. Add MySQL JDBC `.jar` to your project’s classpath
3. Update your DB credentials in `HospitalManagnmentSystem.java`:

```java
private static final String url = "jdbc:mysql://localhost:3306/hospital";
private static final String username = "root";
private static final String password = "your_password";
```

---

### 4️⃣ Run the Application

* Compile and run `HospitalManagnmentSystem.java`
* Use the terminal menu:

```
HOSPITAL MANAGEMENT SYSTEM
1. ADD patients
2. VIEW patients
3. VIEW doctors
4. BOOK appointment
5. EXIT
ENTER YOUR CHOICE:
```

---

## 📌 Notes

* Appointment date must be entered in `dd-MM-yyyy` format
* Doctor double-booking is prevented through an availability check
* The app runs completely in the terminal and does not include a GUI

---

## 🚀 Future Improvements

* Add GUI using JavaFX or Swing
* Convert to a web app using Spring Boot or JSP
* Add authentication and role-based access
* Export patient/appointment data as PDF or CSV

---

## 👨‍💻 Author

Developed by **Vishal Kumar**

---

## 📜 License

This project is open source and available under the [MIT License](LICENSE).

```

---

Let me know if you want to include the actual Java code files or link it with GitHub!
```
