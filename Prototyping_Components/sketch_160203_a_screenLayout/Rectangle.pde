protected class Rectangle{
  // Creating private variables to store the properties of each object.
  // These variables are private so that they can only be accessed within
  // this class i.e. must be set through the constructor of the object.
  private float rectX;
  private float rectY;
  private float rectWidth;
  private float rectHeight;
  private color rectCol;

  protected Rectangle(color col){
    // If no x and y are passed in, defaulting these to 0, and if no width
    // and height are passed in, defaulting these to the display size of the
    // device and then passing the rest of the values to the full constructor function
    this(0, 0, displayWidth, displayHeight, col);
  }
  
  protected Rectangle(float w, float h, color col){
    // If no x and y are passed in, defaulting these to 0, and then passing
    // the rest of the values to the full constructor function
    this(0, 0, w, h, col);
  }
  
  protected Rectangle(float x, float y, float w, float h, color col){
    // Storing the values that are passed into the constructor in the private
    // variables of this class, so that they can be accessed by other functions
    // within this class.
    rectX = x;
    rectY = y;
    rectWidth = w;
    rectHeight = h;
    rectCol = col;
  }
  
  // Creating a method that only descendants of this class can access i.e. to
  // redraw the object or "show" it on the screen
  protected void show(){
    fill(rectCol);
    rect(rectX, rectY, rectWidth, rectHeight);
  }
}