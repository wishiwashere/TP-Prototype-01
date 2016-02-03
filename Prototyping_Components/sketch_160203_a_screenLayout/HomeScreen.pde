public class HomeScreen extends Screen{
  
  // Creating a public constructor for the HomeScreen class, so that
  // instances of it can be created anywhere in the sketch
  public HomeScreen(color col){
    
    // Passing all of the parametres from the constructor
    // class into the super class (Screen)
    super(col);
    
    // Creating new instances of the Icon class, setting their x to width/2 (so that they will
    // appear centered on the screen), incrementing their y by 100 pixels each, so that they
    // will be spaced out vertically, setting their width to 260 and height to 50 (random numbers 
    // I chose for the time being). I am also passing in a rotation of 0 for the time being, as we
    // may want to have the ability to rotate these based on the device's orientation at a later
    // stage. Passing in a colour value of while. Passing in a linkTo value of the name of the 
    // screen they will later link to, and finally, passing in the text that should be displayed
    // on them (this is the only parametre that is not required).
    Icon randomTravelIcon = new Icon(width/2, 100, 260, 50, 0, #ffffff, "CameraLiveViewScreen", "Random");
    Icon searchTravelIcon = new Icon(width/2, 200, 260, 50, 0, #ffffff, "SearchTravelScreen", "Search");
    Icon aboutIcon = new Icon(width/2, 300, 260, 50, 0, #ffffff, "AboutScreen", "About");
    Icon settingsIcon = new Icon(width/2, 400, 260, 50, 0, #ffffff, "SettingsScreen", "Settings");
    Icon myFavouritesIcon = new Icon(width/2, 500, 260, 50, 0, #ffffff, "FavouritesScreen", "My Favourites");
    
    // Creating the allIcons array to be able to store 5 objects i.e. the 5 icons I created above
    allIcons = new Icon[5];
    
    // Storing each of the icon buttons in the allIcons array, to make it easier to call the same methods
    // on them later on (i.e. I can now loop through them, instead of calling methods individually on them
    allIcons[0] = randomTravelIcon;
    allIcons[1] = searchTravelIcon;
    allIcons[2] = aboutIcon;
    allIcons[3] = settingsIcon;
    allIcons[4] = myFavouritesIcon;
  }
}