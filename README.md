# PackAssist

为了方便给测试同学打包apk测试：
* 自动打包上传到蒲公英
* 发送APK下载消息到飞书群

![打包成功](https://github.com/missu263/PackAssist/raw/master/img/feishudownload.png)

## Quick Start
* Gradle插件方式，方便快速集成

#### 配置build.gradle

在位于项目的根目录 `build.gradle` 文件中添加Packassist Gradle插件的依赖， 如下：

```groovy
buildscript {
    dependencies {
        classpath 'io.github.missu263:packassist:1.2.0'
    }
}
```

并在当前App的 `build.gradle` 文件中apply这个插件

```groovy
apply plugin: 'pack.assist.plugin'
```

#### 配置插件
```groovy
packAssist {
    chatName ""
    feishuBotAppId ""
    feishuBotAppSecret ""
    pgyApiKey ""
}
```

配置项具体解释：
* pgyApiKey: 蒲公英apiKey

蒲公英，API信息中的`API Key` 

* feishuBotAppId: 飞书机器人的appId
* feishuBotAppSecret: 飞书机器人的appSecret
* chatName: 指定打包完成的下载信息发送至飞书哪个聊天群

（可选配置）发送到飞书群消息是基于`飞书机器人`，需要在 [飞书开放平台](https://open.feishu.cn/app) 注册，然后创建`企业自建应用`


![创建应用](https://github.com/missu263/PackAssist/raw/master/img/feishu_create.png)

![应用信息](https://github.com/missu263/PackAssist/raw/master/img/feishu_info.png)

* 在飞书群聊中添加`飞书机器人`


#### 如何打包

打包上传的方式是和`assemble${variantName}Assist`指令结合

用法示例：

* 打包 `./gradlew assembleReleaseAssist`
* 支持productFlavors `./gradlew assembleFreeReleaseAssist`

#### 更多用法

##### 插入额外信息
- 增加备注信息： `./gradlew assembleReleaseAssist -Pdescription=测试环境备注信息`
##### 自定义task
- 在`app/build.gradle`中自定义task，支持一键打包：
```groovy
task releasePackAssist {
  afterEvaluate {
      tasks.findByName('assembleReleaseAssist').doFirst {
          project.setProperty("description", "release")
      }
      dependsOn 'assembleReleaseAssist'
  }
}

task testPackAssist {
  afterEvaluate {
      tasks.findByName('assembleSnapshotAssist').doFirst {
          project.setProperty("description", "snapshot")
      }
      dependsOn 'assembleSnapshotAssist'
  }
}

task debugPackAssist {
  afterEvaluate {
      tasks.findByName('assembleDebugAssist').doFirst {
          project.setProperty("description", "debug")
      }
      dependsOn 'assembleDebugAssist'
  }
}
```




