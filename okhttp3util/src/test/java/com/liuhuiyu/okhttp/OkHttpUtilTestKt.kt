//package com.liuhuiyu.okhttp
//
//import org.junit.Test
//
///**
// * @author LiuHuiYu
// * @version v1.0.0.0
// * Created DateTime 2021-04-01 10:00
// */
//class OkHttpUtilTestKt {
//    @Test
//    fun test01() {
//        print("this is my first Kotlin.\n")
//    }
//
//    private fun getUrl(url: String): String {
//        return "http://192.168.2.7:8006$url"
//    }
//
//    @Test
//    fun testGetForLogin() {
//        val url: String = this.getUrl("/Authentication/Login")
//        val okHttpUtil = OkHttpUtil.create()
//        okHttpUtil.addQueryParameter("userid", "ZH_Admin")
//        okHttpUtil.addQueryParameter("password", "UmVob21lLnpocHNAMjAyMA==")
//        val res = okHttpUtil.executeGet(url)
//        val resString = okHttpUtil.executeGetToString(url)
//        val resMap = okHttpUtil.executeGetToMap(url)
//    }
//
//    @Test
//    fun testTime() {
//        for (i in 0..99) {
//            testGetForLogin()
//        }
//    }
//
//    private fun getToken(): String {
//        val url = getUrl("/Authentication/Login")
//        val okHttpUtil = OkHttpUtil.create()
//        okHttpUtil.addQueryParameter("userid", "ZH_Admin")
//        okHttpUtil.addQueryParameter("password", "UmVob21lLnpocHNAMjAyMA==")
//        val resMap = okHttpUtil.executeGetToMap(url)
//        return resMap["token"].toString()
//    }
//
//    @Test
//    fun testGet02() {
//        val url = getUrl("/U_RS_BASICINFO/GetUserInfoByManID")
//        val token = getToken()
//        val okHttpUtil = OkHttpUtil.create()
//        okHttpUtil.headerAuthorizationByBearerToken(token)
//        okHttpUtil.headerAuthorizationByBearerToken(token)
//        val res = okHttpUtil.executeGet(url)
//        val resString = okHttpUtil.executeGetToString(url)
//        val resMap = okHttpUtil.executeGetToMap(url)
//    }
//
//    @Test
//    fun testFor() {
//        val num = 100000
//        var n = 0
//        var m: Long = 0
//        for (k in 0..50) {
//            for (j in 0..10000) {
//                n = 0
//                for (i in 0..num) {
//                    n += i * 2 - 1
//                    m++
//                }
//            }
//        }
//        println("$m,$n")
//    }
//}