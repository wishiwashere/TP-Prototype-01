package com.pigottlaura.www.wishiwashere;

import android.content.Intent;

import processing.opengl.*;
import processing.core.*;

public class Sketch extends PApplet {
    public static float myNum1 = (float)(5.8);
    public static float myNum2;
    MyClass instanceOfMyClass;
    public static Sketch sketch;
    public static Sketch getSketch(){
        return sketch;
    }
    public static void setSketch(Sketch s){
        sketch = s;
    }

    private Boolean showingTwitterButton = false;


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

        println("Processing draw function called at frame " + frameCount);
        instanceOfMyClass.show();

        fill(0);
        textSize(30);
        textAlign(CENTER, CENTER);
        text("Hello World", width / 2, height / 2 - 100);

        if(MainActivity.twitterLoggedIn){
            println("In SKETCH - Twitter username = " + MainActivity.twitterUserUsername);
            println("In SKETCH - Twitter userid = " + MainActivity.twitterUserUserId);
            println("In SKETCH - Twitter access token = " + MainActivity.twitterUserAccessToken);
            println("In SKETCH - Twitter secret token = " + MainActivity.twitterUserSecretToken);
        }
    }

    public void setMyNum2(float _myNum2){
        myNum2 = _myNum2;
    }

    @Override
    public void onStop(){
        super.onStop();
        println("Processing Sketch Stopped");
    }

    @Override
    public void onResume() {
        super.onResume();
        println("Processing Sketch Resumed");
    }

    public float getMyNum2() {
        return myNum2;
    }
}