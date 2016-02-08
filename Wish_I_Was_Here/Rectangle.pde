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
  private PImage rectImage;
  private PImage rectCameraImg;
  private int rectCameraImgScaleX = 1;
  private int rectCameraImgRotate = 0;

  /*-------------------------------------- Constructor() ------------------------------------------------*/
  
  // This constructor is used by screens that want to accept all the defaults
  protected Rectangle(){
    // If no x, y, or rotation are passed in, defaulting these to half of the sketch's
    // width and height (i.e. so that the rectangle will appear centered). If no width
    // and height are passed in, defaulting these to the current width and height of
    // the sketch. If no rotation value is specified, defaulting this value to 0, and 
    // finally, if no color is specified, defaulting this to white
    this(appWidth/2, appHeight/2, appWidth, appHeight, #FFFFFF, null);
  }
  
  // This constructor is used by screens that want to accept all the defaults, as well
  // as setting a background image
  protected Rectangle(PImage img){
    // If no x, y passed in, defaulting these to half of the sketch's width and height 
    // (i.e. so that the rectangle will appear centered). If no width and height are 
    // passed in, defaulting these to the current width and height of the sketch. If 
    // no color is specified, defaulting this to white. Passing these default values, 
    // along with the image that was passed in, to the main constructor of this class
    this(appWidth/2, appHeight/2, appWidth, appHeight, #FFFFFF, img);
  }
  
  // This constructor is used by screens that want to accept all the defaults, as well
  // as setting a background color
  protected Rectangle(color col){
    // If no x, y passed in, defaulting these to half of the sketch's width and height 
    // (i.e. so that the rectangle will appear centered). If no width and height are 
    // passed in, defaulting these to the current width and height of the sketch. If 
    // no image is specified, defaulting this null. Passing these default values, along 
    // with the color that was passed in, to the main constructor of this class
    this(appWidth/2, appHeight/2, appWidth, appHeight, col, null);
  }
  
  // This constructor is used by icons that do not link to anything, and that
  // want to have an image as their background
  protected Rectangle(float x, float y, float w, float h, PImage img){
    // If no color passed in, defaulting it to white and then passing this default value,
    // along with the x, y, width, height and image that was passed in, to the main 
    // constructor of this class
    this(x, y, w, h, #FFFFFF, img);
  }
  
  // This constructor is used by text input boxes (TEMPORARILY)
  protected Rectangle(float x, float y, float w, float h, color col){
    this(x, y, w, h, col, null);
  }
  
  // This is the main constructor of this class, to which all other constructors pass
  // their values to be stored as the instance's properties
  protected Rectangle(float x, float y, float w, float h, color col, PImage img){
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
    rectImage = img;
  }
  
  /*-------------------------------------- show() ------------------------------------------------*/
  
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
    
    // Checking if a background image has been passed in
    if(rectImage != null){    
      // Calling the addImage() method of the this class, to add the image to the screen,
      // passing in the image, along with the current x, y, width and height of the instance,
      // so that the image will appear the full size of the object
      this.addImage(rectImage, rectX, rectY, rectWidth, rectHeight, rectCameraImgScaleX, rectCameraImgRotate); 
    }
  }
  
  /*-------------------------------------- addText() ------------------------------------------------*/
  
  // Partial addText() method that adds text to the object using the default text size
  protected void addText(String text, float textX, float textY){
    // If no alignment specified, defaulting it to center on the x-axis. If no
    // text size specified, defaulting it to the defaultTextSize variable (as
    // defined in the main sketch. Passing these default values, along with the
    // specified text, x and y to the main addText() method
    this.addText(text, "CENTER", textX, textY, defaultTextSize);
  }
  
  // Partial addText() method that adds text to the object, using the default text size, and
  // changing the alignment on the x-axis to a specified alignment (as it will
  // default to CENTER otherwise
  protected void addText(String text, String align, float textX, float textY){
    // If no text size specified, defaulting it to the defaultTextSize variable
    // (as defined in the main sketch. Passing these default values, along with
    // the specified text, alignment, x and y to the main addText() method
    this.addText(text, align, textX, textY, defaultTextSize);
  }
  
  // Partial addText() method that adds text to the object, with a specific text size
  protected void addText(String text, float textX, float textY, float textSize){
    // If no alignment specified, defaulting it to center on the x-axis. Passing
    // this default value, along with the specified text, x, y and text size to
    // the main addText() method
    this.addText(text, "CENTER", textX, textY, textSize);
  }
  
  // Full addText() method, which takes in the values specified (some of which may have
  // been defaulted by the partial addText() methods above)
  protected void addText(String text, String align, float textX, float textY, float textSize){
    // Storing the current state of the matrix
    pushMatrix();
      
    // Translating the position of the matrix be equal to the x and y positions
    // passed into the function
    translate(textX, textY);
    
    // Rotating the matrix by the currnet rotation value of this object (which has been
    // stored as a radian value)
    rotate(this.getRotation());
    
    if(align.equals("LEFT")){
      // Setting the text align to Left on the x axis, and Center on the y so that
      // the text will be drawn from the center point of it's position on the left of
      // the page
      textAlign(LEFT, CENTER);
    }else{
      // Setting the text align to center (on both the x and the y) so that
      // the text will be drawn from the center point of it's position on
      // the page
      textAlign(CENTER, CENTER);
    }
    
    // Setting the text size to be responsive to the height of the app
    textSize(textSize);
    
    // Setting the fill color for the text to black
    fill(0);
    
    // Adding the text to the screen, setting the x and y positions to 0, 
    // as the actual position on the screen will depend on the matrix's translation,
    // as this will control where the text is drawn
    text(text, 0, 0);
    
    // Restoring the matrix to it's previous state
    popMatrix();
  }
  
  /*-------------------------------------- addImage() ------------------------------------------------*/
    
  // Partial addImage() method which is used by images that need to be displayed
  // centered in full resolution of the screen
  protected void addImage(PImage img, int scaleX, int rotate){
    // If no x, y, width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values to the full addImage() method
    this.addImage(img, appWidth/2, appHeight/2, img.width, img.height, scaleX, rotate);
  }
  
  // Partial addImage() method which is used by images that need to be displayed
  // at their default resolution
  protected void addImage(PImage img, float imgX, float imgY){
    // If no image width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values, along with the image, x and y to the full addImage() method
    this.addImage(img, imgX, imgY, img.width, img.height, 1, (int)this.getRotation());
  }
  
  // Partial addImage() method which is used by images that require a specific width and height
  // (Some of these values may have been defaulted by the partial addImage() method)
  protected void addImage(PImage img, float imgX, float imgY, float imgWidth, float imgHeight){
    // If no image width or height passed in, defaulting the width and height to be
    // equal to that of the image (i.e. it's default resolution). Passing these
    // default values, along with the image, x and y to the full addImage() method
    this.addImage(img, imgX, imgY, imgWidth, imgHeight, 1, (int)this.getRotation());
  }
  
  // Full addImage() method which is used by images that require a specific width and height
  // (Some of these values may have been defaulted by the partial addImage() method)
  protected void addImage(PImage img, float imgX, float imgY, float imgWidth, float imgHeight, int scaleX, int rotate){
    // Storing the current state of the matrix
    pushMatrix();
      
    // Translating the position of the matrix be equal to the x and y positions
    // passed into the function
    translate(imgX, imgY);
    
    // Flipping the image so that it better represents the camera it is using i.e.
    // on front facing cameras, the image will be flipped horizontally, so that things
    // don't appear in reverse.
    scale(scaleX, 1);
    
    // Rotating the matrix by the current rotation value of this screen (which has been
    // stored as a radian value)
    rotate(radians(rotate));
    
    // Setting the imageMode to center so that the image will be drawn from the center 
    // point of it's position on the page
    imageMode(CENTER);
    
    // Adding the image to the screen, setting the x and y positions to 0, 
    // as the actual position on the screen will depend on the matrix's translation,
    // as this will control where the text is drawn. Setting the width and height of the image
    // to be equal to the values passed into the function
    image(img, 0, 0, imgWidth, imgHeight);
    
    // Restoring the matrix to it's previous state
    popMatrix();
  }  
  
  /*-------------------------------------- get() and set() ------------------------------------------------*/
    
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
  
  protected void setScaleX(int sX){
    if(sX == 1 || sX == -1){
      rectCameraImgScaleX = sX;
    }
  }
  
  protected void showCameraImage(PImage bgImg, int scaleX, int rotate){
    rectWidth = appHeight;
    rectHeight = appWidth;
    rectImage = bgImg;
    rectCameraImgScaleX =  scaleX;
    rectCameraImgRotate = rotate;
  }
}