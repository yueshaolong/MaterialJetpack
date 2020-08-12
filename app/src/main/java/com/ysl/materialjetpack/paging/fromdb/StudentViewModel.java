package com.ysl.materialjetpack.paging.fromdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;


public class StudentViewModel extends AndroidViewModel {

    private StudentRepository studentRepository;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application);
    }

    public LiveData<PagedList<StudentAS>> getPagedListLiveData() {
        return studentRepository.getAll();
    }
    public void insertStudents(StudentAS... studentAS){
        studentRepository.insertStudents(studentAS);
    }
    public void deleteAllStudents(){
        studentRepository.deleteAllStudents();
    }

}






