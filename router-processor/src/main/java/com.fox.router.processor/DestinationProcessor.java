package com.fox.router.processor;

import com.fox.router.annotations.Destination;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

class DestinationProcessor extends AbstractProcessor {
    private static final String TAG = "DestinationProcessor";

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

        }
        return false;
    }
}