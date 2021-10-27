package com.piaoquantv.util;

import com.piaoquantv.ApkUploader;
import com.piaoquantv.bean.PgyUploadResult;
import com.piaoquantv.bean.model.Message;
import com.piaoquantv.bean.model.config.MessageConfig;
import com.piaoquantv.bean.model.element.BaseElement;
import com.piaoquantv.bean.model.element.DivElement;
import com.piaoquantv.bean.model.element.Filed;
import com.piaoquantv.bean.model.element.ImageElement;
import com.piaoquantv.bean.model.element.MarkDownText;
import com.piaoquantv.bean.model.header.HeadTitle;
import com.piaoquantv.bean.model.header.MessageHeader;
import com.piaoquantv.bean.property.PackProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by nieqi on 2021/7/9
 */
public class MessageHelper {

    public static Message buildMessageContent(PgyUploadResult pgyUploadResult, String imageKey, List<PackProperty> packProperties) {

        MessageConfig messageConfig = new MessageConfig();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.title = new HeadTitle(String.format("【Android】%s", pgyUploadResult.getBuildName()));

        List<BaseElement> elements = new ArrayList<>();
        ImageElement imageElement = new ImageElement();
        imageElement.img_key = imageKey;
        elements.add(imageElement);

        DivElement textElement = new DivElement();
        for (PackProperty packProperty : packProperties) {
            textElement.fields.add(new Filed(new MarkDownText(String.format("**%s**：%s", packProperty.name, packProperty.value))));
        }
        textElement.fields.add(new Filed(new MarkDownText(String.format("**打包人**：%s", System.getProperty("user.name")))));
        textElement.fields.add(new Filed(new MarkDownText("**安装密码：**123456")));
        textElement.fields.add(new Filed(new MarkDownText(String.format("**版本号：**%s", pgyUploadResult.getBuildVersion()))));
        textElement.fields.add(new Filed(new MarkDownText(String.format("**蒲公英构建版本号：**%s", pgyUploadResult.getBuildBuildVersion()))));
        textElement.fields.add(new Filed(new MarkDownText(String.format("[点击下载App历史版本](%s)", "https://www.pgyer.com/" + pgyUploadResult.getBuildShortcutUrl()))));
        elements.add(textElement);

        return new Message(messageConfig, messageHeader, elements);
    }
}
