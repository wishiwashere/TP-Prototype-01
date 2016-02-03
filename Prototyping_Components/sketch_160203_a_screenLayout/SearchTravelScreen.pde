public class SearchTravelScreen extends Screen{
  
  // Creating a public constructor for the SearchTravelScreen class, so that
  // instances of it can be created anywhere in the sketch
  public SearchTravelScreen(color col){
    super(col);
    
    Icon homeIcon = new Icon(width/2, 100, 260, 50, 0, #ffffff, "HomeScreen", "Go Home");
    Icon cameraLiveViewIcon = new Icon(width/2, 200, 260, 50, 0, #ffffff, "CameraLiveViewScreen", "Search");
    
    allIcons = new Icon[2];
    allIcons[0] = homeIcon;
    allIcons[1] = cameraLiveViewIcon;
  }
}