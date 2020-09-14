package com.example.attendance_guider.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance_guider.R;

import com.example.attendance_guider.dataclass.marking;


import java.util.List;

public class historyadapter extends RecyclerView.Adapter<historyadapter.historyviewholder>  {


    private List<marking> markingList;
    private Context mctx;



    public historyadapter(Context mctx, List<marking> markingList) {
        this.mctx = mctx;
        this.markingList = markingList;
    }
    @NonNull
    @Override
    public historyviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.historylist,null);
        return new historyviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final historyviewholder holder, final int position) {
        final marking x  = markingList.get(position);


        holder.subject_name.setText(x.getSubject_name());
        holder.mark.setText( x.getMark());
        holder.date.setText(x.getDate());



    }



    @Override
    public int getItemCount() {
        return markingList.size();
    }







    class historyviewholder extends  RecyclerView.ViewHolder {

        public TextView date,mark,subject_name;


        public historyviewholder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            mark=itemView.findViewById(R.id.mark);
            subject_name=itemView.findViewById(R.id.subject);
        }
    }

}
