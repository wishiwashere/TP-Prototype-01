public class CameraLiveViewScreen extends Screen {
  public Boolean favouriteLocation;

  // Creating a public constructor for the CameraLiveViewScreen class, so that
  // an instance of it can be declared in the main sketch
  public CameraLiveViewScreen() {
    // Calling the super class (Screen), which will in turn call it's super class 
    // (Rectangle) and create a rectangle with the default values i.e. fullscreen, 
    // centered etc.
    super();

    favouriteLocation = false;

    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(iconRightX, iconTopY, loadImage("homeIconWhiteImage.png"), "Home", false, "HomeScreen");
    Icon favIcon = new Icon(iconLeftX, iconTopY, favIconImage, "Add to Favourites", false, "_addToFavourites");
    Icon shakeIcon = new Icon(iconLeftX, iconBottomY, shakeIconImage, "Turn on/off Shake", false);
    Icon shutterIcon = new Icon(iconCenterX, iconBottomY, shutterIconImage, "Take a Picture", false, "ImagePreviewScreen");
    Icon switchViewIcon = new Icon(iconRightX, iconBottomY, switchViewIconImage, "Switch View", false, "_switchCameraView");

    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, favIcon, shakeIcon, shutterIcon, switchViewIcon};

    // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
    // the temporary allIcons array to the screenIcons array of the Screen class so that they 
    // can be looped through by the showScreen() method, and methods inherited from the Icon 
    // class (such as showIcon and checkMouseOver) can be called on them from within this array. 
    // This reduces the need for each screen to have to loop through it's icons, or call the 
    // same method on multiple icons.
    this.setScreenIcons(allIcons);

    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("");
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {
    image(loadImage("pyramids.jpg"), appWidth/2, appHeight/2, appWidth, appHeight);

    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();

    // Calls super super class (Rectangle). Passing in the current frame image, the width and height
    // which have been reversed - i.e. the width will now be equal to the height of the app, as the 
    // ketaiCamera image requires it's rotation to be offset by 90 degress (either in the plus or the 
    // minus depending on whether you are using the front or rear camera) so the width and the height
    // need to swap to fit with the image's new resolution
    this.addBackgroundImage(currentImage, appHeight, appWidth, cameraScale, cameraRotation);
  }

  private void switchCameraView()
  {    
    // If the camera is already running before we try and effect it
    if (ketaiCamera.isStarted())
    {
      // Checking if the device has more than one camera. If it does we want to toggle between them
      if (ketaiCamera.getNumberOfCameras() > 1)
      {
        // Ternary operator to toggle between cameras 1 & 0 (i.e. front and back)
        camNum = camNum == 0 ? 1 : 0;

        // Toggle the image rotation value between a plus and a minus i.e. -90 and 90
        cameraRotation *= -1;

        // Toggling the scale of the camera image between 1 and -1 (depending on if the camera
        // is front or rear facing (only on devices with more than one camera)
        cameraScale *= -1;

        // Stopping the ketaiCamera so that no new frames will be read in, switching to the camera specified
        // by the camNum, then restarting the camera
        ketaiCamera.stop();
        ketaiCamera.setCameraID(camNum);
        ketaiCamera.start();
      }
    }
  }
}