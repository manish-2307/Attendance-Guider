package com.example.attendance_guider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendance_guider.dataclass.AttendanceData;
import com.example.attendance_guider.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Bar extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private static final  String url="https://attendanceguider.000webhostapp.com/bar.php";
    private RequestQueue mqueue;

    String jsonmail,email;
    String attendance,subjectname;
    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsNames;

     ProgressBar progressBar;
     RelativeLayout progressLayout;

    static ArrayList<AttendanceData>  attendanceDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);



        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bar);

        progressBar=findViewById(R.id.progressBar);
        progressLayout=findViewById(R.id.progressLayout);

        progressLayout.setVisibility(View.VISIBLE);

        Intent intent=getIntent();
        email = intent.getStringExtra("mails");
        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_LONG).show();

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
                        return true;

                    case R.id.predict:
                        Intent intent1 = new Intent(getApplicationContext(), Predict.class);
                        intent1.putExtra("mails",email);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        mqueue= Volley.newRequestQueue(this);
        barChart=(BarChart)findViewById(R.id.barchart);

        barEntryArrayList= new ArrayList<>();
        labelsNames= new ArrayList<>();


        //fillAttendance();
         fillAttendance x = new fillAttendance();
         x.execute();

        for(int i=0;i<attendanceDataArrayList.size();i++)
        {

                          String subject = attendanceDataArrayList.get(i).getSubject_name();
                          float attendance = attendanceDataArrayList.get(i).getAttendance();
                          String mail =attendanceDataArrayList.get(i).getEmail();
                          if(mail.equals(email))
                          {
                          barEntryArrayList.add(new BarEntry(i, attendance));
                          labelsNames.add(subject);
                      }
        }
        attendanceDataArrayList.clear();

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Attendance");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Subject Name");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        XAxis Xaxis  = barChart.getXAxis();
        Xaxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        Xaxis.setPosition(XAxis.XAxisPosition.TOP);
        Xaxis.setDrawGridLines(false);
        Xaxis.setDrawAxisLine(false);
        Xaxis.setGranularity(1f);
        Xaxis.setLabelCount(labelsNames.size());
        Xaxis.setLabelRotationAngle(270);


        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisMinimum(0f);
        barChart.animateY(2000);
        barChart.invalidate();

        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setPinchZoom(false);
        barChart.getAxisRight().setEnabled(false);


    }

 class fillAttendance extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressLayout.setVisibility(View.INVISIBLE);
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject bar = array.getJSONObject(i);
                                    jsonmail=bar.getString("email");
                                    attendance = bar.getString("attendance");
                                    subjectname = bar.getString("subject_name");
                                    Float x = Float.parseFloat(attendance);
                                    attendanceDataArrayList.add(new AttendanceData(jsonmail,subjectname, x));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Bar.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            Volley.newRequestQueue(Bar.this).add(stringRequest);

            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
