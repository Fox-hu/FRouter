package com.fox.gradlepractice.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping {
    public static Map<String, String> get() {
        HashMap<String, String> mapping = new HashMap<>();
        mapping.putAll(RouterMapping_1.get());
        mapping.putAll(RouterMapping_2.get());
        return mapping;
    }
}
