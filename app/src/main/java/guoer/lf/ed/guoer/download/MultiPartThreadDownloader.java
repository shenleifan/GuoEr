package guoer.lf.ed.guoer.download;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultiPartThreadDownloader {
    private static final String TAG = "MultiPartThreadDownload";

    private String mUrl;

    private File mLocalFile;

    private String mDirStr;

    private String mFilename;

    private int mThreadCount;

    private long mFileSize;

    public MultiPartThreadDownloader(String url, String dirStr, String fileName, int threadCount) {
        this.mUrl = url;
        this.mDirStr = dirStr;
        this.mFilename = fileName;
        this.mThreadCount = threadCount;
    }

    public void download() {
        createFileByUrl();

    }

    private String createFileByUrl() {
        return null;
    }

    private static class DownloadThread extends Thread {
        private URL url;

        private File localFile;

        private boolean isFinish;

        private long startPoint;

        private long endPoint;

        public DownloadThread(URL url, File localFile, long startPoint, long endPoint) {
            this.url = url;
            this.localFile = localFile;
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }

        @Override
        public void run() {
            super.run();
            Log.d(TAG, "run: " + Thread.currentThread().getName() + "Downloading...");
            HttpURLConnection connection = setHttpUrlConnection(url);
            startPartDownload(connection);
        }

        private void startPartDownload(HttpURLConnection connection) {

        }

        private HttpURLConnection setHttpUrlConnection(URL url) {
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, " +
                                "application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, " +
                                "application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, " +
                                "application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
