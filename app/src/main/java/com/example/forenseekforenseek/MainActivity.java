package com.example.forenseekforenseek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_signup,tv_forgot;
    Button btn_signin;
    EditText et_signin_id,et_signin_pwd;
    private ProgressBar progressBar;
    private int CurrentProgress = 0;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv_signup = (TextView) findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(this);

        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(this);
        tv_forgot = (TextView) findViewById(R.id.tv_forgot);
        tv_forgot.setOnClickListener(this);
        et_signin_id = (EditText) findViewById(R.id.et_signin_id);
        et_signin_pwd= (EditText) findViewById(R.id.et_signin_pwd);
//        mAuth = FirebaseAuth.getInstance();
        mAuth= FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_signup:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.btn_signin:
                CurrentProgress = CurrentProgress + 1;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(10);
                progressBar.setVisibility(View.VISIBLE);
                userLogin();

                break;
            case R.id.tv_forgot:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = et_signin_id.getText().toString().trim();
        String password = et_signin_pwd.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_signin_pwd.setError("Password is required");
                et_signin_pwd.requestFocus();
                return;

            }
            if (password.length() < 6) {
                et_signin_pwd.setError("Minimum lenght for password is 6");
                et_signin_id.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user.isEmailVerified()) {
                            startActivity(new Intent(MainActivity.this, Dashboard.class));
                            Toast.makeText(MainActivity.this, "LOGGING IN", Toast.LENGTH_LONG).show();
                        }else {
                            user.sendEmailVerification();
                            Toast.makeText(MainActivity.this, "CHECK YOUR EMAIL FOR VERIFICATION", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(MainActivity.this, "Failed to login, Please check your credentials", Toast.LENGTH_LONG).show();
                    }
                }

                ;

            });
        } else {
            et_signin_id.setError("Email is required");
            et_signin_id.requestFocus();
            return;

        }
    }


}