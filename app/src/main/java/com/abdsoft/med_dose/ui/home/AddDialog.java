package com.abdsoft.med_dose.ui.home;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.abdsoft.med_dose.R;
import com.google.android.material.appbar.MaterialToolbar;

public class AddDialog extends DialogFragment {
    public static final String TAG = "example_dialog";

    private MaterialToolbar toolbar;

   /* public static AddDialog display(FragmentManager fragmentManager) {
        AddDialog exampleDialog = new AddDialog();
        exampleDialog.show(fragmentManager, TAG);
        return exampleDialog;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_medicine_dialog, container, false);

        toolbar = view.findViewById(R.id.toolbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(AddDialog.this.getContext(), "Close Pressed", Toast.LENGTH_LONG).show();
            AddDialog.this.dismiss();
        });
        toolbar.setTitle("Some Title");
        toolbar.inflateMenu(R.menu.add_dialog);
        toolbar.setOnMenuItemClickListener(item -> {
            AddDialog.this.dismiss();
            return true;
        });
    }
}
