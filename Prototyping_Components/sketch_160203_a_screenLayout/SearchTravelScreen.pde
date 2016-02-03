public class SearchTravelScreen extends Screen{
  private Icon[] allIcons;
  private Icon homeIcon;
  private Icon cameraLiveViewIcon;
  
  public SearchTravelScreen(color col){
    super(col);
    
    homeIcon = new Icon(width/2, 100, 260, 50, 0, #ffffff, "HomeScreen", "Go Home");
    cameraLiveViewIcon = new Icon(width/2, 200, 260, 50, 0, #ffffff, "CameraLiveViewScreen", "Search");
    
    allIcons = new Icon[2];
    allIcons[0] = homeIcon;
    allIcons[1] = cameraLiveViewIcon;
  }
  
  public void showSearchTravelScreen(){
    this.show();
    for(int i = 0; i < allIcons.length; i++){
      allIcons[i].showIcon();
      if(mousePressed)
      {
        allIcons[i].checkMouseOver();
      }
    }
  }
}