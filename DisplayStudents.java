package com.mhdbaker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class DisplayStudents {

    private static final String DB_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    private static final String DB_USER = "COMP214_M25_ers_43";
    private static final String DB_PASS = "password";

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Registered Students");

        TableView<Student> tableView = new TableView<>();

        // Define columns
        TableColumn<Student, Integer> studentNumberCol = new TableColumn<>("Student Number");
        studentNumberCol.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));

        TableColumn<Student, String> fullnameCol = new TableColumn<>("Full Name");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));

        TableColumn<Student, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Student, String> birthDateCol = new TableColumn<>("Birth Date");
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<Student, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Student, String> studentEmailCol = new TableColumn<>("Email");
        studentEmailCol.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));

        tableView.getColumns().addAll(studentNumberCol, fullnameCol, addressCol, birthDateCol, genderCol, studentEmailCol);

        // Load data
        ObservableList<Student> students = getStudents();
        tableView.setItems(students);

        VBox vbox = new VBox(tableView);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 700, 400);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Student> getStudents() {
        ObservableList<Student> list = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT studentNumber, fullname, address, TO_CHAR(birthDate, 'YYYY-MM-DD') AS birthDate, gender, studentEmail FROM StudentLab4";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("studentNumber"),
                        rs.getString("fullname"),
                        rs.getString("address"),
                        rs.getString("birthDate"),
                        rs.getString("gender"),
                        rs.getString("studentEmail")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
