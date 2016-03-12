package com.pigottlaura.www.wishiwashere;

import processing.core.PApplet;

public class MyClass extends Sketch {
    PApplet p;

    public String addText;

    public MyClass(PApplet _p, String txt){
        p = _p;
        addText = txt;
    }

    public void show(){
        println("MyClass show method called - " + addText);

        p.fill(0);
        p.textSize(20);
        p.textAlign(p.CENTER, p.CENTER);
        p.text(addText, p.width/2, p.height/2 + 100);
    }
}