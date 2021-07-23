package com.piaoquantv.http;

import com.piaoquantv.http.listener.UploadProgressListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Create by nieqi on 2021/7/23
 */
public class ProgressMultipartBody extends RequestBody {

    private RequestBody mRequestBody;
    private int mCurrentLength;
    private UploadProgressListener mProgressListener;

    public ProgressMultipartBody(RequestBody requestBody, UploadProgressListener progressListener) {
        this.mRequestBody = requestBody;
        this.mProgressListener = progressListener;
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(@NotNull BufferedSink sink) throws IOException {
        final long contentLength = contentLength();
        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(@NotNull Buffer source, long byteCount) throws IOException {
                mCurrentLength += byteCount;
                if (mProgressListener != null) {
                    mProgressListener.onProgress(contentLength, mCurrentLength);
                }
                super.write(source, byteCount);
            }
        };
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }
}
