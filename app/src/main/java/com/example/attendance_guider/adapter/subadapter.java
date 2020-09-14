package com.example.attendance_guider.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance_guider.activity.Display;
import com.example.attendance_guider.activity.Preview;
import com.example.attendance_guider.R;
import com.example.attendance_guider.dataclass.subject_list;

import java.util.List;


public class subadapter extends RecyclerView.Adapter<subadapter.subjectviewholder>  {



    private Context mctx;
    private List<subject_list> subjectList;
    public String mail;
    public String name;


    public subadapter(Context mctx, List<subject_list> subjectList) {
        this.mctx = mctx;
        this.subjectList = subjectList;
    }
    @NonNull
    @Override
    public subjectviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.subjectlist,null);
        return new subjectviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final subjectviewholder holder, final int position) {
        final subject_list x  = subjectList.get(position);


       mail = x.getEmail();
       name=x.getName();

                 holder.subjectname.setText(x.getName());
                 holder.attended.setText("Attended:" + x.getAttended());
                 holder.total.setText("Total:" + x.getTotal());


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), Display.class);
                intent.putExtra("subject",x.getName());
                mctx.startActivity(intent);
            }
        });






    }



    @Override
    public int getItemCount() {
        return subjectList.size();
    }







    class subjectviewholder extends  RecyclerView.ViewHolder {

         public TextView subjectname,attended,total;


        public subjectviewholder(@NonNull View itemView) {
            super(itemView);
            subjectname=itemView.findViewById(R.id.sub);
            attended=itemView.findViewById(R.id.attended);
            total=itemView.findViewById(R.id.total);



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mctx);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position =getAdapterPosition();
                            subjectList.remove(position);
                            notifyItemRemoved(position);
                            dialog.dismiss();
                            delete_sub x = new delete_sub(mctx);
                            x.execute("delete_sub",name,mail);
                            delete_timetable y = new delete_timetable(mctx);
                            y.execute("deletetimetable",mail,name);


                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                    return true;
                }
            });

        }





    }

}
