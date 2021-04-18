package com.example.segradaclient;

public class Score
{
    String Color;
    int Columns, Sets, Pairs, Holes;

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getColumns() {
        return Columns;
    }

    public void setColumns(int columns) {
        Columns = columns;
    }

    public int getSets() {
        return Sets;
    }

    public void setSets(int sets) {
        Sets = sets;
    }

    public int getPairs() {
        return Pairs;
    }

    public void setPairs(int pairs) {
        Pairs = pairs;
    }

    public int getHoles() {
        return Holes;
    }

    public void setHoles(int holes) {
        Holes = holes;
    }

    public Score(String color, int columns, int sets, int pairs, int holes) {
        Color = color;
        Columns = columns;
        Sets = sets;
        Pairs = pairs;
        Holes = holes;
    }

}
