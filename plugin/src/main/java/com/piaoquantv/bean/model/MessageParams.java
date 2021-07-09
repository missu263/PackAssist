package com.piaoquantv.bean.model;

/**
 * Create by nieqi on 2021/7/9
 */
public class MessageParams {

    private String chat_id;
    private String msg_type = "interactive";
    private Message card;

    public MessageParams(String chat_id, Message card) {
        this.chat_id = chat_id;
        this.card = card;
    }
}
