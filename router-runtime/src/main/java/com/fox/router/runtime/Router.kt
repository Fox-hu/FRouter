package com.fox.router.runtime

import android.util.Log

object Router {
    private const val TAG = "Router"
    private const val GENERATED_MAPPING = "com.fox.router.mapping.generated.RouterMapping"
    private val mapping: HashMap<String, String> = HashMap()

    fun init() {
        try {
            val clazz = Class.forName(GENERATED_MAPPING)
            val method = clazz.getMethod("get")
            val allMapping = method.invoke(null) as Map<String, String>
            if (allMapping.isNotEmpty()) {
                Log.i(TAG, "get all mapping")
                allMapping.forEach {
                    Log.i(TAG, " map  = ${it.key} -> ${it.value}")
                }
                mapping.putAll(allMapping)
            }
        } catch (e: Throwable) {
            Log.e(TAG, "init router error")
        }
    }

}