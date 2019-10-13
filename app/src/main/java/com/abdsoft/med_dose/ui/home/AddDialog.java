package com.abdsoft.med_dose.ui.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.abdsoft.med_dose.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddDialog extends DialogFragment {
    public static final String TAG = "example_dialog";

    private MaterialToolbar toolbar;
    private MaterialTextView textViewDate, textViewTime;
    private ChipGroup chipGroupScheduleTimes;


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
        View root = inflater.inflate(R.layout.add_medicine_dialog, container, false);

        toolbar = root.findViewById(R.id.toolbar);
        textViewDate = root.findViewById(R.id.text_view_select_date);
        textViewTime = root.findViewById(R.id.text_view_select_time);
        chipGroupScheduleTimes = root.findViewById(R.id.chip_group_times);


        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(AddDialog.this.getContext(), "Close Pressed", Toast.LENGTH_LONG).show();
            AddDialog.this.dismiss();
        });
        toolbar.setTitle("Add Medicine");
        toolbar.inflateMenu(R.menu.add_dialog);
        toolbar.setOnMenuItemClickListener(item -> {
            AddDialog.this.dismiss();
            return true;
        });

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        textViewDate.setText(format.format(calendar.getTime()));


        textViewDate.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view2, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        textViewDate.setText(format1.format(calendar.getTime()));


                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        format = new SimpleDateFormat("h:mm a");
        textViewTime.setText(format.format(mCurrentTime.getTime()));

        textViewTime.setOnClickListener(view1 -> {
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), (timePicker, selectedHour, selectedMinute) -> {
                calendar.set(Calendar.HOUR, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                SimpleDateFormat format12 = new SimpleDateFormat("h:mm a");
                textViewTime.setText(format12.format(calendar.getTime()));
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.show();
        });

        initChipGroup(chipGroupScheduleTimes);
    }

    private void initChipGroup(ChipGroup chipGroup) {

        String[] textArray = getResources().getStringArray(R.array.schedule_arrays);
        for (String text : textArray) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.cat_chip_group_item_choice, chipGroup, false);
            chip.setText(text);
            chipGroup.addView(chip);
            chipGroup.setSingleSelection(true);
            chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
                Toast.makeText(getContext(), String.valueOf(group.getCheckedChipId()), Toast.LENGTH_LONG).show();
            });
        }
    }
}
