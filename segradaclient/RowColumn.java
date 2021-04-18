package com.example.segradaclient;

import android.widget.ImageButton;

//this class represent the grids rows and column
public class RowColumn {

    ImageButton button;
    Cell cell;

    public RowColumn(ImageButton button, Cell cell) {
        this.button = button;
        this.cell = cell;
    }

    public ImageButton getButton() {
        return button;
    }

    public void setButton(ImageButton button) {
        this.button = button;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
