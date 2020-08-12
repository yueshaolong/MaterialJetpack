package com.ysl.materialjetpack.room;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ysl.materialjetpack.R;

import java.util.List;

public class RoomActivity extends AppCompatActivity {

    StudentViewModel studentViewModel;
    ListView listView;


    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        Log.i("jett","onRetainCustomNonConfigurationInstance");
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public Object getLastNonConfigurationInstance() {
        Log.i("jett","getLastNonConfigurationInstance");
        return super.getLastNonConfigurationInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_room);
        listView=findViewById(R.id.listView);

        studentViewModel= new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(StudentViewModel.class);
        studentViewModel.getAllLiveDataStudent().observe(this, new Observer<List<Student>>() {
                    @Override
                    public void onChanged(List<Student> students) {
                        Log.i("jett","记录数:"+students.size());
                        listView.setAdapter(new GoodsAdapter(RoomActivity.this,students));
                    }
                }
        );


//        for (int i = 0; i < 50; i++) {
//            studentViewModel.insert(new Student("jett","123",1));
//        }

//        new Thread(){
//            @Override
//            public void run() {
//                int x=0;
//                for (int i = 0; i < 50; i++) {
//                    try{
//                        Thread.sleep(1000);
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                    studentViewModel.update(new Student(6,"jett"+i,"123",1));
//                }
//            }
//        }.start();

    }
}





