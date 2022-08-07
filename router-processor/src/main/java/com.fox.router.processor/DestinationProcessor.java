package com.fox.router.processor;

import com.fox.router.annotations.Destination;
import com.google.auto.service.AutoService;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
class DestinationProcessor extends AbstractProcessor {
    private static final String TAG = "DestinationProcessor";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    //要处理哪些注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Destination.class.getCanonicalName());
    }

    //怎么处理这些目标注解
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println(TAG + ">>> process start ...");
        //获取所有标记了@Destination 注解的类的信息
        Set<? extends Element> allElements = roundEnvironment.getElementsAnnotatedWith(
                Destination.class);
        System.out.println(TAG + ">>> all Destination elements count = " + allElements.size());
        //如果数量为0 则全部处理完了
        if (allElements.size() < 1) {
            return false;
        }

        //遍历所有@Destination 注解信息 依次获取详细信息
        for (Element element : allElements) {
            //尝试在当前类中获取注解信息
            final TypeElement typeElement = (TypeElement) element;
            Destination destination = typeElement.getAnnotation(Destination.class);
            if(destination == null) continue;

            //非空 开始解析
            String url = destination.url();
            String description = destination.description();
            //拿到当前类的全路径
            String realPath = typeElement.getQualifiedName().toString();

            System.out.println(TAG + ">>> url = " + url + " description = " + description + " realPath = " + realPath);
        }
        System.out.println();
        return false;
    }
}