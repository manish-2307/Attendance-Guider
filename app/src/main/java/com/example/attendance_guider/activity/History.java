package com.example.attendance_guider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendance_guider.R;
import com.example.attendance_guider.adapter.historyadapter;
import com.example.attendance_guider.adapter.subadapter;
import com.example.attendance_guider.dataclass.marking;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {


    private static final String URL_PRODUCTS = "https://attendanceguider.000webhostapp.com/table.php";
    private List<marking> markingList;
    RecyclerView recyclerView;
    private String subjectname;
     public String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.historyList);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        markingList = new ArrayList<>();
        loadAttendance();
        email=Home.sent();
        Intent intent = getIntent();
        subjectname=intent.getStringExtra("subject");
    }



    private void loadAttendance() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject subobject = array.getJSONObject(i);
                                String mail = subobject.getString("email");
                                String name = subobject.getString("subject_name");
                                        if(mail.equals(email)&& subjectname.equals(name)) {


                                            markingList.add(new marking(
                                                    subobject.getString("email"),
                                                    subobject.getString("date"),
                                                    subobject.getString("subject_name"),
                                                    subobject.getString("mark")));
                                        }

                            }

                            historyadapter adapter = new historyadapter(History.this, markingList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}

