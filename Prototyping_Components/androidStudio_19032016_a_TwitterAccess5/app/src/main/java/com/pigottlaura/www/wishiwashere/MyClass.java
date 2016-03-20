package com.pigottlaura.www.wishiwashere;


public class MyClass {
    private Sketch sketch;
    private String addText;

    public MyClass(String myText){
        sketch = Sketch.getSketch();
        addText = myText;
        sketch.myNum2 = 400;
    }

    public void show(){
        sketch.println("MyClass show method called - " + addText);
        sketch.println("In My Class - Variable MyNum1 = " + sketch.myNum1);
        sketch.println("In My Class - VariableMyNum2 = " + sketch.myNum2);
        sketch.println("In My Class - Using getMyNum2() = " + sketch.getMyNum2());
        sketch.fill(0);
        sketch.textSize(20);
        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        sketch.text(addText, sketch.width/2, sketch.height/2 + 100);
    }
}