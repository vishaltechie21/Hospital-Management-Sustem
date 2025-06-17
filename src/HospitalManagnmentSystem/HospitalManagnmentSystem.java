package HospitalManagnmentSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagnmentSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "vishal@99#";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patients patients = new Patients(connection, scanner);
            Doctors doctors = new Doctors(connection);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. ADD patients");
                System.out.println("2. VIEW patients");
                System.out.println("3. VIEW doctors");
                System.out.println("4. BOOK appointment");
                System.out.println("5. EXIT");
                System.out.print("ENTER YOUR CHOICE: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patients.addPatients();
                        System.out.println();
                        break;
                    case 2:
                        patients.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctors.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patients, doctors, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patients patients, Doctors doctors, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();

        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();

        System.out.print("Enter Appointment date (yyyy-MM-dd): ");
        String appointmentDate = scanner.next();

        if (patients.getPatientsByid(patientId) && doctors.getdoctorsByid(doctorId)) {
            if (checkDoctorAvailability(doctorId, connection, appointmentDate)) {
                String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("✅ Appointment is booked.");
                    } else {
                        System.out.println("❌ Failed to book appointment.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("❌ Doctor is not available on that date.");
            }
        } else {
            System.out.println("❌ Invalid Patient or Doctor ID.");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, Connection connection, String appointmentDate) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
