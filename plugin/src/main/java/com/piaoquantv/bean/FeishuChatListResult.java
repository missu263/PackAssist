package com.piaoquantv.bean;

import java.util.List;

/**
 * Create by nieqi on 2021/7/8
 */
public class FeishuChatListResult {

    private boolean has_more;
    private String page_token;
    private List<FeishuGroup> groups;

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getPage_token() {
        return page_token;
    }

    public void setPage_token(String page_token) {
        this.page_token = page_token;
    }

    public List<FeishuGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FeishuGroup> groups) {
        this.groups = groups;
    }
}
