public class Icon extends ClickableElement {

  // Creating private variables to store the icon's link, title and show title
  // properties, so that they can only be accessed within this class
  private String iconLinkTo;
  private String iconTitle;
  private Boolean showIconTitle;
  private String iconTitlePosition;

  /*-------------------------------------- Constructor() ------------------------------------------------*/

  // This constructor is used by icons in the CameraLiveView Screen, that want to accept the 
  // default width and height of an icon, but do not link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle) {

    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, "Middle", "");
  }

  // This constructor is used by icons such as the homeIcon, that want to accept the default 
  // width and height of an icon, and also link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle, String linkTo) {

    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, "Middle", linkTo);
  }  

  // This constructor is used by icons such as the homeIcon, that want to accept the default 
  // width and height of an icon, and also link to another page
  public Icon(float x, float y, PImage img, String title, Boolean showTitle, String titlePosition, String linkTo) {

    // If no width or height specified, defaulting these to 15% of the screen width.
    // If no link is specified, then defaulting this to an empty string.
    // Then passing this default, along with the specified parametres, into the 
    // full constructor of this class
    this(x, y, smallIconSize, smallIconSize, img, title, showTitle, titlePosition, linkTo);
  }   

  // Full Constructor. Both of the above constructors both pass their values to this constructor, as
  // well as other icon's in the app that want to pass arguments for all of the specified values
  public Icon(float x, float y, float w, float h, PImage img, String title, Boolean showTitle, String titlePosition, String linkTo) {

    // Passing the relevant parametres from the constructor into the constructor 
    // of the super class (Rectangle)
    super(x, y, w, h, img, title);

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

    iconTitlePosition = titlePosition;
  }

  /*-------------------------------------- showIcon() ------------------------------------------------*/

  // Creating a public showIcon function, which can be called anywhere in the code
  // to display the icon, and add any text that has been specified
  public void showIcon() {
    // Calling the show() method (which was inherited from the Rectangle class)
    // so that this icon will be displayed on screen
    this.show();

    // Checking if this icon's title should be displayed as a header on the screen
    if (showIconTitle)
    {
      // Calling the super class's (Rectangle) addText method, to add the title to
      // the icon. Passing in the String containing the title for the icon, the current
      // x and y positions of the icon itself, and the font size (which is relative
      // to the icon's current height
      if (iconTitlePosition == "Middle") {
        this.addText(this.iconTitle, this.getX(), this.getY(), this.getWidth() * 0.20);
      } else if (iconTitlePosition == "Below") {
        this.addText(this.iconTitle, this.getX(), this.getY() + (this.getHeight()*0.6), this.getWidth() * 0.20);
      }
    }
    if (mousePressed) {
      if (this.checkMouseOver()) {
        // Checking if this icon has a link associated with it
        if (this.iconLinkTo.length() > 0)
        {
          // Checking if the iconTitle contains "http" i.e. if it is an external link
          if (this.iconLinkTo.indexOf("http") > -1)
          {
            // This is an EXTERNAL link
            // Passing the icon's link into the link() method, so that it can be treated as 
            // an external link i.e. to a website
            link(this.iconLinkTo);

            // Logging out what site the app will now be taken to
            println("Going to " + this.iconLinkTo);
          } else if (this.iconLinkTo.indexOf("_") == 0) {
            callFunction = this.iconLinkTo;
            if (this.iconLinkTo.equals("_addToFavourites")) {
              if (myCameraLiveViewScreen.favouriteLocation) {
                this.setImage(loadImage("favIconNoImage.png"));
              } else {
                this.setImage(loadImage("favIconYesImage.png"));
              }
            }

            // Logging out what page the app will now be taken to
            println("Calling the " + this.iconLinkTo + "() function");
          } else if (this.iconLinkTo.indexOf("-") == 0) {
            currentScreen = returnTo;
            // Logging out what page the app will now be taken to
            println("Returning to the " + this.iconLinkTo + "screen");
          } else {
            // This is an INTERNAL link
            // Setting the global currentScreen variable to be equal to the link
            // contained within the icon that was clicked on (so it can be used
            // in the main sketch to determine which page to display)
            currentScreen = this.iconLinkTo;

            // Resets required for the About Screen.
            // Resetting teh screenTitleY position to it's original value (as it may have been
            // incremented if the about screen was scrolled
            screenTitleY = appHeight * 0.08;
            // Resetting the about screen's loaded value to false, so that the next time it is opened
            // it will reset to it's original positions
            myAboutScreen.loaded = false;

            // Logging out what page the app will now be taken to
            println("Going to the " + this.iconLinkTo);
          }
        }
      }
    }
  }
}