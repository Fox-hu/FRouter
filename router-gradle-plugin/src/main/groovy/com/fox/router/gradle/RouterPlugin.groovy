package com.fox.router.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    //实现apply方法 注入插件逻辑
    @Override
    void apply(Project project) {
        //1.自动帮助用户传递路径参数到注解处理器中
        //2.实现旧构建产物的自动清理
        //3.在javac任务后 汇总生成文档
        /**
         *     kapt {
         *         arguments {
         *             arg("root_project_dir", rootProject.projectDir.absolutePath)
         *         }
         *     }
         */

        println("get root project dir")
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir",project.getRootProject().projectDir.absolutePath)
            }
        }

        println("from RouterPlugin, apply from ${project.name}")
        //注册extension
        project.getExtensions().create("router", RouterExtension)

        project.afterEvaluate {
            //获取用户配置的插件参数
            RouterExtension extension = project["router"]
            println("setting dir = ${extension.wikiDir}")
        }
    }
}