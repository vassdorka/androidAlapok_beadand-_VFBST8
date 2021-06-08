package com.example.kurzusnaplo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "table_name")
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "courseName")
    private String courseName;
    @ColumnInfo(name = "courseCredit")
    private int courseCredit;
    @ColumnInfo(name = "courseGrade")
    private int courseGrade;
    @ColumnInfo(name = "courseType")
    private String courseType;
    @ColumnInfo(name = "completed")
    private int completed;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(int courseGrade) {
        this.courseGrade = courseGrade;
    }
    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
