package com.example.attendance_guider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendance_guider.R;
import com.example.attendance_guider.adapter.subjectadapter;
import com.example.attendance_guider.dataclass.subject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity {



    DrawerLayout drawerLayout;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

   private ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    private static final String URL_PRODUCTS = "https://attendanceguider.000webhostapp.com/home.php";
    private static  List<subject> subjectList;
    RecyclerView recyclerView;
    public static String email;
    SwipeRefreshLayout swipeRefreshLayout;
    public static String criteria;
    public int id;


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        setContentView(R.layout.activity_home);


          swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
          email=sharedPreferences.getString("mails",email);


        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_LONG).show();


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);


        drawerLayout=findViewById(R.id.drawer);
        coordinatorLayout=findViewById(R.id.coordinate);
        frameLayout=findViewById(R.id.frame);
        navigationView=findViewById(R.id.navigation);
        toolbar=findViewById(R.id.toolbar);




        setToolbar();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Home.this,drawerLayout,R.string.open_drawer,R.string.close_drawer);
      drawerLayout.addDrawerListener(actionBarDrawerToggle);
      actionBarDrawerToggle.syncState();

      navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {

              switch (item.getItemId())
              {
                  case R.id.logout:
                      FirebaseAuth.getInstance().signOut();
                   startActivity(new Intent(getApplicationContext(), Login.class));
                      sharedPreferences.edit().putBoolean("isLoggedIn",false).apply();
                     finish();
                      return true;

                  case R.id.editsub:
                      Intent intent = new Intent(getApplicationContext(), AddSubject.class);
                      intent.putExtra("email",email);
                      startActivity(intent);
                      overridePendingTransition(0,0);
                      return true;

                  case R.id.edittimetable:
                      Intent intent1 = new Intent(getApplicationContext(), Preview.class);
                      intent1.putExtra("email",email);
                      startActivity(intent1);
                      overridePendingTransition(0,0);
                      return true;
              }
              return false;
          }
      });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id =item.getItemId();

                switch (item.getItemId())
                {
                    case R.id.home:
                        return true;

                    case R.id.bar:
                        Intent intent1 = new Intent(getApplicationContext(), Bar.class);
                        intent1.putExtra("mails",email);
                        startActivity(intent1);
                        return true;

                    case R.id.predict:
                        Intent intent = new Intent(getApplicationContext(), Predict.class);
                        intent.putExtra("mails",email);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });




        progressBar=(ProgressBar)findViewById(R.id.circularProgressbar);
        recyclerView =findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectList = new ArrayList<>();
        loadsubject();



        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView t2= (TextView) findViewById(R.id.t2);
        TextView cri=(TextView)findViewById(R.id.criteria);
        cri.setText(criteria);
        t2.setText(currentDate);

        final subjectadapter adapter = new subjectadapter(Home.this, subjectList);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                intent.putExtra("mails",email);
                startActivity(intent);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                finish();
            }
        });


    }



    private void loadsubject() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject subobject = array.getJSONObject(i);
                                String mail = subobject.getString("email");
                                 criteria = subobject.getString("criteria");

                            if(mail.equals(email))
                                {
                                subjectList.add(new subject(

                                        subobject.getString("subject_name"),
                                        subobject.getString("attended_lecture"),
                                        subobject.getString("total_lecture"),
                                        subobject.getString("email"),
                                        subobject.getString("attendance"),
                                        subobject.getString("lecture"),
                                        subobject.getString("criteria")));



                          }
                            }


                            final subjectadapter adapter = new subjectadapter(Home.this, subjectList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         {
            if (item.getItemId() == android.R.id.home) {
                drawerLayout.openDrawer(GravityCompat.START);
            }



        }
        return super.onOptionsItemSelected(item);
    }

   public void setToolbar()
   {
       setSupportActionBar(toolbar);
       getSupportActionBar().setTitle("Attendance_Guider");
       getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }

     static public String sent()
    {
        return email;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if(id==R.id.bar) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("mails", email);
            startActivity(intent);
        }else if(id == R.id.predict)
        {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("mails", email);
            startActivity(intent);
        }
        else
        {
            super.onBackPressed();
        }
    }
}



