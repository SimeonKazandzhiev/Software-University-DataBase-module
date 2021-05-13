package demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);


/*
        Can be done with file to keep the user and password

        System.out.println("Enter username default (root)");
        String user = scanner.nextLine();
        user = user.equals("") ? "root" : user;

        System.out.println("Enter password default (root)");
        String password = scanner.nextLine();
        password = password.equals("") ? "root" : password;*/

        Properties props = new Properties();
        String appConfigPath = Main.class.getClassLoader().getResource("db.properties").getPath();
        props.load(new FileInputStream(appConfigPath));
      //  props.setProperty("user", user);
      //  props.setProperty("password", password);


        // 1. Load jdbc driver (optional)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Driver loaded successfully");

        System.out.println("Connection successfully");
        //2. Connect to DB
        try (Connection connection =
             DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni?useSSL=false", props);

             //3. Prepare statement
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM employees WHERE salary > ?")) {

            System.out.println("Enter min salary: ");
            String salary = scanner.nextLine();

            // The set statements prevent SQL injection
            stmt.setDouble(1, Double.parseDouble(salary));
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                System.out.printf("| %-15.15s | %-15.15s | %10.2f |%n",
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDouble("salary"));

            }
          //  connection.close();
        }
    }
}
