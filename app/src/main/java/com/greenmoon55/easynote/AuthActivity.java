package com.greenmoon55.easynote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.sns.*;


public class AuthActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo,
                    "https://leancloud.cn/1.1/sns/goto/edqwvuh8ywvaxzs8");
        } catch (AVException e) {
            e.printStackTrace();
        }
        SNS.loginWithCallback(this, SNSType.AVOSCloudSNSSinaWeibo, new SNSCallback() {
            @Override
            public void done(SNSBase base, SNSException e) {
            if (e == null) {
                SNS.loginWithAuthData(base.userInfo(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(final AVUser user, AVException e) {
                        if (e == null) {
                            System.out.println(AVUser.getCurrentUser());
                        } else {
                            System.out.println("create new user with auth data error: " + e.getMessage());
                        }
                        finish();
                    }
                });
            }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SNS.onActivityResult(requestCode, resultCode, data, SNSType.AVOSCloudSNSSinaWeibo);
    }
}