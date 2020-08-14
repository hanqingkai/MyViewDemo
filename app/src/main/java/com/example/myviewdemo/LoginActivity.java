package com.example.myviewdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_page_view)
    LoginPageView loginPageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPageView.setOnLoginPageActionListener(new LoginPageView.OnLoginPageActionListener() {
            @Override
            public void onGetVerifyCodeClick(String phoneNum) {
                //获取验证码
            }

            @Override
            public void onOpenAgreementClick() {

            }

            @Override
            public void onConfirmClick(String verifyCode, String phoneNum) {

            }
        });
    }
}
