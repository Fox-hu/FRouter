package com.fox.router.gradle

import groovy.json.JsonSlurper
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
         *     kapt {          arguments {              arg("root_project_dir", rootProject.projectDir.absolutePath)
         *}}*/

        println("get root project dir")
        //1.帮用户传递路径参数到注解处理器中
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.getRootProject().projectDir.absolutePath)
            }
        }

        //2.实现旧构建产物的自动清理
        project.clean.doFirst {
            //删除上一次构建生成的 router_mapping目录
            File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
            if (routerMappingDir.exists()) {
                println("delete dir = ${routerMappingDir.absolutePath}")
                routerMappingDir.deleteDir()
            }
        }

        println("from RouterPlugin, apply from ${project.name}")
        //注册extension
        project.getExtensions().create("router", RouterExtension)
        project.afterEvaluate {
            //获取用户配置的插件参数
            RouterExtension extension = project["router"]
            println("setting dir = ${extension.wikiDir}")

            //3.在javac任务后(compileDebugJavaWithJavac) 汇总生成文档
            project.tasks.findAll {
                it.name.startsWith('compile') && it.name.endsWith('JavaWithJavac')
            }.each {
                it.doLast {
                    File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
                    if (!routerMappingDir.exists()) {
                        return
                    }
                    File[] allChildFiles = routerMappingDir.listFiles()
                    if (allChildFiles.length < 1) {
                        return
                    }

                    StringBuilder markdownBuilder = new StringBuilder()
                    markdownBuilder.append("# 页面文档\n\n")
                    allChildFiles.each { child ->
                        if (child.name.endsWith(".json")) {
                            JsonSlurper jsonSlurper = new JsonSlurper()
                            def content = jsonSlurper.parse(child)
                            content.each { innerContent ->

                                def url = innerContent['url']
                                def description = innerContent['description']
                                def realPath = innerContent['realPath']

                                markdownBuilder.append("## $description \n")
                                markdownBuilder.append("- url: $url \n")
                                markdownBuilder.append("- realPath: $realPath \n\n")
                            }
                        }
                    }

                    File wikiFileDir = new File(extension.wikiDir)
                    if (!wikiFileDir.exists()) {
                        wikiFileDir.mkdir()
                    }

                    File wikiFile = new File(wikiFileDir, "页面文档.md")
                    if (wikiFile.exists()) {
                        wikiFile.delete()
                    }
                    wikiFile.write(markdownBuilder.toString())
                }
            }
        }
    }
}