package com.example.forenseekforenseek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText et_signin_id;
    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        et_signin_id =(EditText)findViewById(R.id.et_signin_id);
        btn_reset=(Button)findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpwd();
            }
        });
    }
    private void resetpwd(){
        String emailid=et_signin_id.getText().toString().trim();
        if (emailid.isEmpty()){
            et_signin_id.setError("Email is required");
            et_signin_id.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
            et_signin_id.setError("Please enter valid email");
            et_signin_id.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(emailid)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ForgotPassword.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else {
                            Toast.makeText(ForgotPassword.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}