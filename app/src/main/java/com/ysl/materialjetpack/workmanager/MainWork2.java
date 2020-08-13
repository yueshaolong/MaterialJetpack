package com.ysl.materialjetpack.workmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 后台任务
 */
public class MainWork2 extends Worker {

    public final static String TAG = MainWork2.class.getSimpleName();

    private Context mContext;
    private WorkerParameters workerParams;

    // 有构造函数
    public MainWork2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
        this.workerParams = workerParams;

        // Set initial progress to 0
        setProgressAsync(new Data.Builder().putInt("key", 0).build());
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {

        Log.d(TAG, "MainWorkManager2 doWork: 后台任务执行了");
//        return new Result.Success(); // 本地执行 doWork 任务时 成功 执行任务完毕

        // Set progress to 100 after you are done doing your work.
        setProgressAsync(new Data.Builder().putInt("key", 100).build());
        Data outputData = new Data.Builder()
                .putString("key", "value")
                .build();
        return new Result.Success(outputData); //Data 类可用于输出返回值。
    }

}
