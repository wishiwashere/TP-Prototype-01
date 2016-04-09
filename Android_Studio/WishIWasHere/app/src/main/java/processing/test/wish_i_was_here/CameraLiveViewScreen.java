package processing.test.wish_i_was_here;

import processing.core.PApplet;

public class CameraLiveViewScreen extends Screen {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

    public Icon favIcon;
    public Icon shakeIcon;

    public Boolean favouriteLocation;

    // Creating a public constructor for the CameraLiveViewScreen class, so that
    // an instance of it can be declared in the main sketch
    public CameraLiveViewScreen(Sketch _sketch) {

        // Calling the super class (Screen), which will in turn call it's super class
        // (Rectangle) and create a rectangle with the default values i.e. fullscreen,
        // centered etc.
        super(_sketch);

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this class.
        sketch = _sketch;

        this.favouriteLocation = false;

        String switchViewIconImage;
        if(sketch.ketaiCamera.getNumberOfCameras() > 1){
            switchViewIconImage = "switchViewIconImage.png";
        } else {
            switchViewIconImage = "switchViewIconDisabledImage.png";
        }

        // Creating the icon/s for this screen, using locally scoped variables, as these
        // icons will be only ever be referred to from the allIcons array. Setting their
        // x, and y, based on percentages of the width and height (where icon positioning variables
        // are used, these were defined in the main sketch. Not passing in any width or height, so as
        // to allow this icon to be set to the default size in the Icon class of the app . Passing
        // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
        // whether this name should be displayed on the icon or not. Finally, passing in a linkTo
        // value of the name of the screen they will later link to. The title arguments, as well
        // as the linkTo argument, are optional
        Icon homeIcon = new Icon(sketch, sketch.iconRightX, sketch.iconTopY, sketch.loadImage("homeIconWhiteImage.png"), "Home", false, "HomeScreen");
        favIcon = new Icon(sketch, sketch.iconLeftX, sketch.iconTopY, sketch.loadImage("favIconImage.png"), "Add to Favourites", false, "_addToFavourites");
        shakeIcon = new Icon(sketch, sketch.iconLeftX, sketch.iconBottomY, sketch.loadImage("shakeIconOffImage.png"), "Turn on/off Shake", false, "_switchShakeMovement");
        Icon shutterIcon = new Icon(sketch, sketch.iconCenterX, sketch.iconBottomY, sketch.loadImage("shutterIconImage.png"), "Take a Picture", false, "_mergeImages");
        Icon switchViewIcon = new Icon(sketch, sketch.iconRightX, sketch.iconBottomY, sketch.loadImage(switchViewIconImage), "Switch View", false, "_switchCameraView");

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

        // Checking if the mouse is pressed (i.e. the user wants to scroll around the background of this image)
        if (sketch.mousePressed) {

            // Calculating the amount scrolled, based on the distance between the previous y position,
            // and the current y position. When the mouse is first pressed, the previous y position
            // is initialised (in the main sketch) but then while the mouse is held down, the previous
            // y position gets updated each time this function runs (so that the scrolling can occur
            // while the person is still moving their hand (and not just after they release the screen)
            float amountScrolledX = dist(0, sketch.previousMouseX, 0, sketch.mouseX);
            float amountScrolledY = dist(0, sketch.previousMouseY, 0, sketch.mouseY);

            if (sketch.previousMouseX > sketch.mouseX) {
                // The user has scrolled RIGHT

                // Decrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
                // operator to check that this will not result in a value less than 0 (the minimum
                // value allowed for the heading. If it does, then resetting the heading to 359 i.e. so the
                // user can continue turn around in that direction, otherwise allowing it to equal to the
                // current heading value minus the amount scrolled on the X
                sketch.googleImageHeading = (sketch.googleImageHeading + amountScrolledX) > 359 ? 0 : sketch.googleImageHeading + amountScrolledX;
                println("scrolled right. heading is now " + googleImageHeading);
            } else {
                // The user has scrolled LEFT

                // Incrementing the googleImageHeading by the amount scrolled on the x axis. Using a ternary
                // operator to check that this will not result in a value greater than 359 (the maximum
                // value allowed for the heading. If it does, then resetting the heading to 0 i.e. so the
                // user can continue turn around in that direction, otherwise allowing it to equal to the
                // current heading value plus the amount scrolled on the X
                sketch.googleImageHeading = (sketch.googleImageHeading - amountScrolledX) < 0 ? 359 : sketch.googleImageHeading - amountScrolledX;
                println("scrolled left. heading is now " + sketch.googleImageHeading);
            }

            // Checking if shakeMovementOn is currently false, because when shake movement is activated, the user will
            // not be controlling the pitch of the Google image by scrolling, but instead by moving their device around
            // to look "up" and "down".
            if(sketch.shakeMovementOn == false) {

                // Logging out the amount the user has scrolled (for TESTING purposes)
                println("amountScrolledY = " + amountScrolledY);

                // Determining which direction the user has scrolled, based on the previous and current mouse Y positions
                // i.e. has the user scrolled up or down.
                if (sketch.previousMouseY > sketch.mouseY) {
                    // The user has scrolled UP

                    // Incrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
                    // operator to check that this will not result in a value greater than 90 (the maximum
                    // value allowed for the pitch). If it does, then stopping the pitch at 90 i.e. so the
                    // user cannot exceed the maximum value, otherwise allowing it to equal to the current pitch
                    // value plus the amount scrolled on the Y axis.
                    sketch.googleImagePitch = (sketch.googleImagePitch - amountScrolledY) < -90 ? -90 : sketch.googleImagePitch - amountScrolledY;

                    // Logging out the current pitch of the Google image (for TESTING purposes)
                    println("scrolled up. pitch is now " + sketch.googleImagePitch);
                } else {
                    // The user has scrolled DOWN

                    // Decrementing the googleImagePitch by the amount scrolled on the y axis. Using a ternary
                    // operator to check that this will not result in a value less than -90 (the minimum
                    // value allowed for the pitch). If it does, then stopping the pitch at -90 i.e. so the
                    // user cannot exceed the minimum value, otherwise allowing it to equal to the current pitch
                    // value minus the amount scrolled on the Y axis.
                    sketch.googleImagePitch = (sketch.googleImagePitch + amountScrolledY) > 90 ? 90 : sketch.googleImagePitch + amountScrolledY;

                    // Logging out the current pitch of the Google image (for TESTING purposes)
                    println("scrolled down. pitch is now " + sketch.googleImagePitch);
                }

                // Calling the loadGoogleImage method from the main Sketch class, so that a new google image will be
                // loaded in, with the new heading and pitch values specified above. The call to this function only
                // occurs within this class when
                sketch.loadGoogleImage();
            }
            sketch.previousMouseX = sketch.mouseX;
            sketch.previousMouseY = sketch.mouseY;
        }

        // Adding the currentLocationImage to the CameraLiveViewScreen, so that the user can feel like they are taking a picture
        // in that location. Passing in the currentLocationImage, as sourced from the Google Street View Image API, using the
        // location specified by the user. Setting the rotation of this image to be equal to the orientationRotation of the
        // app, so that the image will be rotated based on which way the user is holding the device, so users can take pictures
        // in both landscape and portrait.
        this.addImage(sketch.currentLocationImage, sketch.appWidth, sketch.appHeight, sketch.orientationRotation);

        // Adding the current keyed image to the CameraLiveViewScreen, so that the user can see themselves in the location added
        // above. Setting the scaleX of this image to be equal to the cameraScale, which accounts for and corrects the way in which
        // front facing cameras read in images in reverse (so they no longer appear reversed). Setting the rotation of this image
        // to be equal to the cameraRotation, which accounts for and corrects the way in which ketaiCamera reads in images, so the
        // image appears in the correct orientation.
        this.addImage(sketch.currentImage, sketch.appHeight, sketch.appWidth, sketch.cameraScale, sketch.cameraRotation);

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's icons. This method will then
        // in turn call it's super class's (Rectangle) method, to generate the screen.
        this.drawScreen();
    }

    public void switchCameraView()
    {
        sketch.callFunction = "";

        // If the camera is already running before we try and effect it
        if (sketch.ketaiCamera.isStarted())
        {
            // Checking if the device has more than one camera. If it does we want to toggle between them
            if (sketch.ketaiCamera.getNumberOfCameras() > 1)
            {
                // Ternary operator to toggle between cameras 1 & 0 (i.e. front and back)
                sketch.camNum = sketch.camNum == 0 ? 1 : 0;

                // Toggle the image rotation value between a plus and a minus i.e. -90 and 90
                sketch.cameraRotation *= -1;

                // Toggling the scale of the camera image between 1 and -1 (depending on if the camera
                // is front or rear facing (only on devices with more than one camera)
                sketch.cameraScale *= -1;

                // Stopping the ketaiCamera so that no new frames will be read in, switching to the camera specified
                // by the camNum, then restarting the camera
                sketch.ketaiCamera.stop();
                sketch.ketaiCamera.setCameraID(sketch.camNum);
                sketch.ketaiCamera.start();
            }
        }
    }
}