public class HomeScreen extends Screen{
  
  // Creating a public constructor for the HomeScreen class, so that
  // an instance of it can be declared in the main sketch
  public HomeScreen(color col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
       
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon searchTravelIcon = new Icon(appWidth * 0.3, appHeight * 0.2, squareIconSize, squareIconSize, searchPageIconImage, "Search", true, "Below", "SearchScreen");
    Icon randomTravelIcon = new Icon(appWidth * 0.7, appHeight * 0.2, squareIconSize, squareIconSize, randomPageIconImage, "Random", true, "Below", "CameraLiveViewScreen");
    Icon myFavouritesIcon = new Icon(appWidth * 0.3, appHeight * 0.5, squareIconSize, squareIconSize, favouritesPageIconImage, "My Favourites", true, "Below", "FavouritesScreen");
    Icon aboutIcon = new Icon(appWidth * 0.7, appHeight * 0.5, squareIconSize, squareIconSize, aboutPageIconImage, "About", true, "Below", "AboutScreen");
    Icon settingsIcon = new Icon(appWidth * 0.5, appHeight * 0.8, squareIconSize, squareIconSize, settingsPageIconImage, "Settings", true, "Below", "SettingsScreen");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {randomTravelIcon, searchTravelIcon, myFavouritesIcon, aboutIcon, settingsIcon};
    
    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }
}