package com.piaoquantv.plugin

import com.android.build.gradle.api.BaseVariant
import com.piaoquantv.ApkUploader
import com.piaoquantv.bean.property.PackProperty
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

                def packProperties = new ArrayList()
                def chatNameProperty = ""

                println("apkFile =  " + apkFile.path)

                def propertyIterator = targetProject.getProperties().keySet().iterator()
                while (propertyIterator.hasNext()) {
                    def propertyName = propertyIterator.next()
                    if (PackProperty.isCustomProperty(propertyName)) {
                        def name = propertyName.replace(PackProperty.CUSTOM_PROPERTY_FLAG, "")
                        if (PackProperty.CHAT_NAME == name) {
                            chatNameProperty = targetProject.getProperties().get(propertyName)
                        } else {
                            packProperties.add(PackProperty.build(name, targetProject.getProperties().get(propertyName)))
                        }
                    }
                }

                println("properties build complete !")


                ApkUploader.start(apkFile.path, extension.pgyApiKey,
                        (chatNameProperty == null || chatNameProperty.isBlank()) ? extension.chatName : chatNameProperty,
                        extension.feishuBotAppId, extension.feishuBotAppSecret, packProperties)
            }
        } catch (Exception e) {
            e.printStackTrace()
            throw Exception("TestAssistTask abort ")
        }
    }
}
