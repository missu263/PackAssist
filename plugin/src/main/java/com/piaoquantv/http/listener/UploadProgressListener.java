package com.piaoquantv.http.listener;

/**
 * Create by nieqi on 2021/7/23
 */
public interface UploadProgressListener {
    void onProgress(long total, long current);
}
