package com.example.attendance_guider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendance_guider.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Boolean isLoggedIn;

    EditText mail,pass;
    FirebaseAuth fAuth;
    Button log;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);

      isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false);



        if(isLoggedIn)
        {
            Intent intent1 = new Intent(getApplicationContext(), Home.class);
            startActivity(intent1);
            finish();
        }
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        fAuth= FirebaseAuth.getInstance();
        log = findViewById(R.id.log);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    mail.setError("Email is empty");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    pass.setError("Password is empty");
                    return;
                }

                if(password.length()<6){
                    pass.setError("Password must be >6");
                    return;
                }
                log.setVisibility(View.INVISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            setSharedPreferences(email);
                            startActivity(intent);


                        }else {
                            log.setVisibility(View.VISIBLE);

                            Toast.makeText(Login.this,"Error! U Have Not Registered Yet!!!!!!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }


    public void Another(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void forgot(View view) {
        Intent i = new Intent(this, Forgot.class);
        startActivity(i);
    }

    public void setSharedPreferences(String email)
    {
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply();
        sharedPreferences.edit().putString("mails",email).apply();
    }

    @Override
    protected void onPause() {
        if(isLoggedIn) {
            super.onPause();
        }
    }
}
