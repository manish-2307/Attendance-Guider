package com.example.attendance_guider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance_guider.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {


    EditText Name , Phone , mail , pass,pass2;
    Button reg;
    TextView alr ;
    FirebaseAuth fAuth;
    ProgressBar barr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phone);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        pass2 = findViewById(R.id.pass2);
        reg = findViewById(R.id.btn2);
        alr = findViewById(R.id.reg2);
        barr = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mail.getText().toString().trim();
                final String phone =Phone.getText().toString();
                final String name=Name.getText().toString();
                String password = pass.getText().toString().trim();
                String password2 = pass2.getText().toString().trim();


                if(TextUtils.isEmpty(name)) {
                    Name.setError("Name  is empty");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    mail.setError("Email is empty");
                    return;
                }

                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
                Pattern pat = Pattern.compile(emailRegex);
                if( !(pat.matcher(email).matches()))
                {
                    mail.setError("Invalid Email");

                }

                if(TextUtils.isEmpty(phone)) {
                    Phone.setError("Phone is empty");
                    return;
                }

                if(!(phone.length()==10)){
                    Phone.setError("Invalid Phone Number!!!!");
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



                if(!(password.equals(password2))){
                    pass2.setError("Password doesn't match!!!");
                    return;
                }

                barr.setVisibility(View.VISIBLE);
                //registering user in firebasesss
                fAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User is Created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Percent.class);
                            intent.putExtra("name",name);
                            intent.putExtra("email",email);
                            intent.putExtra("phone",phone);
                            startActivity(intent);

                        }else {
                            Toast.makeText(Register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            barr.setVisibility(View.GONE);
                        }
                    }
                } );
            }
        });
    }

    public void already(View view) {
        Intent in = new Intent(this, Login.class);
        startActivity(in);
    }

    public void gonext(View view) {
        Intent in1 = new Intent(this,Percent.class);
        startActivity(in1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
