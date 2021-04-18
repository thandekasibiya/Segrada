package com.example.segradaclient;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
//An object die class that will be bouncing in the canvas
public class Dice implements Serializable
{

    public String color;
    public int number;
    public Drawable image;
    boolean isSelected =false;
    private ArrayList<Dice> ListOfDice =new ArrayList<>();
    private Paint paint;
    private RectF bounds;
    //the top conner coordinates
    private int leftTopX;
    private int leftTopY;
    private int radius;
    //horizontal and vertical speed
    private int speedX;
    private int speedY;
    //size of the dice in the canvas
    private int maxWidth;
    private int maxHeight;
// The dice different constructors


    /**
     * @param die
     */
    public Dice(Dice die)
    {
        this.number = die.number;
        this.color = die.color;
        this.image = die.image;
    }


    /**
     * @param color
     * @param number
     * @param image
     */
    public Dice(String color, int number, Drawable image) {
        this.color = color;
        this.number = number;
        this.image = image;
    }

    /**
     * @param color
     * @param image
     */
    public Dice(int color,Drawable image) {
        paint=new Paint();
        paint.setColor(color);
        setImage(image);
        bounds=new RectF();
    }

    public static String[] values() {

        return  new String[];
    }

    /**
     * @param x
     * @param y
     * @param radius
     * setting the die coordinates in the canvas
     */
    public void setCoordinates(int x,int y ,int radius){
        leftTopX =x;
        leftTopY =y;
        this.radius=radius;
        bounds.set(leftTopX, leftTopY, leftTopX +this.radius*2, leftTopY +this.radius*2);
    }



    /**
     * @param speedX
     * @param speedY
     * setting the initial speed of th die in the canvas
     */
    public void setSpeed(int speedX,int speedY){
        this.speedX=speedX;
        this.speedY=speedY;
    }

    /**
     * to change from the old speed to the new one
     */
    private void updateSpeed() {

        if ((leftTopX+2*radius> maxWidth)){
            speedX=speedX*-1;
        }else if ((leftTopX<0)){
            speedX=speedX*-1;
        }
        if ((leftTopY+2*radius> maxHeight)){
            speedY=speedY*-1;
        }else if ((leftTopY<0)){
            speedY=speedY*-1;
        }
        leftTopX = leftTopX +speedX;
        leftTopY = leftTopY +speedY;
        bounds.set(leftTopX, leftTopY, leftTopX +2*radius, leftTopY +2*radius);

    }


    /**
     * @param canvas
     * this draws the moving dice in the canvas
     */
    public void draw(Canvas canvas){
        canvas.drawRect(bounds,paint);
        updateSpeed();
    }


    /**
     * the method to move the dice in the canvas
     */
    public void moveDice()
    {
        for (Dice die: ListOfDice)
            die.updateSpeed();
    }

    /**
     * @param maxHeight
     * @param maxWidth
     * setting the die to its maximum height and width
     */

    public void setMaxSize(int maxHeight, int maxWidth){
        this.maxWidth =maxWidth;
        this.maxHeight=maxHeight;
    }

    //the dice's attributes getters and setters
    public String getColor()
    {
        return color;
    }

    public void setSelected()
    {
        isSelected=true;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Drawable getImage()
    {
        return image;
    }

    public void setImage(Drawable image)
    {
        this.image = image;
    }







}
