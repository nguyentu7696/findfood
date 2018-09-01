package com.anhtu.hongngoc.findfood.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anhtu.hongngoc.findfood.R;
import com.facebook.FacebookSdk;

public class DangNhapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        setContentView(R.layout.layout_dangnhap);
    }
}
