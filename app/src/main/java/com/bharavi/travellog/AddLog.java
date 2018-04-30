package com.bharavi.travellog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddLog extends AppCompatActivity implements View.OnClickListener {

    LogDB ldb = new LogDB(this, "logdb.db", null, 1);
    LogModel currentLog;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText descriptionEditText;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);
        datePicker=findViewById(R.id.pickDate);
        timePicker=findViewById(R.id.pickTime);
        descriptionEditText=findViewById(R.id.descriptionEditText);
        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        timePicker.setIs24HourView(true);
    }

    @Override
    public void onClick(View view) {

        String date = String.format("%02d", datePicker.getDayOfMonth()) + "/" + String.format("%02d", datePicker.getMonth() + 1) + "/" + String.format("%02d", datePicker.getYear());
        String time = String.format("%02d", timePicker.getHour()) + ":" + String.format("%02d", timePicker.getMinute());
        String description = descriptionEditText.getText().toString();
        if (description.length() > 0) {
            if (ldb.insertLog(date, time, description))
                Toast.makeText(getApplicationContext(), "Log Added!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Failed to add Log!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        else
            Toast.makeText(getApplicationContext(), "No Description given!", Toast.LENGTH_SHORT).show();
    }
}
