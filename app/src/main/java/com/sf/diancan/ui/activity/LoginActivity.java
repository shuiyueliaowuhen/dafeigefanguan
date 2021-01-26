package com.sf.diancan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sf.diancan.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mBtnRegister;

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
                toOrderActivity();
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
