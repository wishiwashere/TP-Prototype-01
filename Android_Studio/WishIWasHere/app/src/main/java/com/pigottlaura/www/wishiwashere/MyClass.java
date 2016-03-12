package com.pigottlaura.www.wishiwashere;

public class MyClass extends Sketch {

    public String addText;

    public MyClass(String txt){
        addText = txt;
    }

    public void show(){
        println("MyClass show method called - " + addText);
    }
}