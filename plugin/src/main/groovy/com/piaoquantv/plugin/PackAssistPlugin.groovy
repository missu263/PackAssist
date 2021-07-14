package com.piaoquantv.plugin

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

/**
 * Create by nieqi on 2021/7/7
 */
class PackAssistPlugin implements Plugin<Project> {

    public static final String PACK_ASSIST_EXTENSION = "packAssist"

    @Override
    void apply(Project target) {

        if (!target.plugins.hasPlugin("com.android.application")) {
            throw new ProjectConfigurationException("Plugin requires the 'com.android.application' plugin to be configured.", null);
        }

        def extension = target.extensions.create(PACK_ASSIST_EXTENSION, Extension)
        target.afterEvaluate {
            try {
                target.android.applicationVariants.all { BaseVariant variant ->
                    def variantName = variant.name.capitalize()

                    PackAssistTask packAssistTask = target.tasks.create("assemble${variantName}Assist", PackAssistTask);
                    packAssistTask.targetProject = target
                    packAssistTask.variant = variant
                    packAssistTask.setup()

                    if (variant.hasProperty('assembleProvider')) {
                        packAssistTask.dependsOn variant.assembleProvider.get()
                    } else {
                        packAssistTask.dependsOn variant.assemble
                    }
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new Exception("abort")
            }
        }
    }
}