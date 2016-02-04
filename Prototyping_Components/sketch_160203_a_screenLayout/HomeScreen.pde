public class HomeScreen extends Screen{
  
  // Creating a public constructor for the HomeScreen class, so that
  // an instance of it can be declared in the main sketch
  public HomeScreen(color col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(col);
    
    // Creating the icons for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their x to 
    // width/2 (so that they will appear centered on the screen), incrementing their y by 100 pixels
    // each, so that they will be spaced out vertically, setting their width to 260 and height to 50 
    // (random numbers I chose for the time being). I am also passing in a rotation of 0 for the time being, as we
    // may want to have the ability to rotate these based on the device's orientation at a later
    // stage. Passing in a colour value of while. Passing in a name for the icon, followed by a
    // boolean to choose whether this name should be displayed on the button or not. Finally, passing
    // in a linkTo value of the name of the screen they will later link to
    Icon randomTravelIcon = new Icon(width/2, 150, 260, 50, #ffffff, "Random", true, "CameraLiveViewScreen");
    Icon searchTravelIcon = new Icon(width/2, 250, 260, 50, #ffffff, "Search", true, "SearchTravelScreen");
    Icon aboutIcon = new Icon(width/2, 350, 260, 50, #ffffff, "About", true, "AboutScreen");
    Icon settingsIcon = new Icon(width/2, 450, 260, 50, #ffffff, "Settings", true, "SettingsScreen");
    Icon myFavouritesIcon = new Icon(width/2, 550, 260, 50, #ffffff, "My Favourites", true, "FavouritesScreen");
    
    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {randomTravelIcon, searchTravelIcon, aboutIcon, settingsIcon, myFavouritesIcon};
    
    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);
  }
}