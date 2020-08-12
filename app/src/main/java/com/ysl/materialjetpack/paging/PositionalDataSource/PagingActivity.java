package com.ysl.materialjetpack.paging.PositionalDataSource;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysl.materialjetpack.R;

public class PagingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerPagingAdapter recyclerPagingAdapter;
    StudentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_paging);

        recyclerView =  findViewById(R.id.recycle_view);
        recyclerPagingAdapter = new RecyclerPagingAdapter();

        // 最新版本初始化 viewModel
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(StudentViewModel.class);

        // LiveData 观察者 感应更新
        viewModel.getListLiveData().observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(PagedList<Student> students) {
                // 再这里更新适配器数据
                recyclerPagingAdapter.submitList(students);
            }
        });

        recyclerView.setAdapter(recyclerPagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
