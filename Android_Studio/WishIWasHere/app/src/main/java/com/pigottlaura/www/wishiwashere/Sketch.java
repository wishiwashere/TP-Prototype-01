package com.pigottlaura.www.wishiwashere;

import processing.opengl.*;
import processing.core.*;

public class Sketch extends PApplet {

    MyClass instanceOfMyClass;

    @Override
    public void settings() {
        size(1000, 1000, P2D);
        fullScreen();
        println("Processing settings function called");
    }

    @Override
    public void setup() {
        background(255);
        println("Processing setup function called");
        instanceOfMyClass = new MyClass(this, "Hello from Sketch Class");

    }

    @Override
    public void draw() {
        //Finally draw from here
        println("Processing draw function called at frame " + frameCount);
        instanceOfMyClass.show();

        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width/2, height/2 - 100);
    }
}