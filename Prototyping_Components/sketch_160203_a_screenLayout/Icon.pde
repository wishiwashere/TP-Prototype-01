public class Icon extends Rectangle{
  // Creating a private variable to store the icon's link, so that
  // it can only be accessed within this class
  private String iconLinkTo;
  
  // Creating a public constructor for the Icon class, so that
  // instances of it can be created anywhere in the sketch
  public Icon(float w, float h, color col, String linkTo){
    
    // Passing all of the parametres from the constructor
    // class into the super class (Rectangle)
    super(w, h, col);
    
    // Initialising the iconLinkTo to be equal to the requested link
    // specified in the object's constructor
    iconLinkTo = linkTo;
  }
}