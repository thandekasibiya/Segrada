package com.example.segradaclient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.segradaclient.messages.Message;
import com.example.segradaclient.messages.MessageReceiver;
import com.example.segradaclient.messages.server.HandleSet;

import java.nio.charset.Charset;
import java.util.Random;

public class MainActivity extends AppCompatActivity {



    Button btnStart;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart= findViewById(R.id.startGame);

        final Intent intent = new Intent(this, Game.class);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });}}

