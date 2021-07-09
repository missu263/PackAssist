package com.piaoquantv.bean.model;

import com.piaoquantv.bean.model.config.MessageConfig;
import com.piaoquantv.bean.model.element.BaseElement;
import com.piaoquantv.bean.model.element.ImageElement;
import com.piaoquantv.bean.model.header.MessageHeader;

import java.util.List;

/**
 * Create by nieqi on 2021/7/9
 */
public class Message {

    private MessageConfig config;
    private MessageHeader header;
    private List<BaseElement> elements;

    public Message(MessageConfig config, MessageHeader header, List<BaseElement> elements) {
        this.config = config;
        this.header = header;
        this.elements = elements;
    }
}
