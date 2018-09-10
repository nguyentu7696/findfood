package com.anhtu.hongngoc.findfood.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anhtu.hongngoc.findfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDangKy;
    private EditText edEmailDK, edPasswordDK, edNhapLaiPasswordDK;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        firebaseAuth = FirebaseAuth.getInstance();

        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        edEmailDK = (EditText) findViewById(R.id.edEmailDK);
        edPasswordDK = (EditText) findViewById(R.id.edPasswordDK);
        edNhapLaiPasswordDK = (EditText) findViewById(R.id.edNhapLaiPasswordDK);

        btnDangKy.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {

        final String email = edEmailDK.getText().toString();
        String matkhau = edPasswordDK.getText().toString();
        String nhaplaimatkhau = edNhapLaiPasswordDK.getText().toString();
        String thongbaoloi = getString(R.string.thongbaoloidangky);

        if (email.trim().length() == 0) {
            thongbaoloi += getString(R.string.email);
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_SHORT).show();
        } else if (matkhau.trim().length() == 0) {
            thongbaoloi += getString(R.string.matkhau);
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_SHORT).show();
        } else if (!nhaplaimatkhau.equals(matkhau)) {
            Toast.makeText(this, getString(R.string.thongbaonhaplaimatkhau), Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, matkhau)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(DangKyActivity.this , "Authentication Successful.", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(DangKyActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}

