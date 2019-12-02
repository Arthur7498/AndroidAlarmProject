package edu.ilstu.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityAlarm extends AppCompatActivity {
    boolean snoozeBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        final Button snooze = findViewById(R.id.snoozeBttn);
        final Button cancel = findViewById(R.id.cancelAlarmBttn);
        final Intent mainActivity = new Intent(this, MainActivity.class);
        final TextView sentMessage = findViewById(R.id.sentMessage);
        Intent intent = getIntent();



        sentMessage.setText(intent.getStringExtra("MESSAGE"));

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snoozeBool = true;
                mainActivity.putExtra("SNOOZE", snoozeBool);
                startActivity(mainActivity);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snoozeBool = false;
                mainActivity.putExtra("SNOOZE", snoozeBool);
                startActivity(mainActivity);

            }
        });
    }
}
