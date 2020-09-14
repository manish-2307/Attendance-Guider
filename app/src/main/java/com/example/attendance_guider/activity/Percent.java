package com.example.attendance_guider.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.attendance_guider.R;

public class Percent extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progressBar ;
    private  ProgressBar progress1;
    private SeekBar seekBar;
     int cri;
    String name, phone, email, criteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);

        //SharedPreferences.Editor editor=getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE).edit();
        //editor.putInt("criteria",cri);
        //editor.apply();


        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.circularProgressbar);
        progress1=(ProgressBar)findViewById(R.id.progressBar1);
        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                textView.setText("" + progress + "%");
                criteria = Integer.toString(progress);
                cri=progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    public void OnCLick(View v)
    {
        progress1.setVisibility(v.VISIBLE);
        User user = new User(this);
        user.execute("Add", name, email, phone, criteria);
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
