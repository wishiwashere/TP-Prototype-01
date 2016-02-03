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
    
    iconTitle = title;
  }
  public void showIcon(){
    this.show();
        
    if(iconTitle.length() > 1)
    {
      textAlign(CENTER, CENTER);
      textSize(this.getHeight() * 0.50);
      fill(#000000);
      text(iconTitle, this.getX(), this.getY());
    }
  }
}