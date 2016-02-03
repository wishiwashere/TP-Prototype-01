protected class Rectangle{
  
  // Creating private variables to store the properties of each object.
  // These variables are private so that they can only be accessed within
  // this class i.e. must be set through the constructor of the object.
  private float rectX;
  private float rectY;
  private float rectWidth;
  private float rectHeight;
  private color rectCol;
  private float rectRotation;

  protected Rectangle(color col){
    // If no x, y, or rotation are passed in, defaulting these to 0. If no width
    // and height are passed in, defaulting these to the display size of the
    // device and then passing the rest of the values to the full constructor function
    this(width/2, height/2, width, height, 0, col);
  }
  
  protected Rectangle(float w, float h, color col){
    // If no x, y or rotation are passed in, defaulting these to 0, and then passing
    // the rest of the values to the full constructor function
    this(w/2, h/2, w, h, 0, col);
  }
  
  protected Rectangle(float x, float y, float w, float h, color col){
    // If no rotation is passed in, defaulting this to 0, and then passing
    // the rest of the values to the full constructor function
    this(x, y, w, h, 0, col);
  }
  
  protected Rectangle(float x, float y, float w, float h, int r, color col){
    // Storing the values that are passed into the constructor in the private
    // variables of this class, so that they can be accessed by other functions
    // within this class.
    rectX = x;
    rectY = y;
    rectWidth = w;
    rectHeight = h;
    rectRotation = radians(r);
    rectCol = col;
  }
  
  // Creating a method that only descendants of this class can access i.e. to
  // redraw the object or "show" it on the screen
  protected void show(){
    // Storing the current state of the matrix
    pushMatrix();
    
    // Translating the position of the matrix to the specified x and y of the object
    translate(rectX, rectY);
    
    // Rotating the matrix by the specified rotation value of the object (which has been
    // stored as a radian value
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
  
  protected float getX(){
    return rectX;
  }
  
  protected float getY(){
    return rectY;
  }
  
  protected float getWidth(){
    return rectWidth;
  }
  
  protected float getHeight(){
    return rectHeight;
  }
}