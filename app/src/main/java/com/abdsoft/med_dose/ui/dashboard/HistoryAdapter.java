package com.abdsoft.med_dose.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdsoft.med_dose.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private List<HistoryItem> historyItems;
    private Context context;

    public HistoryAdapter(List<HistoryItem> historyItems, Context context) {
        this.historyItems = historyItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryItem historyItem = historyItems.get(position);

        holder.textViewName.append(historyItem.getName());
        holder.textViewDate.append(historyItem.getDate());
        holder.textViewTimesPerDay.append(String.valueOf(historyItem.getTimesPerDay()));
        holder.textViewDosage.append(String.valueOf(historyItem.getTotalDosage()));
        holder.textViewTimings.append(historyItem.getTimings());
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewName, textViewDate, textViewTimesPerDay, textViewDosage, textViewTimings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.medicine_name_text_view_history);
            textViewDate = itemView.findViewById(R.id.medicine_date_text_view_history);
            textViewTimesPerDay = itemView.findViewById(R.id.medicine_times_per_day_text_view_history);
            textViewDosage = itemView.findViewById(R.id.total_dosage_text_view_history);
            textViewTimings = itemView.findViewById(R.id.medicine_timings_text_view_history);
        }
    }
}
