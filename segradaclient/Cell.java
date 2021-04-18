package com.example.segradaclient;

import android.graphics.drawable.Drawable;

//A cell class that contain a button with a die image, die number die color and its tag

public class Cell {
    public String color;
    public Drawable image;
    public int Row, Column;
    public String Cellname;
    public int Number;
    public boolean OccupiedCell;



    //The cell constructor
    public Cell(String color, Drawable image, int Row, int Column, String Cellname, int number, boolean OccupiedCell) {
        this.color = color;
        this.image = image;
        this.Row = Row;
        this.Column = Column;
        this.Cellname = Cellname;
        Number = number;
        this.OccupiedCell = OccupiedCell;
    }

    //The cell's attributes getters and setters

    public boolean isOccupiedCell()
    {
        return OccupiedCell;
    }

    public void setOccupiedCell(boolean occupiedCell)
    {
        OccupiedCell = occupiedCell;
    }
    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int getNumber()
    {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getRow()
    {
        return Row;
    }

    public void setRow(int row)
    {
        this.Row = row;
    }

    public int getColumn()
    {
        return Column;
    }

    public void setColumn(int column)
    {
        this.Column = column;
    }

    public String getCellname()
    {
        return Cellname;
    }

    public void setCellname(String cellname)
    {
        Cellname = cellname;
    }

    public Drawable getImage()
    {
        return image;
    }

    public void setImage(Drawable image)
    {
        this.image = image;
    }

    public boolean isEmpty() {
        return  true;
    }

    public Cell getDie() {

    }
}
