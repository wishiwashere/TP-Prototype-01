package com.pigottlaura.www.twitteraccess10;

import android.os.Bundle;

import processing.core.PApplet;


public class MainActivity extends PApplet {
    private static String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // the following line must be commented or removed:
        //setContentView(R.layout.activity_main);
    }

    /////////////////////////////////////////////////////////////////////////////
    // Processing Sketch

    @Override
    public void settings() {
        size(1920, 1080, OPENGL);
        fullScreen();
        println("settings()");
    }

    @Override
    public void setup() {
        println("setup()");
        background(0);
        strokeWeight(8.0f);
    }

    @Override
    public void draw() {
        println("draw()");
        stroke(128);

        if (mousePressed) {
            println("mousePressed");
            line(mouseX, mouseY, pmouseX, pmouseY);
        }
    }

}
