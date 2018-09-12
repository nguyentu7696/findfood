package com.anhtu.hongngoc.findfood.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anhtu.hongngoc.findfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class KhoiPhucMatKhauActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtDangKyKP;
    private Button btnGuiEmail;
    private EditText edEmailKP;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenmatkhau);

        firebaseAuth = FirebaseAuth.getInstance();

        txtDangKyKP = (TextView) findViewById(R.id.txtDangKyKP);
        btnGuiEmail = (Button) findViewById(R.id.btnGuiEmailKP);
        edEmailKP = (EditText) findViewById(R.id.edEmailKP);

        btnGuiEmail.setOnClickListener(this);
        txtDangKyKP.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGuiEmailKP:
                String email = edEmailKP.getText().toString();
                boolean kiemtraemail = kiemTraEmail(email);

                if(kiemtraemail){
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(KhoiPhucMatKhauActivity.this,getString(R.string.thongbaoguimailthanhcong),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(KhoiPhucMatKhauActivity.this,getString(R.string.thongbaoemailkhonghople),Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnDangKy:
                Intent iDangKy = new Intent(KhoiPhucMatKhauActivity.this, DangKyActivity.class);
                startActivity(iDangKy);
        }
    }

    public boolean kiemTraEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
