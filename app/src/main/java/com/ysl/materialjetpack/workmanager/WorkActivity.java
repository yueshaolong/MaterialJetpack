package com.ysl.materialjetpack.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.BackoffPolicy;
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
        // 数据
        Data sendData = new Data.Builder().putString("Derry", "九阳神功").build();

        // 请求对象初始化
        OneTimeWorkRequest oneTimeWorkRequest1 = new OneTimeWorkRequest.Builder(MainWork.class)
                .setInputData(sendData) // 数据的携带
                .build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest1.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(MainWork.TAG, "Activity取到了任务回传的数据: " + workInfo.getOutputData().getString("Derry"));

                        Log.d(MainWork.TAG, "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(MainWork.TAG, "状态：isFinished=true 同学们注意：后台任务已经完成了...");
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
        OneTimeWorkRequest oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(MainWork2.class).build();
        OneTimeWorkRequest oneTimeWorkRequest3 = new OneTimeWorkRequest.Builder(MainWork3.class).build();
        OneTimeWorkRequest oneTimeWorkRequest4 = new OneTimeWorkRequest.Builder(MainWork4.class).build();

        // 顺序执行2 3 4
        WorkManager.getInstance(this)
                .beginWith(oneTimeWorkRequest2)
                .then(oneTimeWorkRequest3)
                .then(oneTimeWorkRequest4)
                .enqueue();

        // 并发执行 TODO 作业，同学们自己API 找到并行
        // 把Request 存入集合
        List<OneTimeWorkRequest> oneTimeWorkRequests = new ArrayList<>();
        oneTimeWorkRequests.add(oneTimeWorkRequest2); // 测试：没有并行
        oneTimeWorkRequests.add(oneTimeWorkRequest3); // 测试：没有并行
        WorkManager.getInstance(this)
                .beginWith(oneTimeWorkRequests)
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
                new PeriodicWorkRequest.Builder(MainWork2.class, 10, TimeUnit.SECONDS).build();

        // 监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(MainWork.TAG, "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(MainWork.TAG, "状态：isFinished=true 同学们注意：后台任务已经完成了...");
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

        // 约束条件，必须满足我的条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时）
        // 内部做了电量优化（Android App 不耗电）
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络链接中...
                .setRequiresCharging(true) // 充电中..
                .setRequiresDeviceIdle(true) // 空闲时.. (没有玩游戏)
                .setRequiresBatteryNotLow(false) // 低电量
                .setRequiresStorageNotLow(false) //可用存储低于临界值
                .build();

        /**
         * 除了上面设置的约束外，WorkManger还提供了以下的约束作为Work执行的条件：
         *  setRequiredNetworkType：网络连接设置，默认NetworkType.NOT_REQUIRED
         *  setRequiresBatteryNotLow：是否为低电量时运行，默认false
         *  setRequiresCharging：是否要插入设备（接入电源），默认false
         *  setRequiresDeviceIdle：设备是否为空闲，默认false
         *  setRequiresStorageNotLow：设备可用存储是否不低于临界阈值，默认false
         */

        Data imageData = new Data.Builder()
                .putString("key", "value")
                .build();

        // 请求对象
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MainWork2.class)
                .setConstraints(myConstraints)
                //满足条件，并且10分钟后再执行；
                .setInitialDelay(10, TimeUnit.MINUTES)
                //退避延迟时间指定了重试工作前的最短等待时间。
                .setBackoffCriteria(
                        BackoffPolicy.LINEAR,//线性方式
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,//10秒
                        TimeUnit.MILLISECONDS)//单位毫秒
                //设置输入的参数，以键值对的方式;Worker 类可通过调用 Worker.getInputData() 访问输入参数。
                .setInputData(imageData)
                //为任务添加标记；WorkManager.cancelAllWorkByTag(String) 会取消使用特定标记的所有任务，
                //WorkManager.getWorkInfosByTagLiveData(String) 会返回 LiveData 和具有该标记的所有任务的状态列表。
                .addTag("ooxx")
                //OverwritingInputMerger 会尝试将所有输入中的所有键添加到输出中。如果发生冲突，它会覆盖先前设置的键。
                //ArrayCreatingInputMerger 会尝试合并输入，并在必要时创建数组。
                .setInputMerger(ArrayCreatingInputMerger.class)
                .build();

        // 测试：监听状态
        // 监听状态
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())//会返回具有改id的LiveData
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(MainWork.TAG, "状态：" + workInfo.getState().name());
                        if (workInfo.getState().isFinished()) {
                            Log.d(MainWork.TAG, "状态：isFinished=true 同学们注意：后台任务已经完成了...");
                        }
                        //可以实时观察任务执行的进度
                        Data progress = workInfo.getProgress();
                        int value = progress.getInt("key", 0);
                        Log.d(MainWork.TAG, "任务进度: "+value);
                    }
                });

        // 加入队列
        WorkManager.getInstance(this).enqueue(request);
        // 取消任务
        WorkManager.getInstance(this).cancelWorkById(request.getId());
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
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MainWork2.class)
                .setConstraints(myConstraints) // TODO 3 约束条件的执行
                .build();


        WorkManager.getInstance(this) // TODO 1 初始化工作源码


                .enqueue(request); // TODO 2 加入队列执行
    }
}
