package com.project.examsproject;

public class Marks {
    private String assignment1;
    private String assignment2;
    private String cat1;
    private String cat2;
    private String exam;
    private String course;

    private String totalMarks;

    public Marks(String assignment1, String assignment2, String cat1, String cat2, String exam, String course, String totalMarks) {
        this.assignment1 = assignment1;
        this.assignment2 = assignment2;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.exam = exam;
        this.course = course;
        this.totalMarks = totalMarks;
    }


    public Marks(){

    }


    public String getAssignment1() {
        return assignment1;
    }

    public void setAssignment1(String assignment1) {
        this.assignment1 = assignment1;
    }

    public String getAssignment2() {
        return assignment2;
    }

    public void setAssignment2(String assignment2) {
        this.assignment2 = assignment2;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }
}
