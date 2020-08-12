package com.ysl.materialjetpack.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student... students);

    @Delete
    void delete(Student student);

    @Update
    void update(Student student);

    @Query("select * from Student")
    List<Student> getAll();

    @Query("select * from Student order by uid")
    LiveData<List<Student>> getAllLiveDataStudent();

    //查询一条记录
    @Query("select * from Student where name like :name")
    Student findByName(String name);

    @Query("select * from Student where uid in(:userIds)")
    List<Student> getAllId(int[] userIds);

    //只查N个字段
    @Query("select name,pwd from Student")
    List<StudentTuple> getRecord();

}







