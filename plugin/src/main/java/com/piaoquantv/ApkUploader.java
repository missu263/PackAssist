package com.piaoquantv;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.piaoquantv.bean.FeishuAccessToken;
import com.piaoquantv.bean.FeishuChatListResult;
import com.piaoquantv.bean.FeishuGroup;
import com.piaoquantv.bean.FeishuImageResult;
import com.piaoquantv.bean.FeishuSendMessageResult;
import com.piaoquantv.bean.PgyUploadResult;
import com.piaoquantv.bean.model.Message;
import com.piaoquantv.bean.model.MessageParams;
import com.piaoquantv.http.HttpRequest;
import com.piaoquantv.http.HttpResponse;
import com.piaoquantv.util.MessageHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Create by nieqi on 2021/7/8
 */
public class ApkUploader {

    public static final String URL_PGY_UPLOAD = "https://www.pgyer.com/apiv2/app/upload";
    public static final String URL_FEISHU_ACCESSTOKEN = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal";
    public static final String URL_FEISHU_CHAT_LIST = "https://open.feishu.cn/open-apis/chat/v4/list";
    public static final String URL_FEISHU_PUT_IMAGE = "https://open.feishu.cn/open-apis/image/v4/put";
    public static final String URL_FEISHU_SEND_MESSAGE = "https://open.feishu.cn/open-apis/message/v4/send";

    public static void start(String apkPath, String pgyApiKey, String chatName,
                             String feishuBotAppId, String feishuBotAppSecret, String description) throws Exception {
        PgyUploadResult pgyUploadResult = startUpload2Pgy(apkPath, pgyApiKey);
        startSendFeishuMessage(pgyUploadResult, chatName, feishuBotAppId, feishuBotAppSecret, description);
    }

    /**
     * 上传APK到蒲公英
     */
    private static PgyUploadResult startUpload2Pgy(String apkPath, String pgyApiKey) throws Exception {
        System.out.println("prepareUpload2Pgy");

        if (!new File(apkPath).exists())
            throw new FileNotFoundException(apkPath);

        HashMap<String, String> params = new HashMap<>();
        params.put("_api_key", pgyApiKey);
        params.put("buildUpdateDescription", "");
        params.put("buildInstallType", "2");
        params.put("buildPassword", "123456");

        Headers headers = new Headers.Builder()
                .add("content-type", "application/x-www-form-urlencoded")
                .build();

        return new HttpRequest<PgyUploadResult>(URL_PGY_UPLOAD, new TypeToken<HttpResponse<PgyUploadResult>>() {
        }).doRequest(params, new File(apkPath), headers);
    }

    /**
     * 往指定的飞书群中发送消息
     */
    private static void startSendFeishuMessage(PgyUploadResult pgyUploadResult, String chatName,
                                               String feishuBotAppId, String feishuBotAppSecret, String description) throws Exception {

        Headers headers = new Headers.Builder()
                .add("content-type", "application/json; charset=utf-8")
                .build();

        //1.获取access token
        HashMap<String, String> accessTokenParams = new HashMap<>();
        accessTokenParams.put("app_id", feishuBotAppId);
        accessTokenParams.put("app_secret", feishuBotAppSecret);
        FeishuAccessToken feishuAccessToken = new HttpRequest<FeishuAccessToken>(URL_FEISHU_ACCESSTOKEN,
                new TypeToken<FeishuAccessToken>() {
                }, false).doRequest(accessTokenParams, headers);

        Headers authorizationHeaders = new Headers.Builder()
                .add("content-type", "application/json; charset=utf-8")
                .add("Authorization", "Bearer " + feishuAccessToken.getTenant_access_token())
                .build();

        //2.找到发送的群
        HashMap<String, String> chatListParams = new HashMap<>();
        chatListParams.put("page_size", "200");
        chatListParams.put("page_token", "");

        FeishuChatListResult feishuChatListResult = new HttpRequest<FeishuChatListResult>(URL_FEISHU_CHAT_LIST,
                new TypeToken<HttpResponse<FeishuChatListResult>>() {
                }).doRequest(chatListParams, authorizationHeaders);

        String chatId = "";
        for (FeishuGroup group : feishuChatListResult.getGroups()) {
            if (group.getName().equals(chatName)) {
                chatId = group.getChat_id();
            }
        }

        //3.上传图片
        String imageKey = uploadDownloadQRCode2Feishu(feishuAccessToken.getTenant_access_token(), pgyUploadResult.getBuildQRCodeURL());

        //4.发送消息
        Message messageContent = MessageHelper.buildMessageContent(pgyUploadResult, imageKey, description);
        MessageParams messageParams = new MessageParams(chatId, messageContent);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(messageParams));

        new HttpRequest<FeishuSendMessageResult>(URL_FEISHU_SEND_MESSAGE,
                new TypeToken<HttpResponse<FeishuSendMessageResult>>() {
                }).doRequest(requestBody, authorizationHeaders);

    }

    private static String uploadDownloadQRCode2Feishu(String token, String qrImageUrl) throws Exception {
        OkHttpClient imageOkHttpClient = new OkHttpClient();
        Request imageResourceRequest = new Request.Builder()
                .url(qrImageUrl)
                .addHeader("Connection", "close")
                .build();
        Response imageResourceResponse = imageOkHttpClient.newCall(imageResourceRequest).execute();
        if (!imageResourceResponse.isSuccessful()) {
            throw new Exception(qrImageUrl + ", responseCode = " + imageResourceResponse.code());
        }

        ResponseBody imageResourceResponseBody = imageResourceResponse.body();
        MediaType imageMediaType = imageResourceResponseBody.contentType();

        byte[] imageResourceResponseBytes = imageResourceResponseBody.bytes();
        imageResourceResponse.close();

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("image_type", "message");
        multipartBodyBuilder.addFormDataPart("image", "image", RequestBody.create(imageMediaType, imageResourceResponseBytes));

        Headers headers = new Headers.Builder().add("Authorization", "Bearer " + token).build();
        FeishuImageResult feishuImageResult = new HttpRequest<FeishuImageResult>(URL_FEISHU_PUT_IMAGE, new TypeToken<HttpResponse<FeishuImageResult>>() {
        }).doRequest(multipartBodyBuilder.build(), headers);

        return feishuImageResult.getImage_key();
    }

}
