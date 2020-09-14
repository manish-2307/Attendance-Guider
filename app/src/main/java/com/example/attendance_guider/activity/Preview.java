package com.example.attendance_guider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendance_guider.R;
import com.example.attendance_guider.adapter.subadapter;
import com.example.attendance_guider.dataclass.subject_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Preview extends AppCompatActivity {

    private static final String URL_PRODUCTS = "https://attendanceguider.000webhostapp.com/preview.php";
    private List<subject_list> subjectList;
    RecyclerView recyclerView;
    static public String email;
    ProgressBar progressBar;
    RelativeLayout progressLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);


        progressBar=findViewById(R.id.progressBar);
        progressLayout=findViewById(R.id.progressLayout);

        progressLayout.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectList = new ArrayList<>();
        loadsubject();
        Intent intent=getIntent();
        email = intent.getStringExtra("email");

    }


    private void loadsubject() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            progressLayout.setVisibility(View.INVISIBLE);
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject subobject = array.getJSONObject(i);
                                String mail = subobject.getString("email");
                                if(mail.equals(email)) {

                                    subjectList.add(new subject_list(
                                            subobject.getString("subject_name"),
                                            subobject.getString("attended_lecture"),
                                            subobject.getString("total_lecture"),
                                            subobject.getString("email")));

                                }
                            }

                            subadapter adapter = new subadapter(Preview.this, subjectList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Preview.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void save(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    static public String send()
    {
        return email;
    }



}
