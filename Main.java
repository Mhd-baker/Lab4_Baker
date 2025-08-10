package com.mhdbaker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {

    private static final String DB_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    private static final String DB_USER = "COMP214_M25_ers_43";
    private static final String DB_PASS = "password";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Registration");

        // Load logo image from resources folder
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/logo512.png")));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        // Form fields
        Label lblStudentNumber = new Label("Student Number:");
        TextField tfStudentNumber = new TextField();

        Label lblFullname = new Label("Fullname:");
        TextField tfFullname = new TextField();

        Label lblAddress = new Label("Address:");
        TextField tfAddress = new TextField();

        Label lblBirthDate = new Label("Birth Date (YYYY-MM-DD):");
        TextField tfBirthDate = new TextField();

        Label lblGender = new Label("Gender:");
        TextField tfGender = new TextField();

        Label lblStudentEmail = new Label("Student Email:");
        TextField tfStudentEmail = new TextField();

        // Buttons
        Button btnRegister = new Button("Register");
        Button btnCancel = new Button("Cancel");
        Button btnShowStudents = new Button("Show Registered Students");

        // GridPane for form inputs
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(8);
        grid.setHgap(10);

        // Add controls to grid
        grid.add(lblStudentNumber, 0, 0);
        grid.add(tfStudentNumber, 1, 0);

        grid.add(lblFullname, 0, 1);
        grid.add(tfFullname, 1, 1);

        grid.add(lblAddress, 0, 2);
        grid.add(tfAddress, 1, 2);

        grid.add(lblBirthDate, 0, 3);
        grid.add(tfBirthDate, 1, 3);

        grid.add(lblGender, 0, 4);
        grid.add(tfGender, 1, 4);

        grid.add(lblStudentEmail, 0, 5);
        grid.add(tfStudentEmail, 1, 5);

        grid.add(btnRegister, 0, 6);
        grid.add(btnCancel, 1, 6);

        grid.add(btnShowStudents, 0, 7);

        // Put the logo and the form grid side by side in HBox
        HBox root = new HBox(20);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(logoView, grid);

        // Register action
        btnRegister.setOnAction(e -> {
            try {
                int studentNumber = Integer.parseInt(tfStudentNumber.getText());
                String fullname = tfFullname.getText();
                String address = tfAddress.getText();
                String birthDate = tfBirthDate.getText();
                String gender = tfGender.getText();
                String studentEmail = tfStudentEmail.getText();

                insertStudent(studentNumber, fullname, address, birthDate, gender, studentEmail);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Student registered successfully!");
                alert.showAndWait();

                clearForm(tfStudentNumber, tfFullname, tfAddress, tfBirthDate, tfGender, tfStudentEmail);

            } catch (NumberFormatException ex) {
                showError("Student Number must be a valid integer.");
            } catch (SQLException ex) {
                showError("Database error: " + ex.getMessage());
            }
        });

        // Cancel action clears form
        btnCancel.setOnAction(e -> clearForm(tfStudentNumber, tfFullname, tfAddress, tfBirthDate, tfGender, tfStudentEmail));

        // Show registered students window
        btnShowStudents.setOnAction(e -> new DisplayStudents().show());

        Scene scene = new Scene(root, 650, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertStudent(int studentNumber, String fullname, String address, String birthDate, String gender, String studentEmail) throws SQLException {
        String sql = "INSERT INTO StudentLab4 (studentNumber, fullname, address, birthDate, gender, studentEmail) VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentNumber);
            stmt.setString(2, fullname);
            stmt.setString(3, address);
            stmt.setString(4, birthDate);
            stmt.setString(5, gender);
            stmt.setString(6, studentEmail);

            stmt.executeUpdate();
            System.out.println("Inserted student " + fullname);
        }
    }

    @SafeVarargs
    private void clearForm(TextField... fields) {
        for (TextField tf : fields) {
            tf.clear();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
