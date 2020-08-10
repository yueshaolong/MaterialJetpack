package com.ysl.materialjetpack.lifecycle.model;


import com.ysl.materialjetpack.R;
import com.ysl.materialjetpack.lifecycle.bean.Goods;
import com.ysl.materialjetpack.lifecycle.databus.LiveDataBus;
import com.ysl.materialjetpack.lifecycle.databus.LiveDataBusX;

import java.util.ArrayList;
import java.util.List;

public class GoodsModel implements IGoodsModel {
    @Override
    public void loadGoodsData(OnLoadListener onLoadListener) {
        onLoadListener.onComplete(getData());
    }

    private List<Goods> getData() {
        ArrayList data = new ArrayList<>();
        //这里的数据来源于网络或数据库或其它地方
        data.add(new Goods(R.mipmap.s1, "一星", "****"));
        data.add(new Goods(R.mipmap.s2, "一星", "****"));
        data.add(new Goods(R.mipmap.s3, "一星", "****"));
        data.add(new Goods(R.mipmap.s4, "一星", "****"));
        data.add(new Goods(R.mipmap.s5, "一星", "****"));
        data.add(new Goods(R.mipmap.s6, "一星", "****"));
        data.add(new Goods(R.mipmap.s7, "一星", "****"));
        data.add(new Goods(R.mipmap.s8, "一星", "****"));
        data.add(new Goods(R.mipmap.s9, "一星", "****"));

        //发送消息
        LiveDataBusX.getInstance().with("list", ArrayList.class).postValue(data);

        return data;
    }

}
