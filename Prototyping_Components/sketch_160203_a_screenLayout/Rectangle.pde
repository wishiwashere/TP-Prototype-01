protected class Rectangle{
  
  // Creating private variables to store the properties of each object.
  // These variables are private so that they can only be accessed within
  // this class i.e. must be set through the constructor of the object, or
  // retrieved using the relevant get methods of this class
  private float rectX;
  private float rectY;
  private float rectWidth;
  private float rectHeight;
  private color rectCol;
  private float rectRotation;

  // Creating protected constructors for the Rectangle class, so that they can
  // only be accessed by classes which extend from this class
  
  protected Rectangle(){
    // If no x, y, or rotation are passed in, defaulting these to half of the sketch's
    // width and height (i.e. so that the rectangle will appear centered). If no width
    // and height are passed in, defaulting these to the current width and height of
    // the sketch. If no rotation value is specified, defaulting this value to 0, and 
    // finally, if no color is specified, defaulting this to white
    this(width/2, height/2, width, height, #FFFFFF);
  }
  
  protected Rectangle(color col){
    // If no x, y, or rotation are passed in, defaulting these to half of the sketch's
    // width and height (i.e. so that the rectangle will appear centered). If no width
    // and height are passed in, defaulting these to the current width and height of
    // the sketch. Passing these default values, and the specified color value, to the main
    // constructor of the class
    this(width/2, height/2, width, height, col);
  }
  
  protected Rectangle(float w, float h, color col){
    // If no x, y, are passed in, defaulting these to half of the specified
    // w and h parametres (i.e. so that the rectangle will appear centered).
    // Passing these default values, and the specified width, height and 
    // color values, to the main constructor of the class
    this(w/2, h/2, w, h, col);
  }
  
  protected Rectangle(float x, float y, float w, float h, color col){
    // Storing the values that are passed into the constructor in the private
    // variables of this class, so that they can be accessed by other functions
    // within this class, but not from anywhere outside of this class. Defaulting
    // the rotation of the instance to 0 (as it can be changed later using the
    // setRotation() method
    rectX = x;
    rectY = y;
    rectWidth = w;
    rectHeight = h;
    rectCol = col;
    rectRotation = 0;
  }
  
  // Creating a method to redraw the object or "show" it on the screen (i.e so that only 
  // descendants of this class can access
  protected void show(){
    // Storing the current state of the matrix
    pushMatrix();
    
    // Translating the position of the matrix to the specified x and y of the object
    translate(rectX, rectY);
    
    // Rotating the matrix by the specified rotation value of the object (which has been
    // stored as a radian value)
    rotate(rectRotation);
    
    // Setting the fill colour of the object to the value specified
    fill(rectCol);
    
    // Setting the drawing mode of the rectangle to be centered. This way, if a rotation has
    // been applied to the rectangle, it will pivot around it's center point
    rectMode(CENTER);
    
    // Drawing the rectangle with x and y values based on half of the width and height of
    // the object, so that it appears centered on it's point of origin. The actual position
    // on the screen will depend on the matrix's translation, as this will control where 
    // the object is drawn
    rect(0, 0, rectWidth, rectHeight);
    
    // Restoring the matrix to it's previous state
    popMatrix();
  }
  
  // Get method that returns the instance's x position
  protected float getX(){
    return rectX;
  }
  
  // Get method that returns the instance's y position
  protected float getY(){
    return rectY;
  }
  
  // Get method that returns the instance's width
  protected float getWidth(){
    return rectWidth;
  }
  
  // Get method that returns the instance's height
  protected float getHeight(){
    return rectHeight;
  }
  
  // Get method that returns the instance's rotation
  protected float getRotation(){
    return rectRotation;
  }
  
  // Set method that sets the rotation of instance
  protected void setRotation(int r){
    rectRotation = radians(r);
  }
}