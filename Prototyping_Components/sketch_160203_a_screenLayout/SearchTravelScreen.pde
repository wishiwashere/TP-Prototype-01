public class SearchTravelScreen extends Screen{
  
  // Creating a public constructor for the SearchTravelScreen class, so that
  // an instance of it can be declared in the main sketch
  public SearchTravelScreen(color col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (these appWidth and appHeight 
    // variables were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(appWidth * 0.85, appHeight * 0.085, #ffffff, "Home", false, "HomeScreen");
    Icon cancelIcon = new Icon(appWidth * 0.3, appHeight * 0.4, appWidth * 0.4, appHeight * 0.08, #ffffff, "Cancel", true, "HomeScreen");
    Icon searchIcon = new Icon(appWidth * 0.7, appHeight * 0.4, appWidth * 0.4, appHeight * 0.08, #ffffff, "Search", true, "CameraLiveViewScreen");
    
    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, searchIcon, cancelIcon};
    
    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);
    
    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("Search");
  }
  
  public void showScreen(){
    
    // Calling the drawScreen() method of the super class, to create the basic background, and 
    // icons of the screen
    this.drawScreen();
  }
}