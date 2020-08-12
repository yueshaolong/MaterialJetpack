package com.ysl.materialjetpack.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.ysl.materialjetpack.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 场景：非及时任务 （例如：每天同步数据，每天上传一次日志）
// Room数据库管理（持久性）

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_work);
    }

    /**
     * 测试后台任务 一
     *
     * @param view
     */
    public void testBackgroundWork1(View view) {

        // 单一的任务  一次
        OneTimeWorkRequest oneTimeWorkRequest1;

        // 数据
        Data sendData = new Data.Builder().putString("Derry", "九阳神功").build();

        // 请求对象初始化
        oneTimeWorkRequest1 = new OneTimeWorkRequest.Builder(MainWorkManager.class)
                .setInputData(sendData) // 数据的携带
                .build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest1.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(MainWorkManager.TAG, "Activity取到了任务回传的数据: " + workInfo.getOutputData().getString("Derry"));

                        Log.d(MainWorkManager.TAG, "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(MainWorkManager.TAG, "状态：isFinished=true 同学们注意：后台任务已经完成了...");
                        }
                    }
                });

        // 请求对象 加入到队列
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest1);
    }

    /**
     * 多个任务
     * @param view
     */
    public void testBackgroundWork2(View view) {
        // 单一的任务  一次
        OneTimeWorkRequest oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(MainWorkManager2.class).build();
        OneTimeWorkRequest oneTimeWorkRequest3 = new OneTimeWorkRequest.Builder(MainWorkManager3.class).build();
        OneTimeWorkRequest oneTimeWorkRequest4 = new OneTimeWorkRequest.Builder(MainWorkManager4.class).build();

        // 顺序执行
        /*WorkManager.getInstance(this).beginWith(oneTimeWorkRequest2)
                .then(oneTimeWorkRequest3)
                .then(oneTimeWorkRequest4)
                .enqueue();*/

        // 并发执行 旧版本  2  3  同时执行，执行完成后，再执行 4 5
        /*WorkManager.getInstance(this).beginWith(oneTimeWorkRequest2, oneTimeWorkRequest3)
                .then(oneTimeWorkRequest4)
                .enqueue();*/

        // 并发执行 TODO 作业，同学们自己API 找到并行
        // 把Request 存入集合
        List<OneTimeWorkRequest> oneTimeWorkRequests = new ArrayList<>();
        oneTimeWorkRequests.add(oneTimeWorkRequest2); // 测试：没有并行
        oneTimeWorkRequests.add(oneTimeWorkRequest3); // 测试：没有并行
        WorkManager.getInstance(this).beginWith(oneTimeWorkRequests)
                .then(oneTimeWorkRequest4)
                .enqueue();
    }

    /**
     * 重复执行后台任务
     * @param view
     */
    public void testBackgroundWork3(View view) {

        // 重复的任务  多次  循环  , 最少循环重复 15分钟
        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(MainWorkManager2.class, 10, TimeUnit.SECONDS).build();

        // 监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(MainWorkManager.TAG, "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(MainWorkManager.TAG, "状态：isFinished=true 同学们注意：后台任务已经完成了...");
                        }
                    }
                });

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    /**
     * 约束条件
     * 约束后台任务执行
     * @param view
     */
    @RequiresApi(23)
    public void testBackgroundWork4(View view) {

        // 约束条件，必须满足我的条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时）  内部做了电量优化（Android App 不耗电）
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络链接中...
                .setRequiresCharging(true) // 充电中..
                .setRequiresDeviceIdle(true) // 空闲时.. (没有玩游戏)
                .build();

        /**
         * 除了上面设置的约束外，WorkManger还提供了以下的约束作为Work执行的条件：
         *  setRequiredNetworkType：网络连接设置
         *  setRequiresBatteryNotLow：是否为低电量时运行 默认false
         *  setRequiresCharging：是否要插入设备（接入电源），默认false
         *  setRequiresDeviceIdle：设备是否为空闲，默认false
         *  setRequiresStorageNotLow：设备可用存储是否不低于临界阈值
         */

        // 请求对象
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MainWorkManager2.class)
                .setConstraints(myConstraints)
                .build();

        // 测试：监听状态
        // 监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(MainWorkManager.TAG, "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(MainWorkManager.TAG, "状态：isFinished=true 同学们注意：后台任务已经完成了...");
                        }
                    }
                });

        // 加入队列
        WorkManager.getInstance(this).enqueue(request);
    }

    /**
     * TODO 分析源码
     * @param view
     */
    @RequiresApi(23)
    public void testBackgroundWork7(View view) {

        // 约束条件，必须满足我的条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时）  内部做了电量优化（Android App 不耗电）
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络链接中...
                .setRequiresCharging(true) // 充电中..
                .setRequiresDeviceIdle(true) // 空闲时.. (没有玩游戏)
                .build();

        // 请求对象
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MainWorkManager2.class)
                .setConstraints(myConstraints) // TODO 3 约束条件的执行
                .build();


        WorkManager.getInstance(this) // TODO 1 初始化工作源码


                .enqueue(request); // TODO 2 加入队列执行
    }
}
