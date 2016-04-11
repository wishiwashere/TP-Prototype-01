package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.
import processing.core.*;

// This class extends from the Screen class, which in turn extends from the Rectangle class, and so
// inherits methods and variables from both of these classes. This screen is displayed while an image
// has been unable to be shared and/or saved.
public class ShareSaveUnsuccessfulScreen extends Screen {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

    // Creating a private variable to store the image which will be displayed as part of this screen
    private PImage shareSaveUnsuccessfulScreenImage;

    // Creating a public constructor for the class so that an instance of it can be declared in the main sketch
    public ShareSaveUnsuccessfulScreen(Sketch _sketch) {

        // Passing the instance of the Sketch class, which was passed to constructor of this class, to the
        // super class (Screen), which will in turn pass it to it's super class (Rectangle). The purpose
        // of this variable is so that we can access the Processing library, along with other global methods
        // and variables of the main sketch class, from all other classes.
        super(_sketch);

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this class.
        sketch = _sketch;

        // Initialising this class's private shareSaveUnsuccessfulScreenImage variable by loading the image
        // in from the assets folder, so that it can be displayed when this screen is called.
        shareSaveUnsuccessfulScreenImage = sketch.loadImage("shareSaveUnsuccessfulScreenImage.png");

        // Creating the icon/s for this screen, using locally scoped variables, as these icons will be only
        // ever be referred to from the allIcons array. Setting their x, and y, based on percentages of the
        // width and height (where icon positioning variables are used, these were defined in the main sketch.
        // Not passing in any width or height, so as to allow this icon to be set to the default size in the
        // Icon class of the app. Passing in a name for the icon, followed by a boolean to choose whether this
        // name should be displayed on the icon or not. Finally, passing in a linkTo value of the name of the
        // screen or function they will later link to.
        Icon retryIcon = new Icon(sketch, sketch.iconCenterX  * 0.55, sketch.largeIconBottomY * 0.75, sketch.largeIconSize, sketch.largeIconSize, sketch.loadImage("retryIconImage.png"), "Retry", true, "Below", "_sendTweet");
        Icon shareImageToDeviceAppsIcon = new Icon(sketch, sketch.iconCenterX * 1.45, sketch.iconCenterY * 0.75, sketch.largeIconSize, sketch.largeIconSize, sketch.loadImage("shareImageToDeviceAppsImage.png"), "Share", true, "Below", "_shareImageToDeviceApps");
        Icon deleteIcon = new Icon(sketch, sketch.iconCenterX * 0.55, sketch.largeIconBottomY, sketch.largeIconSize, sketch.largeIconSize, sketch.loadImage("deleteIconImage.png"), "Delete", true, "Below", "CameraLiveViewScreen");
        Icon saveIcon = new Icon(sketch, sketch.iconCenterX * 1.45, sketch.largeIconBottomY, sketch.largeIconSize, sketch.largeIconSize, sketch.loadImage("saveIconImage"), "Save", true, "Below", "ShareSaveSuccessfulScreen");

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {retryIcon, deleteIcon, saveIcon, shareImageToDeviceAppsIcon};

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

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's icons.
        // This method will then in turn call it's super class's (Rectangle) method, to generate the screen.
        this.drawScreen();

        // Creating a local string, which will contain the value of the tasks that have been unsuccessful.
        // This will be based on the series of if statements below, and then used to add text to this screen
        // to give the user feedback on which issue occurred.
        String methodUsed = "";

        // Checking if this image has been unable to be saved, shared, or both, so as to determine
        // which lines of text to add to this screen
        if (sketch.imageSaved == false && sketch.imageShared == false){
            methodUsed = "shared or saved";
        } else if (sketch.imageSaved == false){
            methodUsed = "saved";
        } else if (sketch.imageShared == false){
            methodUsed = "shared";
        }

        // Using the addText() method, as inherited from the Rectangle class, to add the following
        // lines of text to the screen. Using positioning values which will make this screen responsive
        // to the size of the device it is being displayed on. Using the iconCenterX variable, as defined
        // in the main Sketch class
        this.addText("We're sorry :(", sketch.iconCenterX, sketch.appHeight * 0.1);
        this.addText("Your postcard", sketch.iconCenterX, sketch.appHeight * 0.18);
        this.addText("was not able to be", sketch.iconCenterX, sketch.appHeight * 0.26);

        // Specifying which method/s have been unsuccessful, using the string generated above, based on which
        // tasks were not completed
        this.addText(methodUsed, sketch.iconCenterX, sketch.appHeight * 0.34);

        // Adding this screen's private shareSaveUnsuccessfulScreenImage, using the addImage() method, as inherited
        // from the Rectangle class, so it will appear as part of this screen. Calculating the x, y, width and height
        // based on the current width and height of the device this app is running on.
        this.addImage(this.shareSaveUnsuccessfulScreenImage, sketch.iconCenterX, sketch.appHeight * 0.48, sketch.appWidth * 0.5, sketch.appHeight * 0.16);
    }
}