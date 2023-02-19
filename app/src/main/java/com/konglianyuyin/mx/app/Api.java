/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.konglianyuyin.mx.app;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {

    /**
     * 0://线上环境
     * 1://测试环境
     */
    public static boolean IS_DEBUG = false;

    //todo 域名http://www.wx00fx.cn
//    public static String APP_DOMAIN = "http://b.juchuyuncang.com/api/";//测试
//    public static String APP_DOMAIN3 = "www.wx00fx.cn";//
//    public static String APP_DOMAIN2 = "http://www.wx00fx.cn/";//
//    public static String APP_DOMAIN = "http://www.wx00fx.cn/api/";//

//    public static String APP_DOMAIN3 = "www.xiamawang.cn";//
//    public static String APP_DOMAIN2 = "http://www.xiamawang.cn/";//
//    public static String APP_DOMAIN = "http://www.xiamawang.cn/api/";//

    //public static String APP_DOMAIN3 = "yuyin.weimawangluo.com";//
    public static String APP_DOMAIN3 = "61.160.221.140:2023";//

    //public static String APP_DOMAIN2 = "http://www.2077pb.com/";//
    //public static String APP_DOMAIN4 = "http://www.2077pb.com";//
    //public static String APP_DOMAIN = "http://www.2077pb.com/api/";//
    //public static String APP_DOMAIN = "http://taoz.jiangkukeji360.com/api/";//

    //public static String APP_DOMAIN = "http://www.2077pb.com/api/";
    //public static String APP_DOMAIN_NoApi = "http://www.2077pb.com/";

    //public static String APP_DOMAIN = "http://taoz.jiangkukeji360.com/api/";
    //public static String APP_DOMAIN_NoApi = "http://taoz.jiangkukeji360.com/";

    public static String APP_DOMAIN = "http://61.160.221.140:2023/api/";
    public static String APP_DOMAIN_NoApi = "http://61.160.221.140:2023/";



//    public static String APP_DOMAIN = "http://yuyin.huazhuangpin168.cn/api/";//
//    public static String APP_DOMAIN = "http://cisheng.club/api/";//

    //todo 融云key
//    public static String RONG_YUN_KEY = "4z3hlwrv45hdt";//融云key
    public static String RONG_YUN_KEY = "x18ywvqfxzn4c";//融云key
    //todo 替换声网
//    public static String AGORA_KEY = "92d289a6254f4f0399b5e3f5cb6103fc";//声网key
//    public static String AGORA_KEY = "8de6502d02a84856ac3f460c2407be8e";//声网key
    public static String AGORA_KEY = "a44c4baf632c4794acd97917959bdd9b";//声网key


    String CHANNEL = "guanfang";


    String RequestSuccess = "0";
}
