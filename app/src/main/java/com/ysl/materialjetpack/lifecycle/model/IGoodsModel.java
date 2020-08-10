package com.ysl.materialjetpack.lifecycle.model;

import com.ysl.materialjetpack.lifecycle.bean.Goods;

import java.util.List;

public interface IGoodsModel {
    void loadGoodsData(OnLoadListener onLoadListener);
        interface OnLoadListener{
            void onComplete(List<Goods> goods);
            void onError(String msg);
    }
}









