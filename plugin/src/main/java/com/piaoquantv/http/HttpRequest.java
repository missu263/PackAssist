package com.piaoquantv.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Create by nieqi on 2021/7/8
 */
public class HttpRequest<T> {

    OkHttpClient client = new OkHttpClient();

    private String url;
    private Type type;
    private boolean isHttpResponse = true;

    public HttpRequest(String url, TypeToken<?> typeToken) {
        this.url = url;
        this.type = typeToken.getType();
    }

    public HttpRequest(String url, TypeToken<?> typeToken, boolean isHttpResponse) {
        this.url = url;
        this.type = typeToken.getType();
        this.isHttpResponse = isHttpResponse;
    }

    public T doRequest(HashMap<String, String> params, Headers headers) throws Exception {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (String s : params.keySet()) {
            formBodyBuilder.addEncoded(s, Objects.requireNonNull(params.get(s)));
        }

        return doRequest(formBodyBuilder.build(), headers);
    }

    public T doRequest(RequestBody requestBody, Headers headers) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(headers)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            if (isHttpResponse) {
                HttpResponse<T> result = new Gson().fromJson(response.body().string(), type);
                System.out.println("request message = " + result.getRealMessage());
                return result.getData();
            } else {
                return new Gson().fromJson(response.body().string(), type);
            }
        } else {
            throw new IllegalStateException(url + " , http response code = " + response.code());
        }
    }

    public T doRequest(HashMap<String, String> params, File file, Headers headers) throws Exception {

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (String s : params.keySet()) {
            multipartBodyBuilder.addFormDataPart(s, Objects.requireNonNull(params.get(s)));
        }

        if (file != null && file.exists()) {
            multipartBodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/*"), file));
        }

        return doRequest(multipartBodyBuilder.build(), headers);
    }

}
