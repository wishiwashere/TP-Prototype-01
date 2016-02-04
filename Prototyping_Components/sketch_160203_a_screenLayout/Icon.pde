public class Icon extends Rectangle{
  
  // Creating private variables to store the icon's link, title and show title
  // properties, so that they can only be accessed within this class
  private String iconLinkTo;
  private String iconTitle;
  private Boolean showIconTitle;
  
  // Creating a public constructor for the Icon class, so that
  // instances of it can be created anywhere in the sketch
  public Icon(float x, float y, float w, float h, color col){
    
    // If no title or link specified, then defaulting these to empty strings.
    // Since there is no title, defaulting the showTitle argument to false, so it
    // won't be displayed on screen. Then passing all of these defaults, along with the
    // specified parametres, into the full constructor of this class
    this(x, y, w, h, col, "", false, "");
  }
  public Icon(float x, float y, float w, float h, color col, String title, Boolean showTitle){
    
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, w, h, col, title, showTitle, "");
  }
  public Icon(float x, float y, float w, float h, color col, String title, Boolean showTitle, String linkTo){
    
    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (Rectangle)
    super(x, y, w, h, col);
    
    // Initialising the iconLinkTo to be equal to the requested link
    // specified in the object's constructor. This link will be passed to the global
    // currentScreen variable if the link is clicked on, so that in the draw function,
    // we can determine which page to display
    iconLinkTo = linkTo;
    
    // Initialising the iconTitle to be equal to the requested title.
    // If no title was provided, then an empty string will have been passed
    // in, so we will temporarily store this and figure out later (in the showIcon
    // function) whether or not to display the text
    iconTitle = title;
    
    // Initialising the showIconTitle boolean to be equal to the specified value, which
    // will be used later to decide if this page's title will be displayed as a heading
    // on the page or not
    showIconTitle = showTitle;
  }
  
  // Creating a public showIcon function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  
  public void showIcon(){
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();
        
    // Checking if this icon's title should be displayed as a header on the screen
    if(showIconTitle)
    {
      // Storing the current state of the matrix
      pushMatrix();
      
      // Translating the position of the matrix to the current x and y of this object
      translate(this.getX(), this.getY());
      
      // Rotating the matrix by the currnet rotation value of this object (which has been
      // stored as a radian value)
      rotate(this.getRotation());
      
      // Setting the text align to center (on both the x and the y) so that
      // the text will be drawn from the center point of it's position on
      // the page
      textAlign(CENTER, CENTER);
      
      // Setting the text size to be 50% of the icon's height
      textSize(this.getHeight() * 0.50);
      
      // Setting the text color to black
      fill(#000000);

      // Adding the text to the screen, using the same x and y of the current 
      // icon
      text(iconTitle, 0, 0);
      
      // Restoring the matrix to it's previous state
      popMatrix();
    }
  }
  
  public void checkMouseOver(){ 
    // Checking if the mouse (or finger) is over this icon (called by the Screen
    // class if a mouse event has occurred while this icon's screen is being 
    // displayed. Since the icons are drawn from the center, a bit of additional 
    // calculations are required to find out if the mouse was over them (i.e. 
    // to see if the mouseX was over this icon, we first have to take half 
    // the width from the x from the x postion, to get the furthest left point, 
    // and then add half of the width to the x position of the icon, to get 
    // it's furthest right point. The process is simular for determining the mouseY)
    if((mouseX > (this.getX() - (this.getWidth()/2))) &&
       (mouseX < (this.getX() + (this.getWidth()/2))) &&
       (mouseY > (this.getY() - (this.getHeight()/2))) &&
       (mouseY < (this.getY() + (this.getHeight()/2)))){
         
         // Logging out the name of the icon that was clicked on
          println(this.iconTitle + " was clicked");
          
          // Checking if this icon has a link associated with it
          if(this.iconLinkTo.length() > 0)
          {
            // Setting the global currentScreen variable to be equal to the link
            // contained within the icon that was clicked on (so it can be used
            // in the main sketch to determine which page to display)
            currentScreen = this.iconLinkTo;
            
            // Logging out what page the app will now be taken to
            println("Going to page " + this.iconLinkTo);
          }
          
          // Setting mousePressed back to false, so that if the user still has their
          // mouse pressed after the screen changes, this will not be considered
          // a new click (as otherwise they could inadvertantly click on another button)
          mousePressed = false;
    }
  }
}