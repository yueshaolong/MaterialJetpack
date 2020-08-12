package com.ysl.materialjetpack.paging.fromdb;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class StudentRepository {

    private LiveData<PagedList<StudentAS>> liveDataAllStudent;
    private StudentDao studentDao;

    public StudentRepository(Context context) {
        StudentsDatabase database = StudentsDatabase.getInstance(context);
        studentDao = database.getStudentDao();
        if (liveDataAllStudent == null) {
            liveDataAllStudent = new LivePagedListBuilder<Integer, StudentAS>(
                    studentDao.getAllStudents(), 20).build();
        }
    }

    //提供一些API给viewmodel使用
    void insertStudents(StudentAS... studentAS) {
        studentDao.insertStudents(studentAS);
    }

    void deleteAllStudents() {
        studentDao.deleteAllStudents();
    }

    LiveData<PagedList<StudentAS>> getAll() {
        return liveDataAllStudent;
    }
}
