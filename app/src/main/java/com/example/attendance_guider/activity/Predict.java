package com.example.attendance_guider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance_guider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;

public class Predict extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String email;
    private EditText e1;
    private EditText e2;
    private EditText cri;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private Button b1;
    private Button b2;
    private Button b3;
    static public int x;
    static public int y;
    static public double criteria;
    DecimalFormat round = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.predict);


        Intent intent=getIntent();
        email = intent.getStringExtra("mails");



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.putExtra("mails",email);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bar:
                        Intent intent1 = new Intent(getApplicationContext(), Bar.class);
                        intent1.putExtra("mails",email);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.predict:
                        return true;
                }
                return false;
            }
        });


        cri=(EditText) findViewById(R.id.criteria);
        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(TextUtils.isEmpty(cri.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Criteria can't be empty",Toast.LENGTH_LONG).show();
                    return;
                }

                criteria=Double.parseDouble(cri.getText().toString());

                if(criteria==0 || criteria==100)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Criteria",Toast.LENGTH_LONG).show();
                    return;
                }

                x=Integer.parseInt(e1.getText().toString());
                y=Integer.parseInt(e2.getText().toString());
                double  z= calculate(x,y);
                String s= String.valueOf(round.format(z));
                t1.setText(""+s+"%" );
                t2.setText(""+x);
                t3.setText("/"+y);
                int count=0;
                int count1=0;
                int m;
                int n;
                m=x;
                n=y;
                double k;
                k=z;
                if(z<criteria) {
                    while (k < criteria) {
                        count++;
                        m++;
                        n++;
                        k = calculate(m, n);
                    }
                }
                else {
                    while (k >=criteria) {
                        count1++;
                        n++;
                        k = calculate(m, n);
                    }
                }
                if(count!=0)
                {
                    t4.setText("U Have To Attend "+count+"Classes");
                }
                if(count1!=0&&count1!=1)
                {
                    t4.setText("U May Leave "+(count1-1)+"Classes");
                }
                if(count1==1) {
                    int count2 = 0;
                    int d,e;
                    double f;
                    f=z;
                    d=x;
                    e=y;
                    e++;

                    do {
                        count2++;
                        d++;
                        e++;
                        f = calculate(d,e);
                    } while (f <criteria);

                    t4.setText("U Have To Attend "+(count2)+"Classes");
                }
            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                x=x+1;
                y=y+1;
                double  z= calculate(x,y);
                String s= String.valueOf(round.format(z));
                t1.setText(s+"%" );
                t2.setText(""+x);
                t3.setText("/"+y);
                int count=0;
                int count1=0;
                int m;
                int n;
                m=x;
                n=y;
                double k;
                k=z;
                if(z<criteria) {
                    while (k < criteria) {
                        count++;
                        m++;
                        n++;
                        k = calculate(m, n);
                    }
                }
                else {
                    while (k >= criteria) {
                        count1++;
                        n++;
                        k = calculate(m, n);
                    }
                }
                if(count!=0)
                {
                    t4.setText("U Have To  Attend "+count+"Classes");
                }
                if(count1!=0&&count1!=1)
                {
                    t4.setText("U May Leave "+(count1-1)+"Classes");
                }

                if(count1==1) {
                    int count2 = 0;
                    n++;
                    int d,e;
                    double f;
                    f=z;
                    d=x;
                    e=y;
                    e++;

                    do {
                        count2++;
                        d++;
                        e++;
                        f = calculate(d,e);
                    } while (f<criteria);

                    t4.setText("U Have To Attend "+(count2)+"Classes");
                }




            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                y=y+1;
                double  z= calculate(x,y);
                String s= String.valueOf(round.format(z));

                t1.setText(s+"%" );
                t2.setText(""+x);
                t3.setText("/"+y);
                int count=0;
                int count1=0;
                int m;
                int n;
                m=x;
                n=y;
                double k;
                k=z;
                if(z<criteria) {
                    while (k < criteria) {
                        count++;
                        m++;
                        n++;
                        k = calculate(m, n);
                    }
                }
                else {
                    while (k >= criteria) {
                        count1++;
                        n++;
                        k = calculate(m, n);
                    }
                }
                if(count!=0)
                {
                    t4.setText("U Have To  Attend "+count+"Classes");
                }
                if(count1!=0&&count1!=1)
                {
                    t4.setText("U May Leave "+(count1-1)+"classes");
                }

                if(count1==1) {
                    int count2 = 0;
                    int d,e;
                    double f;
                    f=z;
                    d=x;
                    e=y;
                    e++;
                    do {
                        count2++;
                        d++;
                        e++;
                        f= calculate(d,e);
                    } while (f <criteria);

                    t4.setText("U Have To Attend "+(count2)+"Classes");
                }
            }
        });


    }

    public double  calculate (int x,int y)
    {
        double z;
        if(y==0||y<0||x<0||x>y)
        {
            Toast.makeText(this,"Invalid",Toast.LENGTH_LONG).show();
            return 0;
        }
        z=(double)x/(double)y;
        z=z*100;
        return z;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
