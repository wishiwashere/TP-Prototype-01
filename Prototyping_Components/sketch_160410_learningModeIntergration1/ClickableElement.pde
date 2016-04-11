public class ClickableElement extends Rectangle {
  String elementTitle = "";

  /*-------------------------------------- Constructor() ------------------------------------------------*/

  // This constructor is used by icons
  public ClickableElement(float x, float y, float w, float h, PImage img, String title) {
    super(x, y, w, h, img);
    elementTitle = title;
  }

  // This constructor is used by textInputs
  public ClickableElement(float x, float y, float w, float h, color col, String title) {
    super(x, y, w, h, col);
    elementTitle = title;
  }
  
    // This constructor is used by learningMode
  public ClickableElement(float x, float y, float w, float h, String title) {
    super(x, y, w, h);
    elementTitle = title;
  }

  /*-------------------------------------- checkMouseOver() ------------------------------------------------*/

  protected Boolean checkMouseOver() { 
    Boolean clickedOn = false;
    // Checking if the mouse (or finger) is over this icon (called by the Screen
    // class if a mouse event has occurred while this icon's screen is being 
    // displayed. Since the icons are drawn from the center, a bit of additional 
    // calculations are required to find out if the mouse was over them (i.e. 
    // to see if the mouseX was over this icon, we first have to take half 
    // the width from the x from the x postion, to get the furthest left point, 
    // and then add half of the width to the x position of the icon, to get 
    // it's furthest right point. The process is simular for determining the mouseY)
    if ((mouseX > (this.getX() - (this.getWidth()/2))) &&
      (mouseX < (this.getX() + (this.getWidth()/2))) &&
      (mouseY > (this.getY() - (this.getHeight()/2))) &&
      (mouseY < (this.getY() + (this.getHeight()/2)))) {

      // Logging out the name of the element that was clicked on
      println(this.elementTitle + " was clicked");

      // Setting mousePressed back to false, so that if the user still has their
      // mouse pressed after the screen changes, this will not be considered
      // a new click (as otherwise they could inadvertantly click on another button)
      mousePressed = false;

      clickedOn = true;
    }
    return clickedOn;
  }
}