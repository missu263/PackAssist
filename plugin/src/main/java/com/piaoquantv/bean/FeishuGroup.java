package com.piaoquantv.bean;

/**
 * Create by nieqi on 2021/7/8
 */
public class FeishuGroup {

    private String avatar;
    private String description;
    private String chat_id;
    private String name;
    private String owner_open_id;
    private String owner_user_id;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_open_id() {
        return owner_open_id;
    }

    public void setOwner_open_id(String owner_open_id) {
        this.owner_open_id = owner_open_id;
    }

    public String getOwner_user_id() {
        return owner_user_id;
    }

    public void setOwner_user_id(String owner_user_id) {
        this.owner_user_id = owner_user_id;
    }
}
