package com.ysl.materialjetpack.paging.fromdb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.materialjetpack.R;

public class PagingDbActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button buttonPopulate, buttonClear;
    MyPagedAdapter pagedAdapter;
    StudentViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_paging_db);

        model = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(StudentViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        pagedAdapter = new MyPagedAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(pagedAdapter);

        model.getPagedListLiveData().observe(this, new Observer<PagedList<StudentAS>>() {
            @Override
            public void onChanged(PagedList<StudentAS> studentAS) {
                pagedAdapter.submitList(studentAS);
            }
        });

        buttonPopulate = findViewById(R.id.buttonPopulate);
        buttonClear = findViewById(R.id.buttonClear);

        buttonPopulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentAS[] studentASArr = new StudentAS[1000];
                for (int i = 0; i < 1000; i++) {
                    StudentAS studentAS = new StudentAS();
                    studentAS.setStudentNumber(i);
                    studentASArr[i] = studentAS;
                }
                new InsertAsyncTask(model).execute(studentASArr);
//                new InsertAsyncTask(model).execute();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClearAsyncTask(model).execute();
            }
        });


    }

    static class InsertAsyncTask extends AsyncTask<StudentAS, Void, Void> {
        StudentViewModel model;

        public InsertAsyncTask(StudentViewModel model) {
            this.model = model;
        }

        @Override
        protected Void doInBackground(StudentAS... studentAS) {
            model.insertStudents(studentAS);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void, Void, Void> {
        StudentViewModel model;

        public ClearAsyncTask(StudentViewModel model) {
            this.model = model;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            model.deleteAllStudents();
            return null;
        }
    }
}
