package com.example.administrator.guessmusic.model;

import android.widget.Button;

public class WordButton {

    private Button mBtn;
    private String text = "";
    private int index;
    private boolean visible;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Button getBtn() {
        return mBtn;
    }

    public void setBtn(Button mBtn) {
        this.mBtn = mBtn;
    }
}
