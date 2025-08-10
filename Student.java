package com.mhdbaker;

public class Student {
    private final int studentNumber;
    private String fullname;
    private String address;
    private String birthDate;
    private String gender;
    private final String studentEmail;

    public Student(int studentNumber, String fullname, String address, String birthDate, String gender, String studentEmail) {
        this.studentNumber = studentNumber;
        this.fullname = fullname;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.studentEmail = studentEmail;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getStudentEmail() {
        return studentEmail;
    }
}
