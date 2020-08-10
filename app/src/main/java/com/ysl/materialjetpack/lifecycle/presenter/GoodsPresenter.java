package com.ysl.materialjetpack.lifecycle.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.ysl.materialjetpack.lifecycle.bean.Goods;
import com.ysl.materialjetpack.lifecycle.model.GoodsModel;
import com.ysl.materialjetpack.lifecycle.model.IGoodsModel;
import com.ysl.materialjetpack.lifecycle.view.IGoodsView;

import java.util.List;

public class GoodsPresenter<T extends IGoodsView> extends BasePresenter{

    IGoodsModel iGoodsModel=new GoodsModel();

    /**
     * 执行业务逻辑
     */
    public void fetch(){
        if(iBaseView !=null && iGoodsModel!=null){
            iGoodsModel.loadGoodsData(new IGoodsModel.OnLoadListener() {
                @Override
                public void onComplete(List<Goods> goods) {
                    ((IGoodsView)iBaseView.get()).showGoodsView(goods);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }

    @Override
    void onCreateX(LifecycleOwner owner) {
        super.onCreateX(owner);
        Log.i("jett","create");
    }

    @Override
    void onDestory(LifecycleOwner owner) {
        super.onDestory(owner);
        Log.i("jett","destroy");
    }
}
