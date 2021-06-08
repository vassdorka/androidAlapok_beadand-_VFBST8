package com.example.kurzusnaplo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    //insert
    @Insert(onConflict = REPLACE)
    void insert(Course course);

    //delete one course
    @Delete
    void delete(Course course);

    //delete all course
    @Delete
    void reset(List<Course> course);

    //update
    @Query("UPDATE table_name SET courseName = :sCourseName, courseCredit = :sCourseCredit, courseGrade = :sCourseGrade, courseType = :sCourseType, completed = :sCompleted" +
            " WHERE id = :sId")
    void update(int sId, String sCourseName, int sCourseCredit, int sCourseGrade, String sCourseType, int sCompleted);

    //getAllCourse
    @Query("SELECT * FROM table_name")
    List<Course> getAllCourse();

    //number of completed credits - all
    @Query("SELECT SUM(courseCredit) FROM table_name WHERE completed = 1")
    Integer allCompletedCredits();

    //number of completed grades - obligatory
    @Query("SELECT SUM(courseCredit) FROM table_name WHERE courseType LIKE 'Obligatory'AND completed = 1")
    Integer obligatoryCompletedCredits();

    //number of completed grades - required optional
    @Query("SELECT SUM(courseCredit) FROM table_name WHERE courseType LIKE 'Required optional'AND completed = 1")
    Integer requiredOptionalCompletedCredits();

    //number of completed grades - optional
    @Query("SELECT SUM(courseCredit) FROM table_name WHERE courseType LIKE 'Optional'AND completed = 1")
    Integer optionalCompletedCredits();

    //number of completed
    @Query("SELECT COUNT(courseName) FROM table_name WHERE completed = 1")
    Integer NumberOfCompletedCourses();

    //number of failed courses - all
    @Query("SELECT COUNT(courseName) FROM table_name WHERE completed = 0")
    Integer NumberOfFailedCourses();

    //number of completed course's grades
    @Query("SELECT * FROM table_name WHERE completed = 1 AND courseGrade > 1")
    List<Course> getAllCompleted();

}
