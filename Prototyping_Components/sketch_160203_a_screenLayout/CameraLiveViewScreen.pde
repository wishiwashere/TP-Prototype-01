public class CameraLiveViewScreen extends Screen{
  
  // Creating a public constructor for the CameraLiveViewScreen class, so that
  // an instance of it can be declared in the main sketch
  public CameraLiveViewScreen(){
    
    // Calling the super class (Screen), which will in turn call it's super class 
    // (Rectangle) and create a rectangle with the default values i.e. fullscreen, 
    // centered etc.
    super();
    
    // Creating the icons for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. I am also passing in a 
    // rotation of 0 for the time being, as we may want to have the ability to rotate these 
    // based on the device's orientation at a later stage. Passing in a colour value of while. 
    // Passing in a name for the icon, followed by a boolean to choose whether this name should 
    // be displayed on the button or not. Finally, passing in a linkTo value of the name of the 
    //screen they will later link to
    Icon homeIcon = new Icon(width - 50, 50, 50, 50, 0, #ffffff, "Go Home", false, "HomeScreen");
    Icon favIcon = new Icon(50, 50, 50, 50, 0, #ffffff, "Add to Favourites", false);
    Icon shakeIcon = new Icon(50, height - 50, 50, 50, 0, #ffffff, "Turn on/off Shake", false);
    Icon shutterIcon = new Icon(width/2, height - 50, 50, 50, 0, #ffffff, "Take a Picture", false);
    Icon switchViewIcon = new Icon(width - 50, height - 50, 50, 50, 0, #ffffff, "Switch View", false);
    
    // Creating the allIcons array to store one icon. This array was declared in the
    // super class, so that when icons are saved to it, they can be accessed by this
    // screen's super class (Screen), so that they can be looped through by the showScreen()
    // method, and methods inherited from the Icon class (such as showIcon and checkMouseOver)
    // can be called on them from within this array. This reduces the need for each screen
    // to have to loop through it's icons, or call the same method on multiple icons.
    allIcons = new Icon[5];
    
    // Storing this screen's icons in the allIcons array (which was declared in this class's
    // super class (Screen)
    allIcons[0] = homeIcon;
    allIcons[1] = favIcon;
    allIcons[2] = shakeIcon;
    allIcons[3] = shutterIcon;
    allIcons[4] = switchViewIcon;
  }
}