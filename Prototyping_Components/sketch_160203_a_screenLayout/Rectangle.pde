protected class Rectangle{
  // Creating private variables to store the properties of each object.
  // These variables are private so that they can only be accessed within
  // this class i.e. must be set through the constructor of the object.
  private float rectWidth;
  private float rectHeight;
  private color rectCol;
  
  // Declaring the constructor of the class, which currently takes in three
  // properties (width, height and color).
  protected Rectangle(float w, float h, color col){
    // Storing the values that are passed into the constructor in the private
    // variables of this class, so that they can be accessed by other functions
    // within this class.
    rectWidth = w;
    rectHeight = h;
    rectCol = color(col);
  }
  
  // Creating a method that only descendants of this class can access i.e. to
  // redraw the object or "show" it on the screen
  protected void show(){
    fill(rectCol);
    rect(0, 0, rectWidth, rectHeight);
  }
}