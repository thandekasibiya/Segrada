package com.example.segradaclient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

public class DieCanvas extends View {

    int maxWidth;
    int maxHeight;

    private Dice die;
    public ArrayList<Dice> dieList=new ArrayList<>();
    private Handler handler;
    public DieCanvas(Context context) {
        super(context);


        die=new Dice(Color.RED,getResources().getDrawable(R.drawable.red1));
        die.setCoordinates(0,0,90);
        die.setSpeed(5,5);
        dieList.add(die);


        die=new Dice(Color.BLUE,getResources().getDrawable(R.drawable.blue1));
        die.setCoordinates(0,0,90);
        die.setSpeed(4,4);
        dieList.add(die);


        die=new Dice(Color.YELLOW,getResources().getDrawable(R.drawable.yellow1));
        die.setCoordinates(0,0,90);
        die.setSpeed(3,3);
        dieList.add(die);


        die=new Dice(Color.GREEN,getResources().getDrawable(R.drawable.green1));
        die.setCoordinates(0,0,90);
        die.setSpeed(2,2);
        dieList.add(die);


        die=new Dice(Color.BLUE,getResources().getDrawable(R.drawable.blue1));
        die.setCoordinates(0,0,90);
        die.setSpeed(1,1);
        dieList.add(die);

        die=new Dice(Color.RED,getResources().getDrawable(R.drawable.red1));
        die.setCoordinates(0,0,90);
        die.setSpeed(1,5);
        dieList.add(die);

        die=new Dice(Color.GREEN,getResources().getDrawable(R.drawable.green1));
        die.setCoordinates(0,0,90);
        die.setSpeed(6,3);
        dieList.add(die);

        die=new Dice(Color.YELLOW,getResources().getDrawable(R.drawable.yellow1));
        die.setCoordinates(0,0,90);
        die.setSpeed(4,2);
        dieList.add(die);

        die=new Dice(Color.RED,getResources().getDrawable(R.drawable.red1));
        die.setCoordinates(0,0,90);
        die.setSpeed(2,5);
        dieList.add(die);


        //handles the communication between the UI and TimerThread
        handler=new Handler();
        Runnable tick= new Runnable() {
            @Override
            public void run() {
                die.moveDice();
                DieCanvas.this.invalidate();
            }
        };
        TimerThread timerThread =new TimerThread(10,tick,handler);
        timerThread.start();
    }


    /**
     * @param canvas
     * draws the dice in the canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator iterator =dieList.iterator();
        while(iterator.hasNext()){
            Dice die =(Dice) iterator.next();
            die.draw(canvas);
        }
        invalidate();
    }

    /**
     * @param width
     * @param height
     * @param Previouswidth
     * @param PreviousHeight
     */
    @Override
    protected void onSizeChanged(int width, int height, int Previouswidth, int PreviousHeight) {
        super.onSizeChanged(width, height, Previouswidth, PreviousHeight);
        maxWidth =width;
        maxHeight=height;

        Iterator iterator=dieList.iterator();
        while (iterator.hasNext()){
            Dice die= (Dice) iterator.next();
            die.setMaxSize(maxWidth,maxHeight);
        }
    }
}
