protected class Screen extends Rectangle {

  // Declaring the screenIcons array, which will be used to store the 
  // icons from each screen. This array will be initialised through the
  // setScreenIcons() method, where each screen will pass in an array
  // or the icons that are present on it's screen. The purpose of storing
  // this array within the Screen class, as opposed to in each instance's
  // own class, is that now we can deal with looping through, and calling
  // methods on multiple icons of a screen only when their screen is being
  // shown i.e. when showScreen() is being called
  private Icon[] screenIcons = {};

  // Creating and initialising the screenTitle property of the screen to be
  // equal to an empty string. Each instance of the Screen class can then
  // use the setScreenTitle() method to specify a custom title, which
  // will then be used as the header text on that instance of the screen.
  // If no title is specified, then header text will not be added to the 
  // screen
  public String screenTitle = "";

  // Creating protected constructors for the Screen class, so that they can
  // only be accessed by classes which extend from this class

  /*-------------------------------------- Constructor() ------------------------------------------------*/

  protected Screen() {    
    // Calling this class's super class (Screen) to create a screen using
    // the default settings
    super();
  }

  protected Screen(PImage img) {    
    // Calling this class's super class (Screen) to create a screen using
    // the default settings along with a background image
    super(img);
  }

  protected Screen(color col) {
    // Calling this class's super class (Screen) to create a screen using
    // the default settings, along with setting the colour as specified
    super(col);
  }

  protected Screen(float x, float y, float w, float h, PImage img) {
    super(x, y, w, h, img);
  }


  protected Screen(float x, float y, float w, float h, color col) {
    super(x, y, w, h, col);
  }

  /*-------------------------------------- drawScreen() ------------------------------------------------*/

  // Creating a public method so that this screen can be displayed from
  // within the main sketch
  public void drawScreen() {    
    // Calling the show() method (as inherited from the Rectangle class)
    // to display this screen's background
    this.show();

    // Checking if this screen has been given a title (i.e. if the contents
    // of the screenTitle is at least one character long
    if (screenTitle.length() > 0)
    {
      fill(0);
      this.addText(screenTitle, appWidth/2, screenTitleY);
    }

    // Checking if this screen has any icons to be displayed
    if (screenIcons.length > 0)
    {
      // Looping through each of the screen's icons
      for (int i = 0; i < screenIcons.length; i++) {
        // Calling the showIcon() method (as inherited from the Icon class)
        // to display the icon on screen
        screenIcons[i].showIcon();

        // Checking if the mouse is currently pressed i.e. if a click has
        // been detected
        if (mousePressed)
        {
          // Calling the checkMouseOver() method (as inherited from the Icon
          // class, to see if the mouse was over this icon when it was clicked
          screenIcons[i].checkMouseOver();
        }
      }
    }
  }

  /*-------------------------------------- get() and set() ------------------------------------------------*/

  protected void setScreenTitle(String title) {
    // Setting the screenTitle to the title passed in by each screen. If no
    // title is passed, this variable has already been initialised to an 
    // empty string above
    screenTitle = title;
  }

  protected Icon[] getScreenIcons() {
    return screenIcons;
  }

  protected void setScreenIcons(Icon[] icons) {
    // Initialising the screenIcons array with the contents from the allIcons
    // array that each screen will pass in
    screenIcons = icons;
  }
}