package com.anhtu.hongngoc.findfood.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anhtu.hongngoc.findfood.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener{

    private TextView txtDangKyMoi,txtQuenMatKhau;
    private Button btnDangNhapGoogle;
    private Button btnDangNhapFacebook;
    private Button btnDangNhap;
    private EditText edEmail,edPassword;


    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    public static int KIEMTRA_PROVIDER_DANGNHAP = 0;

    // [START declare_auth]
    private FirebaseAuth firebaseAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackFacebook;
    private LoginManager loginManagerFacebook;
    private List<String> permissionFacebook = Arrays.asList("email","public_profile");

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        setContentView(R.layout.layout_dangnhap);

        // [START initialize_auth]
        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signOut();
        // [END initialize_auth]
        mCallbackFacebook = CallbackManager.Factory.create();
        loginManagerFacebook = LoginManager.getInstance();

        btnDangNhapGoogle = (Button) findViewById(R.id.btnDangNhapGoogle);
        btnDangNhapFacebook = (Button) findViewById(R.id.btnDangNhapFacebook);
        btnDangNhap = (Button)findViewById(R.id.btnDangNhap);
        edEmail = (EditText) findViewById(R.id.edEmailDN);
        edPassword = (EditText) findViewById(R.id.edPasswordDN);
        txtDangKyMoi = (TextView) findViewById(R.id.txtDangKyMoi);
        txtQuenMatKhau = (TextView) findViewById(R.id.txtQuenMatKhau);
        btnDangNhapGoogle.setOnClickListener(this);
        btnDangNhapFacebook.setOnClickListener(this);
        txtDangKyMoi.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        txtQuenMatKhau.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("luudangnhap",MODE_PRIVATE);

        taoClientDangNhapGoogle();
    }

    private void taoClientDangNhapGoogle() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, signInOptions);
    }

    private void dangNhapGoogle() {
        // mở form đăng nhập google
        KIEMTRA_PROVIDER_DANGNHAP = 1;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void dangNhapFacebook(){
        loginManagerFacebook.logInWithReadPermissions(this, permissionFacebook);
        loginManagerFacebook.registerCallback(mCallbackFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                KIEMTRA_PROVIDER_DANGNHAP = 2;
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                chungThucDangNhapFireBase(null, loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    chungThucDangNhapFireBase(account,null);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    // ...
                };
            }
        }else{
            //mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void chungThucDangNhapFireBase(GoogleSignInAccount account, AccessToken token) {
        if (KIEMTRA_PROVIDER_DANGNHAP == 1) {
            // Lấy stokenID đã đăng nhập bằng google để đăng nhập trên Firebase
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential);
        }else if(KIEMTRA_PROVIDER_DANGNHAP == 2){
            AuthCredential authCredential = FacebookAuthProvider.getCredential(token.getToken());
            firebaseAuth.signInWithCredential(authCredential);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }


    private void dangNhap(){
        String email = edEmail.getText().toString();
        String matkhau = edPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(DangNhapActivity.this,getString(R.string.thongbaodangnhapthatbai),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangNhapGoogle:
                dangNhapGoogle();
                break;
            case R.id.btnDangNhapFacebook:
                dangNhapFacebook();
                break;
            case R.id.txtDangKyMoi:
                Intent iDangKy = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(iDangKy);
                break;
            case R.id.btnDangNhap:
                dangNhap();
                break;
            case R.id.txtQuenMatKhau:
                Intent iKhoiPhucMatKhau = new Intent(DangNhapActivity.this, KhoiPhucMatKhauActivity.class);
                startActivity(iKhoiPhucMatKhau);
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        // kiểm tra người dùng đăng nhập thành công hay đăng suất
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mauser",firebaseUser.getUid());
            editor.commit();

            Intent iTrangChu = new Intent(this, TrangChuActivity.class);
            startActivity(iTrangChu);
        } else {

        }
    }

}
