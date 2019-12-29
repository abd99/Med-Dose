package com.abdsoft.med_dose;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rm.com.clocks.ClockImageView;

public class AlarmActivity extends AppCompatActivity {

    TextView textViewMedicineName, textViewTime;
    Vibrator mVibrator;
    Button buttonDismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent intent = getIntent();

        ClockImageView clockImageView = findViewById(R.id.clock_alarm);
        Calendar mCurrentTime = Calendar.getInstance();
        clockImageView.animateToTime(mCurrentTime.get(Calendar.HOUR_OF_DAY), mCurrentTime.get(Calendar.MINUTE));


        textViewMedicineName = findViewById(R.id.text_view_medicine_name_alarm);
        textViewMedicineName.setText(intent.getStringExtra("medicineName"));

        textViewTime = findViewById(R.id.text_view_time_alarm);
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        textViewTime.setText(format.format(mCurrentTime.getTime()));


        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 1000};
        mVibrator.vibrate(pattern, 0);


        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();

        buttonDismiss = findViewById(R.id.button_dismiss);
        buttonDismiss.setOnClickListener(v -> {
            mVibrator.cancel();
            ringtone.stop();
            finish();
        });
    }

}
