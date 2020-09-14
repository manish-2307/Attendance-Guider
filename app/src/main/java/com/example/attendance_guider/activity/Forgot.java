package com.example.attendance_guider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendance_guider.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {

    EditText e1;
    Button b1;
    FirebaseAuth FAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        e1 = findViewById(R.id.editText);
        b1 = findViewById(R.id.button);

        FAuth = FirebaseAuth.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FAuth.sendPasswordResetEmail(e1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Forgot.this,"Password sent to your mail",Toast.LENGTH_LONG).show();
                        }else
                        { Toast.makeText(Forgot.this,"Required Email Is Not Valid!!!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
