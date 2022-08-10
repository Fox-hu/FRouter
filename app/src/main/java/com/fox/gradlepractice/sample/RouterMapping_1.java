package com.fox.gradlepractice.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping_1 {
        public static Map<String, String> get() {
        HashMap<String, String> mapping = new HashMap<>();
        mapping.put("router://","com.xxx.Activity");
        return mapping;
    }
}
