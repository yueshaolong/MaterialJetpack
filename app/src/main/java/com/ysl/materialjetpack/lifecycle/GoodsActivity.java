package com.ysl.materialjetpack.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.lifecycle.Observer;

import com.ysl.materialjetpack.R;
import com.ysl.materialjetpack.lifecycle.adapter.GoodsAdapter;
import com.ysl.materialjetpack.lifecycle.bean.Goods;
import com.ysl.materialjetpack.lifecycle.databus.LiveDataBusX;
import com.ysl.materialjetpack.lifecycle.presenter.GoodsPresenter;
import com.ysl.materialjetpack.lifecycle.view.BaseActivity;
import com.ysl.materialjetpack.lifecycle.view.IGoodsView;

import java.util.ArrayList;
import java.util.List;

public class GoodsActivity extends BaseActivity<GoodsPresenter, IGoodsView>
        implements IGoodsView {



    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lifecycle);
        listView=findViewById(R.id.listView);

        presenter.fetch();

        LiveDataBusX.getInstance().with("list", ArrayList.class)
                .observe(this, new Observer<ArrayList>() {
                    @Override
                    public void onChanged(ArrayList arrayList) {
                        if(arrayList!=null){
                            Log.i("jett","收到了数据"+arrayList.toString());
                        }
                    }
                });

    }
    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter();
    }
    @Override
    public void showGoodsView(List<Goods> goods) {
        listView.setAdapter(new GoodsAdapter(this,goods));
    }

    @Override
    public void showErrorMessage(String msg) {

    }

    @Override
    protected void init() {
        super.init();
        getLifecycle().addObserver(presenter);
    }
}
