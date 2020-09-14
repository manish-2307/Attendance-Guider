package com.example.attendance_guider.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendance_guider.R;

import java.text.DecimalFormat;

public class AddSubject extends AppCompatActivity {


    private EditText subname,attended,total;
    private CheckBox c1,c2,c3,c4,c5,c6;
    private EditText e1,e2,e3,e4,e5,e6;
    String s1="0",s2="0",s3="0",s4="0",s5="0",s6="0";
    String l1="0",l2="0",l3="0",l4="0",l5="0",l6="0";
    String email;
    int a,b;
    double c;
    DecimalFormat round = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        subname=(EditText)findViewById(R.id.subject);
        total=(EditText)findViewById(R.id.total);
        attended=(EditText)findViewById(R.id.attended);
        c1=(CheckBox)findViewById(R.id.checkmon);
        c2=(CheckBox)findViewById(R.id.checktues);
        c3=(CheckBox)findViewById(R.id.checkwed);
        c4=(CheckBox)findViewById(R.id.checkthurs);
        c5=(CheckBox)findViewById(R.id.checkfri);
        c6=(CheckBox)findViewById(R.id.checksat);
        e1=(EditText)findViewById(R.id.mon);
        e2=(EditText)findViewById(R.id.tues);
        e3=(EditText)findViewById(R.id.wed);
        e4=(EditText)findViewById(R.id.thurs);
        e5=(EditText)findViewById(R.id.fri);
        e6=(EditText)findViewById(R.id.sat);

        Intent intent=getIntent();
        email = intent.getStringExtra("email");




    }

    public void addsub(View view) {

        if(TextUtils.isEmpty(subname.getText().toString())) {
            subname.setError("Subject name   is empty");
            return;
        }

        if(TextUtils.isEmpty(attended.getText().toString())) {
            attended.setError("attended lectures  is empty");
            return;
        }

        if(TextUtils.isEmpty(total.getText().toString()) ){
            total.setError(" total lectures is empty");
            return;
        }

        a=Integer.valueOf(total.getText().toString());
        b=Integer.valueOf(attended.getText().toString());

        if(b>a||b<0||a<0||a==0)
        {
            total.setError("Invalid  lectures");

        }
        else {

            c=calculate(b,a);
            String attendance = String.valueOf(round.format(c));

            if (c1.isChecked()) {
                s1 = "1";
                l1 = e1.getText().toString();

            }
            if (c2.isChecked()) {
                s2 = "1";
                l2 = e2.getText().toString();
            }
            if (c3.isChecked()) {
                s3 = "1";
                l3 = e3.getText().toString();
            }
            if (c4.isChecked()) {
                s4 = "1";
                l4 = e4.getText().toString();
            }
            if (c5.isChecked()) {
                s5 = "1";
                l5 = e5.getText().toString();
            }
            if (c6.isChecked()) {
                s6 = "1";
                l6 = e6.getText().toString();
            }
            if(c1.isChecked() && l1.equals("") ||c2.isChecked() && l2.equals("")||c3.isChecked() && l3.equals("")||c4.isChecked() && l4.equals("")||c5.isChecked() && l5.equals("")||c6.isChecked() && l6.equals(""))
            {
                Toast.makeText(this,"Enter Your Lectures ",Toast.LENGTH_LONG).show();
                return;
            }



                SubjectStorage subjectStorage = new SubjectStorage(this);
                subjectStorage.execute("sub", subname.getText().toString(), total.getText().toString(), attended.getText().toString(), email, attendance);
                TimeTable timeTable = new TimeTable(this);
                timeTable.execute("timetable", email, subname.getText().toString(), s1, s2, s3, s4, s5, s6, l1, l2, l3, l4, l5, l6);

            subname.setText("");
            total.setText("");
            attended.setText("");
            e1.setText("");
            e2.setText("");
            e3.setText("");
            e4.setText("");
            e5.setText("");
            e6.setText("");
            if(c1.isChecked()){  c1.toggle();}
            if(c2.isChecked()){  c2.toggle();}
            if(c3.isChecked()){  c3.toggle();}
            if(c4.isChecked()){  c4.toggle();}
            if(c5.isChecked()){  c5.toggle();}
            if(c6.isChecked()){  c6.toggle();}

             s1="0";s2="0";s3="0";s4="0";s5="0";s6="0";
             l1="0";l2="0";l3="0";l4="0";l5="0";l6="0";



        }





    }
    public void next(View view)
    {
        Intent intent = new Intent(AddSubject.this, Preview.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

    public double  calculate (int x,int y)
    {

        double z;
        z=(double)x/(double)y;
        z=z*100;
        return z;
    }


}


