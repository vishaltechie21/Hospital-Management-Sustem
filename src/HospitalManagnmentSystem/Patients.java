package HospitalManagnmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Patients {

    private Connection connection;
    private Scanner scanner;

    public Patients(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatients() {
        try {
            System.out.print("Enter Patient name: ");
            scanner.nextLine(); // consume leftover newline if any
            String name = scanner.nextLine(); // allows full name with spaces

            System.out.print("Enter the Patient age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // consume newline after nextInt()

            System.out.print("Enter the Patient gender: ");
            String gender = scanner.nextLine();

            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Patient added successfully.");
            } else {
                System.out.println("❌ Failed to add patient.");
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter correct data types.");
            scanner.nextLine(); // clear the invalid input from the buffer
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }

    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Patients:");
            System.out.println("+-------------+---------------+-------+--------+");
            System.out.println("| Patient ID  | Name          | Age   | Gender |");
            System.out.println("+-------------+---------------+-------+--------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-11d | %-13s | %-5d | %-6s |\n", id, name, age, gender);
            }

            System.out.println("+-------------+---------------+-------+--------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientsByid(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
