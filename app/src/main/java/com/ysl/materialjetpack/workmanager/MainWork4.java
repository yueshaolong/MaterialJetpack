package com.ysl.materialjetpack.workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 后台任务
 */
public class MainWork4 extends Worker {

    public final static String TAG = MainWork4.class.getSimpleName();

    private Context mContext;
    private WorkerParameters workerParams;

    // 有构造函数
    public MainWork4(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
        this.workerParams = workerParams;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {

        Log.d(TAG, "MainWorkManager4 doWork: 后台任务执行了");

        return new Result.Success(); // 本地执行 doWork 任务时 成功 执行任务完毕
    }
}
