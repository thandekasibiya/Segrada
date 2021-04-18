package com.example.segradaclient;

public class Player
{
    //Name of the player
    public String name;
    //Color assigned to the player String
    public String Colour;
    // Check the status of the player if the have played
    Boolean CheckStatus =false;
    //Position of the player in the list
    public int pos;

    public Player(String name, String Colour, Boolean CheckStatus, int pos) {
        this.name = name;
        this.Colour = Colour;
        this.CheckStatus = CheckStatus;
        this.pos=pos;
    }


    //Set the position of the player
    public void setPos(int pos) {
        this.pos = pos;
    }
    //return the position of the player
    public int getPos() {
        return pos;
    }
}
