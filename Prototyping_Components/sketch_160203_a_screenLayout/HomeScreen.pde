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
    // x, y, width and height properties based on percentages of the width and height
    // of the app (these appWidth and appHeight variables were defined in the main
    // sketch. Passing in a colour value of white. Passing in a name for the icon, followed by a
    // boolean to choose whether this name should be displayed on the icon or not. Finally, passing
    // in a linkTo value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon randomTravelIcon = new Icon(appWidth * 0.3, appHeight * 0.4, appWidth * 0.35, appHeight * 0.08, #ffffff, "Random", true, "CameraLiveViewScreen");
    Icon searchTravelIcon = new Icon(appWidth * 0.7, appHeight * 0.4, appWidth * 0.35, appHeight * 0.08, #ffffff, "Search", true, "SearchTravelScreen");
    Icon myFavouritesIcon = new Icon(appWidth * 0.3, appHeight * 0.6, appWidth * 0.35, appHeight * 0.08, #ffffff, "My Favourites", true, "FavouritesScreen");
    Icon aboutIcon = new Icon(appWidth * 0.7, appHeight * 0.6, appWidth * 0.35, appHeight * 0.08, #ffffff, "About", true, "AboutScreen");
    Icon settingsIcon = new Icon(appWidth * 0.5, appHeight * 0.8, appWidth * 0.35, appHeight * 0.08, #ffffff, "Settings", true, "SettingsScreen");

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
  
  public void showScreen(){
    
    // Calling the drawScreen() method of the super class, to create the basic background, and 
    // icons of the screen
    this.drawScreen();
  }
}