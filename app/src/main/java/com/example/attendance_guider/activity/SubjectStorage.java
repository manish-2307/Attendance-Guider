package com.example.attendance_guider.activity;

import android.content.Context;
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

public class SubjectStorage extends AsyncTask<String, Void, String> {



    Context context;

    String add_url = "https://attendanceguider.000webhostapp.com/sub.php";

    SubjectStorage(Context ctx)
    {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {


        String type = params[0];
        String subname = params[1];
        String total = params[2];
        String attended = params[3];
        String email=params[4];
        String attendance =params[5];


        if(type.equals("sub"))
        {
            try {


                URL url = new URL(add_url);
                HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("Sub_name","UTF-8")+"="+URLEncoder.encode(subname,"UTF-8")+"&"
                        +URLEncoder.encode("Total","UTF-8")+"="+URLEncoder.encode(total,"UTF-8")+"&"
                        +URLEncoder.encode("Attended","UTF-8")+"="+URLEncoder.encode(attended,"UTF-8")+"&"
                        +URLEncoder.encode("Email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("attendance","UTF-8")+"="+URLEncoder.encode(attendance,"UTF-8");

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
        Toast.makeText(context," Subject Added!!!!",Toast.LENGTH_SHORT).show();

    }
}
