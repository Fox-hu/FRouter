package com.fox.router.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    //实现apply方法 注入插件逻辑
    @java.lang.Override
    void apply(Project project) {
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