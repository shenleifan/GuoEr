package guoer.lf.ed.guoer.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

import guoer.lf.ed.guoer.GuoerActivity;
import guoer.lf.ed.guoer.R;

public class DownloadService extends Service {
    private static final int REQUEST_CODE = 0;
    private static final int NOTIFY_ID = 1;
    private DownloadTask mDownloadTask;
    private String mDownloadUrl;
    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(NOTIFY_ID, getNotification("Download...", progress));
        }

        @Override
        public void onSuccesss() {
            mDownloadTask = null;
            //stop foreground service and create a new download success notification
            stopForeground(true);
            getNotificationManager().notify(NOTIFY_ID, getNotification("Download success.", -1));
            Toast.makeText(DownloadService.this, "Download success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            mDownloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(NOTIFY_ID, getNotification("Download Failed.", -1));
            Toast.makeText(DownloadService.this, "Download failed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private DownloadBinder mBinder;

    public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (mDownloadTask == null) {
                mDownloadUrl = url;
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(mDownloadUrl);
                startForeground(NOTIFY_ID, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.cancelDownload();
            } else {
                if (mDownloadUrl != null) {
                    String fileName = mDownloadUrl.substring(mDownloadUrl.lastIndexOf("/"));
                    String fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(fileDirectory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(NOTIFY_ID);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Download canceled.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, GuoerActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, REQUEST_CODE, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        if (progress > 0) {
            //show progress when progress > 0
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
