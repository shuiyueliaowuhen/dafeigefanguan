package com.sf.diancan.biz;

import com.sf.diancan.bean.User;
import com.sf.diancan.config.Config;
import com.sf.diancan.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by zhy on 16/10/23.
 */
public class UserBiz {

    public void onDestory() {
        OkHttpUtils.getInstance().cancelTag(this);
    }

    public void register(String username, String password, CommonCallback<User> callBack) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "user_register")
                .tag(this)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .execute(callBack);
    }


    public void login(String username, String password, CommonCallback<User> callBack) {

        OkHttpUtils
                .post()
                .url(Config.baseUrl + "user_login")
                .tag(this)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .execute(callBack);
    }

}
