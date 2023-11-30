package com.project.examsproject;

public class Student {
    private String name;
    private String email;
    private String regNo;
    private String password;
    private String course1;
    private String course2;
    private String course3;
    private String course4;
    private String course5;
    private String academicYear;
    private String semester;

    // Constructor
    public Student(String name, String email, String regNo, String password,
                   String course1, String course2, String course3, String course4, String course5,
                   String academicYear, String semester) {
        this.name = name;
        this.email = email;
        this.regNo = regNo;
        this.password = password;
        this.course1 = course1;
        this.course2 = course2;
        this.course3 = course3;
        this.course4 = course4;
        this.course5 = course5;
        this.academicYear = academicYear;
        this.semester = semester;
    }

    // Getters and Setters (You may generate these using your IDE for convenience)
    // Example:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Other getters and setters for email, regNo, password, courses, academicYear, and semester...

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse1() {
        return course1;
    }

    public void setCourse1(String course1) {
        this.course1 = course1;
    }

    public String getCourse2() {
        return course2;
    }

    public void setCourse2(String course2) {
        this.course2 = course2;
    }

    public String getCourse3() {
        return course3;
    }

    public void setCourse3(String course3) {
        this.course3 = course3;
    }

    public String getCourse4() {
        return course4;
    }

    public void setCourse4(String course4) {
        this.course4 = course4;
    }

    public String getCourse5() {
        return course5;
    }

    public void setCourse5(String course5) {
        this.course5 = course5;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
