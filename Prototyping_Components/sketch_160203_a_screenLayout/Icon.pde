public class Icon extends Rectangle{
  
  // Creating a private variable to store the icon's link, so that
  // it can only be accessed within this class
  private String iconLinkTo;
  private String iconTitle;
  
  // Creating a public constructor for the Icon class, so that
  // instances of it can be created anywhere in the sketch
  public Icon(float x, float y, float w, float h, int r, color col, String linkTo){
    
    // Passing all of the parametres from the constructor
    // class into the full constructor, passing null for the title parametre
    this(x, y, w, h, r, col, linkTo, "");
  }
  public Icon(float x, float y, float w, float h, int r, color col, String linkTo, String title){
    
    // Passing all of the parametres from the constructor
    // class into the super class (Rectangle)
    super(x, y, w, h, r, col);
    
    // Initialising the iconLinkTo to be equal to the requested link
    // specified in the object's constructor
    iconLinkTo = linkTo;
    
    // Initialising the iconTitle to be equal to the requested title.
    // If no title was provided, then an empty string will have been passed
    // in, so we will temporarily store this and figure out later (in the showIcon
    // function) whether or not to display the text
    iconTitle = title;
  }
  
  // Creating a public showIcon function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showIcon(){
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();
        
    // Checking if the icon has a title that needs to be displayed with it
    if(iconTitle.length() > 1)
    {
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
      text(iconTitle, this.getX(), this.getY());
    }
  }
  
  public void checkMouseOver(){ 
    // Checking if the mouse (or finger) is over this icon (usually only
    // called when a mouse event has occurred). Since the icons are drawn
    // from the center, a bit of additional calculations are required to 
    // find out if the mouse was over them i.e. to see if the mouseX was
    // over this icon, we first have to take half the width from the x from 
    // the x postion, to get the furthest left point, and then add half of
    // the width to the x position of the icon, to get it's furthest right
    // point. The process is simular for determining the mouseY
    if((mouseX > (this.getX() - (this.getWidth()/2))) &&
       (mouseX < (this.getX() + (this.getWidth()/2))) &&
       (mouseY > (this.getY() - (this.getHeight()/2))) &&
       (mouseY < (this.getY() + (this.getHeight()/2)))){
          println(this.iconTitle + " was clicked");
    }
  }
}