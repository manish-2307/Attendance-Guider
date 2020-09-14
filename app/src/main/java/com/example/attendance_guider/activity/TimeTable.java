package com.example.attendance_guider.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class TimeTable extends AsyncTask<String, Void, String> {


    AlertDialog alert;

    Context context;

    String add_url = "https://attendanceguider.000webhostapp.com/timetable.php";

    TimeTable(Context ctx)
    {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {


        String type = params[0];
        String email = params[1];
        String subname = params[2];
        String monday = params[3];
        String tuesday = params[4];
        String wednesday = params[5];
        String thursday = params[6];
        String friday = params[7];
        String saturday = params[8];
        String mon_lec = params[9];
        String tues_lec = params[10];
        String wednes_lec = params[11];
        String thurs_lec = params[12];
        String fri_lec = params[13];
        String satur_lec = params[14];



        if(type.equals("timetable"))
        {
            try {


                URL url = new URL(add_url);
                HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("Email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("Subname","UTF-8")+"="+URLEncoder.encode(subname,"UTF-8")+"&"
                        +URLEncoder.encode("Monday","UTF-8")+"="+URLEncoder.encode(monday,"UTF-8")+"&"
                        +URLEncoder.encode("Tuesday","UTF-8")+"="+URLEncoder.encode(tuesday,"UTF-8")+"&"
                        +URLEncoder.encode("Wednesday","UTF-8")+"="+URLEncoder.encode(wednesday,"UTF-8")+"&"
                        +URLEncoder.encode("Thursday","UTF-8")+"="+URLEncoder.encode(thursday,"UTF-8")+"&"
                        +URLEncoder.encode("Friday","UTF-8")+"="+URLEncoder.encode(friday,"UTF-8")+"&"
                        +URLEncoder.encode("Saturday","UTF-8")+"="+URLEncoder.encode(saturday,"UTF-8")+"&"
                        +URLEncoder.encode("Mon_lec","UTF-8")+"="+URLEncoder.encode(mon_lec,"UTF-8")+"&"
                        +URLEncoder.encode("Tues_lec","UTF-8")+"="+URLEncoder.encode(tues_lec,"UTF-8")+"&"
                        +URLEncoder.encode("Wednes_lec","UTF-8")+"="+URLEncoder.encode(wednes_lec,"UTF-8")+"&"
                        +URLEncoder.encode("Thurs_lec","UTF-8")+"="+URLEncoder.encode(thurs_lec,"UTF-8")+"&"
                        +URLEncoder.encode("Fri_lec","UTF-8")+"="+URLEncoder.encode(fri_lec,"UTF-8")+"&"
                        +URLEncoder.encode("Satur_lec","UTF-8")+"="+URLEncoder.encode(satur_lec,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line="";
                while((line = bufferedReader.readLine())!=null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return null;
    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context," TimeTable Added!!!!",Toast.LENGTH_SHORT).show();


    }
}