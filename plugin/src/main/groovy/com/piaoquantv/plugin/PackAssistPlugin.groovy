package com.piaoquantv.plugin

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Create by nieqi on 2021/7/7
 */
class PackAssistPlugin implements Plugin<Project> {

    public static final String PACK_ASSIST_EXTENSION = "packAssist"

    @Override
    void apply(Project target) {
        def extension = target.extensions.create(PACK_ASSIST_EXTENSION, Extension)

        target.afterEvaluate {
            println("===========PackAssistPlugin working=============")
            try {
                target.android.applicationVariants.all { BaseVariant variant ->
                    def variantName = variant.name.capitalize()

                    PackAssistTask testAssistTask = target.tasks.create("assemble${variantName}Assist", PackAssistTask);
                    testAssistTask.targetProject = target
                    testAssistTask.variant = variant
                    testAssistTask.setup()

                    if (variant.hasProperty('assembleProvider')) {
                        testAssistTask.dependsOn variant.assembleProvider.get()
                    } else {
                        testAssistTask.dependsOn variant.assemble
                    }
                }
            } catch (Exception e) {
                e.printStackTrace()
                throw new Exception("abort")
            }
        }
    }
}