package com.pigottlaura.www.wishiwashere;

import processing.core.PApplet;

public class Sketch extends PApplet {
    MyClass instanceOfMyClass;
    @Override
    public void settings() {
        //Call size/fullscreen from here
        size(1000, 1000, P2D);
        fullScreen();
        println("Processing settings function called");
    }

    @Override
    public void setup() {
        background(0);
        println("Processing setup function called");
        instanceOfMyClass = new MyClass("Hello from Sketch Class");
        instanceOfMyClass.show();
    }

    @Override
    public void draw() {
        //Finally draw from here
        println("Processing draw function called at frame " + frameCount);
    }
}