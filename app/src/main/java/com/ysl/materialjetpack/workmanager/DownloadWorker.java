package com.ysl.materialjetpack.workmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ysl.materialjetpack.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class DownloadWorker extends Worker {
    private static final String KEY_INPUT_URL = "KEY_INPUT_URL";
    private static final String KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME";

    private NotificationManager notificationManager;

    public DownloadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters parameters) {
        super(context, parameters);
        notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data inputData = getInputData();
        String inputUrl = inputData.getString(KEY_INPUT_URL);
        String outputFile = inputData.getString(KEY_OUTPUT_FILE_NAME);
        // Mark the Worker as important
        String progress = "Starting Download";
        setForegroundAsync(createForegroundInfo(progress));
        download(inputUrl, outputFile);
        return Result.success();
    }

    private void download(String inputUrl, String outputFile) {
        // Downloads a file and updates bytes read
        // Calls setForegroundInfoAsync() periodically when it needs to update
        // the ongoing Notification
    }

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        // Build a notification using bytesRead and contentLength

        Context context = getApplicationContext();
        String id = context.getString(R.string.notification_channel_id);
        String title = context.getString(R.string.notification_title);
        String cancel = context.getString(R.string.cancel_download);
        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(title)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build();

        return new ForegroundInfo(0,notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // Create a Notification channel
    }
}

