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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etRetk , etRemk , etRee , etReremk;
    Button btnDangky;
    TextView tvExist;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        progressDialog = new ProgressDialog(this);

        Anhxa();

        btnDangky.setOnClickListener(this);
        tvExist.setOnClickListener(this);
    }

    private void Anhxa() {
        etRetk = findViewById(R.id.etRetk);
        etRemk = findViewById(R.id.etRemk);
        etRee = findViewById(R.id.etRee);
        etReremk = findViewById(R.id.etReremk);
        btnDangky = findViewById(R.id.btnDangky);
        tvExist = findViewById(R.id.tvExist);

    }

    private void registerUser() {
        String email = etRee.getText().toString().trim();
        String pass = etRemk.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Mời bạn nhập Email!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Mời bạn nhập mật khẩu!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            Toast.makeText(RegisterActivity.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this,"Không thành công! Mời thử lại!",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == btnDangky){
            registerUser();
        }
        if (v == tvExist) {
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
