package com.abdsoft.med_dose.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdsoft.med_dose.HomeActivity;
import com.abdsoft.med_dose.R;
import com.abdsoft.med_dose.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View root;

    HomeActivity homeActivity;
    DatabaseHelper databaseHelper;

    List<HistoryItem> historyItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        recyclerView = root.findViewById(R.id.recycler_view_medicine_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        historyItems = new ArrayList<>();
        loadHistory();

        return root;
    }

    public void loadHistory() {
        databaseHelper = new DatabaseHelper(getContext());
        historyItems = databaseHelper.getMedicineHistory();

        adapter = new HistoryAdapter(historyItems, getActivity());
        recyclerView.setAdapter(adapter);
    }
}