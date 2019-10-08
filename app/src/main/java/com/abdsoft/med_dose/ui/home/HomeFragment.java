package com.abdsoft.med_dose.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdsoft.med_dose.HomeActivity;
import com.abdsoft.med_dose.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View root;

    private String[] medicineNames, dosageNames;

    HomeActivity homeActivity;


    private List<HomeItem> homeItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        recyclerView = root.findViewById(R.id.recycler_view_medicine);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeItems = new ArrayList<>();

        medicineNames = new String[]{"Crocin", "Panadol", "Cipla"};
        dosageNames = new String[]{"Twice - Day and Night", "Once - Night", "Once - Morning"};

        for (int iTmp = 0; iTmp < medicineNames.length; iTmp++)
        {
            HomeItem homeItem = new HomeItem(medicineNames[iTmp], dosageNames[iTmp]);
            homeItems.add(homeItem);
        }

        adapter = new HomeAdapter(homeItems, getActivity());
        recyclerView.setAdapter(adapter);

        ExtendedFloatingActionButton fabAddMedicine = root.findViewById(R.id.fab_add_medicine);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Showing Add Dialog", Toast.LENGTH_SHORT).show();
                AddDialog addMedicineDialog = new AddDialog();
                addMedicineDialog.show(getFragmentManager(), "Add_Dialog");
            }
        });

        return root;
    }
}