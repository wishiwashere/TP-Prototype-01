public class HomeScreen extends Screen{
  // Creating a public constructor for the HomeScreen class, so that
  // instances of it can be created anywhere in the sketch
  private Icon randomTravelIcon;
  private Icon searchTravelIcon;
  private Icon aboutIcon;
  private Icon settingsIcon;
  private Icon myFavouritesIcon;
  
  public HomeScreen(color col){
    // Passing all of the parametres from the constructor
    // class into the super class (Screen)
    super(col);
    
    randomTravelIcon = new Icon(50, 100, 260, 50, #ffffff, "RandomTravelScreen");
    searchTravelIcon = new Icon(50, 200, 260, 50, #ffffff, "SearchTravelScreen");
    aboutIcon = new Icon(50, 300, 260, 50, #ffffff, "AboutScreen");
    settingsIcon = new Icon(50, 400, 260, 50, #ffffff, "SettingsScreen");
    myFavouritesIcon = new Icon(50, 500, 260, 50, #ffffff, "myFavouritesScreen");
  }
  
  public void showIcons(){
    this.show();
    randomTravelIcon.show();
    searchTravelIcon.show();
    aboutIcon.show();
    settingsIcon.show();
    myFavouritesIcon.show();
  }
}