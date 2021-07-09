package com.piaoquantv.plugin

import org.gradle.api.Project

/**
 * Create by nieqi on 2021/7/7
 */
public class Extension {
    String chatName
    String feishuBotAppId
    String feishuBotAppSecret

    String pgyApiKey

    public static Extension getConfig(Project project) {
        Extension config = project.getExtensions().findByType(Extension.class);
        if (config == null) {
            config = new Extension()
        }
        return config
    }

}
