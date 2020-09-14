package com.example.attendance_guider.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendance_guider.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Display extends AppCompatActivity {

    private TextView e1,e2,e3,e4,e5,e6,t1;
    private CheckBox c1,c2,c3,c4,c5,c6;
    private RequestQueue mqueue;
    private String subjectname, email;
    private String mon,tues,wed,thurs,fri,sat,monLec,tuesLec,wednesLec,thursLec,friLec,saturLec,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        e1=(TextView) findViewById(R.id.mon);
        e2=(TextView) findViewById(R.id.tues);
        e3=(TextView) findViewById(R.id.wed);
        e4=(TextView) findViewById(R.id.thurs);
        e5=(TextView) findViewById(R.id.fri);
        e6=(TextView) findViewById(R.id.sat);
        t1=(TextView)findViewById(R.id.subjectname);

        c1=(CheckBox)findViewById(R.id.checkmon);
        c2=(CheckBox)findViewById(R.id.checktues);
        c3=(CheckBox)findViewById(R.id.checkwed);
        c4=(CheckBox)findViewById(R.id.checkthurs);
        c5=(CheckBox)findViewById(R.id.checkfri);
        c6=(CheckBox)findViewById(R.id.checksat);


        email = Preview.send();

        Intent intent = getIntent();
        subjectname=intent.getStringExtra("subject");

        mqueue= Volley.newRequestQueue(this);
        jsonparse();
    }



    private void jsonparse()
    {
        String url="https://attendanceguider.000webhostapp.com/display.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject timetable = array.getJSONObject(i);
                                String mail = timetable.getString("email");
                                name = timetable.getString("subject_name");

                                if (mail.equals(email) && subjectname.equals(name)) {

                                    mon = timetable.getString("monday");
                                    tues = timetable.getString("tuesday");
                                    wed = timetable.getString("wednesday");
                                    thurs = timetable.getString("thursday");
                                    fri = timetable.getString("friday");
                                    sat = timetable.getString("saturday");
                                    monLec = timetable.getString("mon_lec");
                                    tuesLec = timetable.getString("tues_lec");
                                    wednesLec = timetable.getString("wednes_lec");
                                    thursLec = timetable.getString("thurs_lec");
                                    friLec = timetable.getString("fri_lec");
                                    saturLec = timetable.getString("satur_lec");


                                if (mon.equals("1")) {
                                    c1.setChecked(true);
                                }
                                if (tues.equals("1")) {
                                    c2.setChecked(true);
                                }
                                if (wed.equals("1")) {
                                    c3.setChecked(true);
                                }
                                if (thurs.equals("1")) {
                                    c4.setChecked(true);
                                }
                                if (fri.equals("1")) {
                                    c5.setChecked(true);
                                }
                                if (sat.equals("1")) {
                                    c6.setChecked(true);
                                }

                                t1.setText(name);
                                e1.setText(monLec);
                                e2.setText(tuesLec);
                                e3.setText(wednesLec);
                                e4.setText(thursLec);
                                e5.setText(friLec);
                                e6.setText(saturLec);

                            } }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Display.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
