package com.pigottlaura.www.wishiwashere;

import com.pigottlaura.www.wishiwashere.Sketch;
import processing.core.*;

public class MyClass extends Sketch {

    public String addText;

    public MyClass(String txt){
        addText = txt;
    }

    public void show(){
        println("MyClass show method called - " + addText);
    }
}