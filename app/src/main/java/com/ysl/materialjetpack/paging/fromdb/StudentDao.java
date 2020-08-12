package com.ysl.materialjetpack.paging.fromdb;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StudentDao {
    @Insert
    void insertStudents(StudentAS... studentAS);

    @Query("DELETE FROM student_table")
    void deleteAllStudents();

    @Query("SELECT * FROM student_table ORDER BY id")
//    LiveData<PagedList<StudentAS>> getAllStudents();
    DataSource.Factory<Integer, StudentAS> getAllStudents();
}
