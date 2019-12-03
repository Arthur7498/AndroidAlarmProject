package edu.ilstu.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GpsAlarmActivity extends AppCompatActivity {
    int minutes = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_alarm);

        final Button addButton = findViewById(R.id.addGpsAlarm);
        final EditText gpsTxt = findViewById(R.id.gpsTxt);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minutes = Integer.parseInt(gpsTxt.getText().toString());

            }
        });
    }
}
