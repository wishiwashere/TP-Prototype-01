public class Icon extends ClickableElement{
  
  // Creating private variables to store the icon's link, title and show title
  // properties, so that they can only be accessed within this class
  private String iconTitle;
  private Boolean showIconTitle;
  
  /*-------------------------------------- Constructor() ------------------------------------------------*/
  
  // This constructor is used by icons in the CameraLiveView Screen, that want to accept the 
  // default width and height of an icon, but do not link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle){
    
    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, "");
  }
  
  // This constructor is used by icons such as the homeIcon, that want to accept the default 
  // width and height of an icon, and also link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle, String linkTo){
    
    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, linkTo);
  }   
  
  // Full Constructor. Both of the above constructors both pass their values to this constructor, as
  // well as other icon's in the app that want to pass arguments for all of the specified values
  public Icon(float x, float y, float w, float h, PImage img, String title, Boolean showTitle, String linkTo){
    
    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (Rectangle)
    super(x, y, w, h, img, title, linkTo);
    
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
  
  /*-------------------------------------- showIcon() ------------------------------------------------*/
  
  // Creating a public showIcon function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showIcon(){
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();
        
    // Checking if this icon's title should be displayed as a header on the screen
    if(showIconTitle)
    {
      // Calling the super class's (Rectangle) addText method, to add the title to
      // the icon. Passing in the String containing the title for the icon, the current
      // x and y positions of the icon itself, and the font size (which is relative
      // to the icon's current height
      this.addText(this.iconTitle, this.getX(), this.getY(), this.getWidth() * 0.20);
    }
  }
}