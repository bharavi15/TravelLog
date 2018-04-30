package com.bharavi.travellog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addLogButton,saveButton;
    RecyclerView recyclerView;
    LogAdapter logAdapter;
    ArrayList<LogModel> logs;
    LogDB ldb = new LogDB(this, "logdb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addLogButton=findViewById(R.id.floatingActionButton);
        saveButton=findViewById(R.id.saveFloatingActionButton);
        recyclerView=findViewById(R.id.myRecyclerView);

        logAdapter=new LogAdapter(logs);
        logs=ldb.getAllLogs();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(logAdapter);


        addLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AddLog.class);
                startActivity(i);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        File file = new File(Environment.getExternalStorageDirectory(), "trlog.txt");
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        String data;

                        for (LogModel log : logs) {
                            data = log.getDate() + " " + log.getTime() + " " + log.getDescription() + "\n";
                            fileOutputStream.write(data.getBytes());
                        }
                        fileOutputStream.close();
                        Toast.makeText(getApplicationContext(), "File Saved!", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        Log.e("MainActivity", e.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("MainActivity", e.toString());
                    }
                }
                else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    else
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        logs=ldb.getAllLogs();
        recyclerView.setAdapter(new LogAdapter(logs));
        logAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            if(grantResults.length==1&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getApplicationContext(),"Write Permission Granted!",Toast.LENGTH_SHORT).show();
        }
    }
}
