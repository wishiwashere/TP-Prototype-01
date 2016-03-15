package com.pigottlaura.www.wishiwashere;

import processing.opengl.*;
import processing.core.*;

public class Sketch extends PApplet {
    float myNum1 = (float)(5.8);
    float myNum2;
    MyClass instanceOfMyClass;
    public static Sketch sketch;
    public static Sketch getSketch(){
        return sketch;
    }
    public static void setSketch(Sketch s){
        sketch = s;
    }


    @Override
    public void settings() {
        size(1000, 1000, P2D);
        fullScreen();
        println("Processing settings function called");

    }

    @Override
    public void setup() {
        Sketch.setSketch(this);
        myNum2 = (float)(5.2);
        background(255);
        println("Processing setup function called");
        instanceOfMyClass = new MyClass("Hello from Sketch Class :)");

    }

    @Override
    public void draw() {
        println("In Sketch - variable myNum2 = " + myNum2);
        println("In Sketch - using getMyNum2 = " + getMyNum2());
        //Finally draw from here
        println("Processing draw function called at frame " + frameCount);
        instanceOfMyClass.show();

        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width/2, height/2 - 100);
    }

    public void setMyNum2(float _myNum2){
        myNum2 = _myNum2;
    }

    public float getMyNum2() {
        return myNum2;
    }
}