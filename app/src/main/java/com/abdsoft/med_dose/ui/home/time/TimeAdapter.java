package com.abdsoft.med_dose.ui.home.time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdsoft.med_dose.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>  {

    private List<TimeItem> timeItems;
    private Context context;

    public TimeAdapter(List<TimeItem> timeItems, Context context) {
        this.timeItems = timeItems;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_time_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAdapter.ViewHolder holder, int position) {

        TimeItem timeItem = timeItems.get(position);

        holder.textViewTime.setText(timeItem.getTime());
    }

    @Override
    public int getItemCount() {
        return timeItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView textViewTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.text_view_select_time);
        }
    }
}
