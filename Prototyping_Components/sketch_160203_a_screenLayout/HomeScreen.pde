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
    
    randomTravelIcon = new Icon(width/2, 100, 260, 50, 0, #ffffff, "RandomTravelScreen", "Random");
    searchTravelIcon = new Icon(width/2, 200, 260, 50, 0, #ffffff, "SearchTravelScreen", "Search");
    aboutIcon = new Icon(width/2, 300, 260, 50, 0, #ffffff, "AboutScreen", "About");
    settingsIcon = new Icon(width/2, 400, 260, 50, 0, #ffffff, "SettingsScreen", "Settings");
    myFavouritesIcon = new Icon(width/2, 500, 260, 50, 0, #ffffff, "myFavouritesScreen", "My Favourites");
  }
  
  public void showHomeScreen(){
    this.show();
    randomTravelIcon.showIcon();
    searchTravelIcon.showIcon();
    aboutIcon.showIcon();
    settingsIcon.showIcon();
    myFavouritesIcon.showIcon();
  }
}