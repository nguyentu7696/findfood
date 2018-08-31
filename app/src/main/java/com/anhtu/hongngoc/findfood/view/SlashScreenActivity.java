package com.anhtu.hongngoc.findfood.view;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.anhtu.hongngoc.findfood.R;

public class SlashScreenActivity extends AppCompatActivity {

    TextView txtPhienBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flashscreen);

        txtPhienBan = (TextView)findViewById(R.id.txtPhienBan);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            txtPhienBan.setText( getString(R.string.phienban) + packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
