public class CameraLiveViewScreen extends Screen{
  
  // Creating a public constructor for the CameraLiveViewScreen class, so that
  // instances of it can be created anywhere in the sketch
  public CameraLiveViewScreen(){
    super();
    
    Icon homeIcon = new Icon(width - 50, 50, 50, 50, 0, #ffffff, "Go Home", false, "HomeScreen");
    Icon favIcon = new Icon(50, 50, 50, 50, 0, #ffffff, "Add to Favourites", false);
    Icon shakeIcon = new Icon(50, height - 50, 50, 50, 0, #ffffff, "Turn on/off Shake", false);
    Icon shutterIcon = new Icon(width/2, height - 50, 50, 50, 0, #ffffff, "Take a Picture", false);
    Icon switchViewIcon = new Icon(width - 50, height - 50, 50, 50, 0, #ffffff, "Switch View", false);
    
    allIcons = new Icon[5];
    allIcons[0] = homeIcon;
    allIcons[1] = favIcon;
    allIcons[2] = shakeIcon;
    allIcons[3] = shutterIcon;
    allIcons[4] = switchViewIcon;
  }
}