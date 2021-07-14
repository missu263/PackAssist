package com.piaoquantv.plugin

import com.android.build.gradle.api.BaseVariant
import com.piaoquantv.ApkUploader
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * Create by nieqi on 2021/7/7
 */
public class PackAssistTask extends DefaultTask {

    @Input
    public BaseVariant variant
    @Input
    public Project targetProject

    public static final String DESCRIPTION = "description"

    public void setup() {
        description " upload "
        group "Package"
    }

    @TaskAction
    public void start() {
        try {
            Extension extension = Extension.getConfig(targetProject);
            def iterator = variant.outputs.iterator();
            while (iterator.hasNext()) {
                def it = iterator.next()
                def apkFile = it.outputFile

                def description = ""
                if (targetProject.hasProperty(DESCRIPTION)) {
                    description = targetProject.getProperties().get(DESCRIPTION)
                }

                println("description = " + description)
                println("apkFile =  " + apkFile.path)

                ApkUploader.start(apkFile.path, extension.pgyApiKey, extension.chatName,
                        extension.feishuBotAppId, extension.feishuBotAppSecret, description)
            }
        } catch (Exception e) {
            e.printStackTrace()
            throw Exception("TestAssistTask abort ")
        }
    }
}
