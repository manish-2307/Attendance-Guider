package com.example.attendance_guider.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance_guider.activity.History;
import com.example.attendance_guider.activity.Home;
import com.example.attendance_guider.activity.Preview;
import com.example.attendance_guider.R;
import com.example.attendance_guider.dataclass.subject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class subjectadapter extends RecyclerView.Adapter<subjectadapter.subjectviewholder> {


    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    private Context mctx;
    private List<subject> subjectList;
    public int y;
     String email;
    public double criteria;
    DecimalFormat round = new DecimalFormat("0.00");


    public subjectadapter(Context mctx, List<subject> subjectList) {
        this.mctx = mctx;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public subjectviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.homelist,null);
        return new subjectviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  final subjectviewholder holder, final int position) {

        final subject x  = subjectList.get(position);
         email = Home.sent();



        final int  lec = Integer.parseInt(x.getLecture());
        final int tot = Integer.parseInt(x.getTotal());
        final int attend=Integer.parseInt(x.getAttended());
        criteria=Double.parseDouble(x.getCriteria());

        holder.lecture.setText(x.getLecture());
        holder.subject.setText(x.getName());
        holder.attend.setText(x.getAttended());
        holder.total.setText(x.getTotal());
        holder.attendance.setText(x.getAttendance());

holder.predict.setText(prediction(attend,tot));


        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                present y = new present(mctx);
                y.execute("present",x.getName(),email);

                holder.predict.setText(prediction(attend+1,tot+1));
                mark a = new mark(mctx);
                a.execute("history",email,x.getName(),date,"Present");

            }
        });

        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                absent z = new absent(mctx);
                z.execute("absent",x.getName(),email);

                holder.predict.setText(prediction(attend,tot+1));

                mark a = new mark(mctx);
                a.execute("history",email,x.getName(),date,"Absent");

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), History.class);
                intent.putExtra("subject",x.getName());
                mctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }


    class subjectviewholder extends RecyclerView.ViewHolder{

        public TextView subject,attend,total,attendance,predict;
        final  public  TextView lecture;
        public ProgressBar progressBar;
        public Button present,absent;


        public subjectviewholder(@NonNull View itemView) {
            super(itemView);
            predict=itemView.findViewById(R.id.predict);
            subject=itemView.findViewById(R.id.subject);
            lecture=itemView.findViewById(R.id.lecture);
            attend=itemView.findViewById(R.id.attend);
            total=itemView.findViewById(R.id.total);
            attendance=itemView.findViewById(R.id.attendance);
            progressBar=itemView.findViewById(R.id.circularProgressbar);
            present=itemView.findViewById(R.id.present);
            absent=itemView.findViewById(R.id.absent);
        }
    }

    public double  calculate (int x,int y)
    {

        double z;
        z=(double)x/(double)y;
        z=z*100;
        return z;
    }

    public String prediction(int attend , int tot) {

        double z = calculate(attend, tot);
        int count = 0;
        int count1 = 0;
        int m;
        int n;
        m = attend;
        n = tot;
        double k;
        k = z;
        if (z < criteria) {
            while (k < criteria) {
                count++;
                m++;
                n++;
                k = calculate(m, n);
            }
        } else {
            while (k >= criteria) {
                count1++;
                n++;
                k = calculate(m, n);
            }
        }
        if (count != 0) {
            return ("U Have To Attend " + count + "Classes");
        }
        if (count1 != 0 && count1 != 1) {
            return ("U May Leave " + (count1 - 1) + "Classes");
        }
        if (count1 == 1) {
            int count2 = 0;
            int d, e;
            double f;
            f = z;
            d = attend;
            e = tot;
            e++;

            do {
                count2++;
                d++;
                e++;
                f = calculate(d, e);
            } while (f < criteria);

            return ("U Have To Attend " + (count2) + "Classes");
        }
        return null;
    }
}
