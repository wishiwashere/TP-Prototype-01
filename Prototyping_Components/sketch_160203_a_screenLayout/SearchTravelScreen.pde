public class SearchTravelScreen extends Screen{
  
  // Creating a public constructor for the SearchTravelScreen class, so that
  // instances of it can be created anywhere in the sketch
  public SearchTravelScreen(color col){
    super(col);
    
    Icon homeIcon = new Icon(width - 50, 50, 50, 50, 0, #ffffff, "Home", false, "HomeScreen");
    Icon searchIcon = new Icon(width/2 + 75, height/2, 150, 50, 0, #ffffff, "Search", true, "CameraLiveViewScreen");
    Icon cancelIcon = new Icon(width/2 - 75, height/2, 150, 50, 0, #ffffff, "Cancel", true, "HomeScreen");
    
    allIcons = new Icon[3];
    allIcons[0] = homeIcon;
    allIcons[1] = searchIcon;
    allIcons[2] = cancelIcon;
    
    screenTitle = "Search";
  }
}