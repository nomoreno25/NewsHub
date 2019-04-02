package com.hailv.newshub.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hailv.newshub.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText etQuenmk;
    Button btnLaylai;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();

        Anhxa();

        btnLaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(etQuenmk.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            Toast.makeText(ForgetPasswordActivity.this,"Password đã được gửi về email!",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void Anhxa() {
        etQuenmk = findViewById(R.id.etQuenmk);
        btnLaylai = findViewById(R.id.btnLaylai);
    }
}
