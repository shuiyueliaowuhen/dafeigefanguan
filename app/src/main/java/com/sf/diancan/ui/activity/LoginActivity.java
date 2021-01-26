package com.sf.diancan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends BaseActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mBtnRegister;

    UserBiz userBiz = new UserBiz();


    public LoginActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initEvent();

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


}
