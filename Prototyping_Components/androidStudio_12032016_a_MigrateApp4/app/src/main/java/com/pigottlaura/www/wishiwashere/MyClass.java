package com.pigottlaura.www.wishiwashere;

import processing.core.PApplet;

public class MyClass extends Sketch {
    private PApplet p;
    private String addText;

    public MyClass(PApplet _p, String myText){
        p = _p;
        addText = myText;
        setMyNum2(600);
    }

    public void show(){
        println("MyClass show method called - " + addText);
        println("In My Class - Variable MyNum1 = " + myNum1);
        println("In My Class - VariableMyNum2 = " + myNum2);
        println("In My Class - Using getMyNum2() = " + getMyNum2());
        p.fill(0);
        p.textSize(20);
        p.textAlign(p.CENTER, p.CENTER);
        p.text(addText, p.width/2, p.height/2 + 100);
    }
}