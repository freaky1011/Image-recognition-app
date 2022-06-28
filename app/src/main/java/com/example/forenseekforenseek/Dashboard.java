package com.example.forenseekforenseek;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.forenseekforenseek.tflite.CameraActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.A;

public class Dashboard extends AppCompatActivity {

    Button btndescription, btnlogout, btnlist, btntxtprnt, btnpeople, btntexttospeech, btnspeechtotext, btnCameraActivity;

    TextView tvChangePass;


    private FirebaseAuth firebaseAuth;

    //this is the normal user screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        firebaseAuth = FirebaseAuth.getInstance();
        btndescription = findViewById(R.id.btnupdate);
        btntxtprnt = findViewById(R.id.btntxtprnt);
        btnlogout = findViewById(R.id.btnlogout);
//        tvChangePass = findViewById(R.id.tvChangePassword);
        btnlist = findViewById(R.id.btnlist);
        btnpeople = findViewById(R.id.btnpeople);
        btntexttospeech = findViewById(R.id.btntxttospeech);
        btnspeechtotext = findViewById(R.id.btnspeechtotext);
        btnCameraActivity = findViewById(R.id.btnCameraActivity);

        btnspeechtotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Speechtotext.class);
                startActivity(i);
            }
        });
        btntxtprnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Txtprnt.class);
                startActivity(i);
            }
        });
        btnpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Developerprofile.class);
                startActivity(i);
                // finish();
            }
        });

        btndescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Description.class);
                startActivity(i);
                // finish();
            }
        });

        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Singlerow.class));
            }
        });

        btntexttospeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Text2speech.class));
            }
        });
        btnCameraActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClassifierActivity.class));
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentL = new Intent( Dashboard.this, MainActivity.class) ;
//                startActivity(intentL);
//                finish();
//                Toast.makeText(Dashboard.this, "Successfully Logout",Toast.LENGTH_SHORT).show();

                if (R.id.btnlogout == R.id.btnlogout){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                    builder.setMessage("Do You Want To Logout?");
                    builder.setCancelable(true);

                    builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentL = new Intent( Dashboard.this, MainActivity.class) ;
                             startActivity(intentL);
                             finish();
                             Toast.makeText(Dashboard.this, "Successfully Logout",Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                }
                return;
            }

        });


    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.exitmenu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id ==R.id.exit){
//            AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
//            builder.setMessage("DO YOU WANT TO LOGOUT?");
//            builder.setCancelable(true);
//
//            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//
//           builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
//               @Override
//               public void onClick(DialogInterface dialog, int which) {
//                   dialog.cancel();
//               }
//           });
//           AlertDialog alertDialog = builder.create();
//           alertDialog.show();
//        }
//        return true;
//    }

}
