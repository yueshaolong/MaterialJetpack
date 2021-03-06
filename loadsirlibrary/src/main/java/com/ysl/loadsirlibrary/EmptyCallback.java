package com.ysl.loadsirlibrary;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;


public class EmptyCallback extends Callback {

    public static String btnText = "返回上一级";
    public static String text = "暂无数据";
    public static boolean showBtn = true;
    public static View.OnClickListener listener;

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
        TextView id_tv_default = view.findViewById(R.id.id_tv_default);
        id_tv_default.setText(text);
        TextView id_tv_return = view.findViewById(R.id.id_tv_return);
        id_tv_return.setText(btnText);
        id_tv_return.setVisibility(showBtn ? View.VISIBLE : View.GONE);
        if(id_tv_return.getVisibility() == View.VISIBLE){
            id_tv_return.setOnClickListener(listener);
        }
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
