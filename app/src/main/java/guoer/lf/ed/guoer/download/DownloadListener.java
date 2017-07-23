package guoer.lf.ed.guoer.download;

public interface DownloadListener {

    void onProgress(int progress);

    void onSuccesss();

    void onFailed();

    void onPaused();

    void onCanceled();
}
