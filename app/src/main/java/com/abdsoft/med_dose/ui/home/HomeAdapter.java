package com.abdsoft.med_dose.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdsoft.med_dose.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<HomeItem> homeItems;
    private Context context;

    HomeAdapter(List<HomeItem> homeItems, Context context) {
        this.homeItems = homeItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HomeItem homeItem = homeItems.get(position);

        holder.textMedicine.setText(homeItem.getMedicineName());
        holder.textDosageSummary.setText(homeItem.getDosageSummary());
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textMedicine, textDosageSummary;
        MaterialCardView cardView;
        CheckBox checkBox;

        ViewHolder(View itemView)
        {
            super(itemView);

            textDosageSummary = itemView.findViewById(R.id.dosage_text_view);
            textMedicine = itemView.findViewById(R.id.medicine_name_text_view);
            cardView = itemView.findViewById(R.id.card_view_medicine);
            checkBox = itemView.findViewById(R.id.medicine_checkbox);

        }
    }
}
