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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText mailid, pwd, Fullname;
    private Button banner;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        banner = (Button) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        Fullname = (EditText) findViewById(R.id.Fullname);
        mailid = (EditText) findViewById(R.id.mailid);
        pwd = (EditText) findViewById(R.id.pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_signup:
                btn_signup();

                break;
        }
    }

    private void btn_signup() {
        String email = mailid.getText().toString().trim();
        String password = pwd.getText().toString().trim();
        String fullname = Fullname.getText().toString().trim();

        if (fullname.isEmpty()) {
            Fullname.setError("Name is required");
            Fullname.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            mailid.setError("Please provide valid email!");
            mailid.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pwd.setError("Please provide valid password!");
            pwd.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailid.setError("please input email");
            mailid.requestFocus();
            return;
        }


        if (password.length() < 6) {
            pwd.setError("Please put strong password!");
            pwd.requestFocus();
            return;}
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Map<String,String> user = new HashMap<>();
                                user.put("FullName",fullname);
                                user.put("Email",email);
                                user.put("Password",password);
                               FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                      .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(Register.this, MainActivity.class));
                                           Toast.makeText(Register.this, "User has been registered!", Toast.LENGTH_LONG).show();


                                       } else {
                                          Toast.makeText(Register.this, "Please Enter Valid Credentials!", Toast.LENGTH_SHORT).show();
                                     }
                                   }
                              });
                            } else {
                               Toast.makeText(Register.this, "Invalid!", Toast.LENGTH_SHORT).show();
                          }
                      }

                   });

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullname,email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been registered!",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(Register.this,"Invalid", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }

                    }
                });
        }
    }
