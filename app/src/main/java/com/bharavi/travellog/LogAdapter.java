package com.bharavi.travellog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bharavi on 28-12-2017.
 */

public class LogAdapter  extends RecyclerView.Adapter<LogAdapter.MyViewHolder>{

        private ArrayList<LogModel> logModelArrayList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView date,time,description;

            public MyViewHolder(View view) {
                super(view);
                date=(TextView) view.findViewById(R.id.dateTextView);
                time = (TextView) view.findViewById(R.id.timeTextView);
                description = (TextView) view.findViewById(R.id.descriptionTextView);
                getAdapterPosition();
            }
        }


        public LogAdapter(ArrayList<LogModel> logModelArrayList1) {
            this.logModelArrayList = logModelArrayList1;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.one_log, parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            LogModel log = logModelArrayList.get(position);
            holder.date.setText(log.getDate());
            holder.time.setText(log.getTime());
            holder.description.setText(log.getDescription());
        }

        @Override
        public int getItemCount() {
            return logModelArrayList.size();
        }
}
