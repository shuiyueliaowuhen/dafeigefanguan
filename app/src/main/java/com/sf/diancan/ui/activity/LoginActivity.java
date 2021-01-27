package com.sf.diancan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sf.diancan.R;
import com.sf.diancan.UserInfoHolder;
import com.sf.diancan.bean.User;
import com.sf.diancan.biz.UserBiz;
import com.sf.diancan.net.CommonCallback;
import com.sf.diancan.utils.T;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

public class LoginActivity extends BaseActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mBtnRegister;

    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASSWORD = "key_password";

    UserBiz userBiz = new UserBiz();


    public LoginActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initEvent();

        initIntent(getIntent());

    }

    @Override
    protected void onResume() {
        super.onResume();
        CookieJarImpl cookieJar = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        cookieJar.getCookieStore().removeAll();
    }

    private void initView() {
        mEtUsername = findViewById(R.id.id_et_username);
        mEtPassword = findViewById(R.id.id_et_password);
        mBtnLogin = findViewById(R.id.id_btn_login);
        mBtnRegister = findViewById(R.id.id_btn_register);
    }

    private void initEvent() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    T.showToast("账号或者密码不能为空");
                    return;
                }

                startLoadingProgress();

                userBiz.login(username, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User user) {
                        stopLoadingProgress();
                        T.showToast("登录成功");
                        UserInfoHolder.getInstance().setUser(user);
                        toOrderActivity();
                        finish();
                    }
                });
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterActivity();
            }
        });
    }

    private void toRegisterActivity() {
        Intent intent =new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void toOrderActivity() {
        Intent intent =new Intent(this,OrderActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        initIntent(intent);
    }

    private void initIntent(Intent intent) {
        if(null == intent){
            return;
        }
        String userName = intent.getStringExtra(KEY_USERNAME);
        String password = intent.getStringExtra(KEY_PASSWORD);
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
            return;
        }
        mEtUsername.setText(userName);
        mEtPassword.setText(password);
    }

    public static void launch(Context context, String username, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KEY_USERNAME, username);
        intent.putExtra(KEY_PASSWORD, password);
        context.startActivity(intent);
    }

}
