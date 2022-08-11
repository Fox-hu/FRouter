package com.fox.router.runtime

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

    fun go(context: Context, url: String) {
        //匹配url 找到目标页面
        //解析url里的参数 封装成bundle
        //打开对应Activity 传入参数

        // 匹配URL，找到目标页面 标准url如下
        // router://fox/profile?name=imooc&message=hello
        val uri = Uri.parse(url)
        val scheme = uri.scheme
        val host = uri.host
        val path = uri.path
        var targetActivityClass = ""

        mapping.onEach {
            val ruri = Uri.parse(it.key)
            val rscheme = ruri.scheme
            val rhost = ruri.host
            val rpath = ruri.path

            if (rscheme == scheme && rpath == path && host == rhost) {
                targetActivityClass = it.value
            }
        }

        if (targetActivityClass.isEmpty()) {
            Log.e(TAG, "go: no destination found")
            return
        }
        //拼装参数到bundle
        val bundle = Bundle()
        val query = uri.query
        query?.let {
            //这里为什么要是3呢 因为a=b最小也是三个字符
            //按照标准url解析name=imooc&message=hello
            if (it.length >= 3) {
                val args = it.split("&")
                args.onEach { arg ->
                    val splits = arg.split("=")
                    bundle.putString(splits[0], splits[1])
                }
            }
        }

        //打开对应的targetActivity 传入参数
        try {
            val target = Class.forName(targetActivityClass)
            val intent = Intent(context, target)
            intent.putExtras(bundle)
            context.startActivity(intent)
        } catch (e: Throwable) {
            Log.e(TAG, "start activity error : $targetActivityClass e = $e")
        }
    }

}