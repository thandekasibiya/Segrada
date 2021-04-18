package com.example.segradaclient;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

//A class where dices bounces
public class DiePool extends AppCompatActivity {

    DieCanvas dieCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dieCanvas =new DieCanvas(this);
        setContentView(dieCanvas);
        dieCanvas.setBackgroundColor(Color.WHITE);
    }





}
