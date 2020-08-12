package com.ysl.materialjetpack.room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository {

    private LiveData<List<Student>> liveDataAllStudent;
    private StudentDao studentDao;

    public StudentRepository(Context context){
        AppDatabase database=AppDatabase.getInstance(context);
        studentDao=database.studentDao();
        if(liveDataAllStudent==null){
            liveDataAllStudent = studentDao.getAllLiveDataStudent();
        }
    }
    //提供一些API给viewmodel使用
    void insert(Student... students){
        studentDao.insert(students);
    }
    void delete(Student student){
        studentDao.delete(student);
    }
    void update(Student student){
        studentDao.update(student);
    }
    List<Student> getAll(){
        return studentDao.getAll();
    }

    LiveData<List<Student>> getAllLiveDataStudent(){
        return studentDao.getAllLiveDataStudent();
    }

    Student findByName(String name){ return studentDao.findByName(name);}
    List<Student> getAllId(int[] userIds){return studentDao.getAllId(userIds);}
    List<StudentTuple> getRecord(){return studentDao.getRecord();}
}
