package com.pigottlaura.www.wishiwashere;

import android.os.Bundle;

import processing.core.PApplet;


public class MainActivity extends PApplet {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void settings() {
        size(1920, 1080, OPENGL);
        fullScreen();
        println("Processing Settings Function Called");
    }

    @Override
    public void setup() {
        println("Processing Setup Funciton Called");
        background(0);
        strokeWeight(4);
        textSize(width / 8);
        textAlign(CENTER);
        text("Hello World", width / 2, height / 2);
    }

    @Override
    public void draw() {
        //println("Processing Draw Function Called at frame " + frameCount);
        stroke(237, 24, 223);

        if (mousePressed) {
            //println("Processing Mouse Pressed");
            line(pmouseX, pmouseY, mouseX, mouseY);
        }
    }

}
