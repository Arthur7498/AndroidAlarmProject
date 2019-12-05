package edu.ilstu.alarm;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView mDisplayDate;
    private TextView timeTxt;
    private Button addAlarm;
    private Button recurs;
    private Button toGps;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText optMssg;
    double latitude1;
    double longitude1;
    double latitude2;
    double longitude2;
    int counter = 0;
    int time;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        time = intent.getIntExtra("TIME", 20000);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10);
            }
        }


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {



            @Override
            public void onLocationChanged(Location location) {
                if (counter == 0) {
                    longitude1 = location.getLongitude();
                    latitude1 = location.getLatitude();
                    counter++;
                }
                else {
                    longitude2 = location.getLongitude();
                    latitude2 = location.getLatitude();
                    if (longitude1 == longitude2 && latitude1 == latitude2) {
                        openGpsDialog();
                    }


                }


                System.out.println(longitude1);
                System.out.println(longitude2);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProviderDisabled(String provider) {
                Intent turnOnGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(turnOnGPS);
            }
        };

        locationManager.requestLocationUpdates("gps", time,0, locationListener);

//--------------------------------------------------------------------------------------------------------------




        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        timeTxt = findViewById(R.id.tvTime);
        addAlarm = findViewById(R.id.add);
        optMssg = findViewById(R.id.optMessage);
        toGps = findViewById(R.id.goToGps);
        recurs = findViewById(R.id.recursAlarm);

        recurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


        toGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGps();
            }
        });



        //put your logic for alarms in here
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date;
                String time;
                String message;
                date = mDisplayDate.getText().toString();
                time = timeTxt.getText().toString();
                message = optMssg.getText().toString();
                makeToast();
                openAlarmDialog(message);
               // openGpsDialog();


            }
        });



        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("MainActivity", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };




        timeTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTxt.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

    }


    public void goToGps() {
        Intent mainActivity;
        mainActivity = new Intent(this, GpsAlarmActivity.class);
        startActivity(mainActivity);

    }

    public void makeToast() {
        Toast. makeText(getApplicationContext(),"Alarm Added",Toast. LENGTH_SHORT).show();

    }

    public void openGpsDialog() {
        GPSDialog textDialog = new GPSDialog();

        textDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void openAlarmDialog(String message) {
        AlarmDialog textDialog = new AlarmDialog(message);

        textDialog.show(getSupportFragmentManager(), "example dialog");
    }




}

