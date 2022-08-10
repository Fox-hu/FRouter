package com.fox.router.gradle

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RouterMappingTransform extends Transform {

    @Override
    String getName() {
        return "RouterMappingTransform"
    }

    //告知编译器 当前Transform需要消费的输入类型
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    //告知编译器 当前Transform需要消费的输入类型
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    //告知编译器 是否支持增量
    @Override
    boolean isIncremental() {
        return false
    }

    //所有的class收集好以后 会被打包传入此方法中
    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        //1.遍历所有的input
        //2.对input进行二次处理
        //3.将input拷贝到目标目录
        //文件分为两类 目录类型和jar类型 两者都需要处理

        RouterMappingCollector collector = new RouterMappingCollector()

        transformInvocation.inputs.each {
            //处理文件夹类型
            it.directoryInputs.each { directoryInput ->
                def destDir = transformInvocation.outputProvider.getContentLocation(
                        directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY
                )
                collector.collect(directoryInput.file)
                //将文件夹类型的文件拷贝到目标目录
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }
            //处理jar类型
            it.jarInputs.each { jarInput ->
                def dest = transformInvocation.outputProvider.getContentLocation(
                        jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
                )
                collector.collectFromJarFile(jarInput.file)
                //将jar类型的文件拷贝到目标目录
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
        println("${getName()} all mapping class name = " + collector.mappingClassName)

        File mappingJarFile = transformInvocation.outputProvider.getContentLocation("router_mapping", getOutputTypes(), getScopes(), Format.JAR)
        println("${getName()} mappingJarFile = $mappingJarFile")

        if (mappingJarFile.getParentFile().exists()) {
            mappingJarFile.getParentFile().mkdirs()
        }

        if (mappingJarFile.exists()) {
            mappingJarFile.delete()
        }

        FileOutputStream fos = new FileOutputStream(mappingJarFile)
        JarOutputStream jarOutputStream = new JarOutputStream(fos)
        ZipEntry zipEntry = new ZipEntry(RouterMappingByteCodeBuilder.CLASS_NAME + ".class")
        jarOutputStream.putNextEntry(zipEntry)
        jarOutputStream.write(RouterMappingByteCodeBuilder.get(collector.mappingClassName))
        jarOutputStream.closeEntry()
        jarOutputStream.close()
        fos.close()
    }
}