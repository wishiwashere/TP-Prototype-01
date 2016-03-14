public class CameraLiveViewScreen extends Screen {
  public Boolean favouriteLocation;
  
  public Icon favIcon;

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
    favIcon = new Icon(iconLeftX, iconTopY, favIconImage, "Add to Favourites", false, "_addToFavourites");
    Icon shakeIcon = new Icon(iconLeftX, iconBottomY, shakeIconImage, "Turn on/off Shake", false);
    Icon shutterIcon = new Icon(iconCenterX, iconBottomY, shutterIconImage, "Take a Picture", false, "_mergeImages");
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
  }

  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen() {
    
    // Checking if the mouse is pressed (i.e. the user wants to interact with the image)
    if (mousePressed) {
      // Calculating the amount scolled, based on the distance between the previous y position, 
      // and the current y position. When the mouse is first pressed, the previous y position
      // is initialised (in the main sketch) but then while the mouse is held down, the previous
      // y position gets updated each time this function runs (so that the scrolling can occur
      // while the person is still moving their hand (and not just after they release the screen)
      float amountScrolledX = dist(0, previousMouseX, 0, mouseX);
      float amountScrolledY = dist(0, previousMouseY, 0, mouseY);

      if (previousMouseX > mouseX) {
        // The user has scrolled RIGHT
        
        // Decrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
        // operator to check that this will not result in a value less than 0 (the minimum
        // value allowed for the heading. If it does, then resetting the heading to 359 i.e. so the 
        // user can continue turn around in that direction, otherwise allowing it to equal to the
        // current heading value minus the amount scrolled on the X
        googleImageHeading = (googleImageHeading + amountScrolledX) > 359 ? 0 : googleImageHeading + amountScrolledX;
        println("scrolled right. heading is now " + googleImageHeading);
      } else {
        // The user has scrolled LEFT

        // Incrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
        // operator to check that this will not result in a value greater than 359 (the maximum
        // value allowed for the heading. If it does, then resetting the heading to 0 i.e. so the 
        // user can continue turn around in that direction, otherwise allowing it to equal to the
        // current heading value plus the amount scrolled on the X
        googleImageHeading = (googleImageHeading - amountScrolledX) < 0 ? 359 : googleImageHeading - amountScrolledX;
        println("scrolled left. heading is now " + googleImageHeading);
      }
      
      println("amountScrolledY = " + amountScrolledY);
      if (previousMouseY > mouseY) {
        // The user has scrolled UP

        // Incrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
        // operator to check that this will not result in a value greater than 90 (the maximum
        // value allowed for the pitch. If it does, then stopping the pitch at 90 i.e. so the 
        // user cannot excede the maximum value, otherwise allowing it to equal to the current pitch 
        // value plus the amount scrolled on the Y
        googleImagePitch = (googleImagePitch - amountScrolledY) < -90 ? -90 : googleImagePitch - amountScrolledY;
        println("scrolled up. pitch is now " + googleImagePitch);
      } else {
        // The user has scrolled DOWN
        
        // Decrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
        // operator to check that this will not result in a value less than -90 (the minimum
        // value allowed for the pitch. If it does, then stopping the pitch at -90 i.e. so the 
        // user cannot excede the minimum value, otherwise allowing it to equal to the current pitch 
        // value minus the amount scrolled on the Y
        googleImagePitch = (googleImagePitch + amountScrolledY) > 90 ? 90 : googleImagePitch + amountScrolledY;
        println("scrolled down. pitch is now " + googleImagePitch);
      }

      loadGoogleImage();
      previousMouseX = mouseX;
      previousMouseY = mouseY;
    }

    if (compiledImage != null) {
      compiledImage = null;
    }
    // Using the currentLocationImage as the background for the camera live view i.e. so the user
    // can feel like they are taking a picture in that location
    image(currentLocationImage, appWidth/2, appHeight/2, appWidth, appHeight);

    // Calls super super class (Rectangle). Passing in the current frame image, the width and height
    // which have been reversed - i.e. the width will now be equal to the height of the app, as the 
    // ketaiCamera image requires it's rotation to be offset by 90 degress (either in the plus or the 
    // minus depending on whether you are using the front or rear camera) so the width and the height
    // need to swap to fit with the image's new resolution
    this.addBackgroundImage(currentImage, appHeight, appWidth, cameraScale, cameraRotation);
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
  }

  private void switchCameraView()
  {    
    callFunction = "";

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