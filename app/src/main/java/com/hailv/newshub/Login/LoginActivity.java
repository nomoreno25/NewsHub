package com.hailv.newshub.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hailv.newshub.MainActivity;
import com.hailv.newshub.R;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etTaikhoan;
    EditText etMatkhau;
    Button btnDangnhap , btnFacebook , btnGoogle;
    TextView tvQuenmatkhau , tvDangky;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        Anhxa();

        btnDangnhap.setOnClickListener(this);
        tvDangky.setOnClickListener(this);
        tvQuenmatkhau.setOnClickListener(this);
    }

    private void Anhxa () {
        etTaikhoan = findViewById(R.id.etTaikhoan);
        etMatkhau = findViewById(R.id.etMatkhau);
        btnDangnhap = findViewById(R.id.btnDangnhap);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);
        tvQuenmatkhau = findViewById(R.id.tvQuenmatkhau);
        tvDangky = findViewById(R.id.tvDangky);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDangnhap){
            userLogin();
        }
        if (v == tvDangky){
            finish();
            startActivity(new Intent(this,RegisterActivity.class));
        }
        if (v == tvQuenmatkhau){
            startActivity(new Intent(this,ForgetPasswordActivity.class));
        }
    }

    private void userLogin() {
        String taikhoan = etTaikhoan.getText().toString().trim();
        String matkhau = etMatkhau.getText().toString().trim();

        if (TextUtils.isEmpty(taikhoan)){
            Toast.makeText(this,"Mời bạn nhập tài khoản!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(matkhau)){
            Toast.makeText(this,"Mời bạn nhập mật khẩu!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Đang đăng nhập...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(taikhoan,matkhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this,"Sai mật khẩu!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
