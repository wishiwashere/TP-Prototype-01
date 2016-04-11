package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.
import processing.core.*;

// This class extends from the Screen class, which in turn extends from the Rectangle class, and so
// inherits methods and variables from both of these classes. This screen is displayed when a user
// decides to keep and image, and offers them the option to save and/or share this image on Twitter.
public class SaveShareScreenA extends Screen {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

    // Declaring two of this screen's icons as public variables, as they will need to be accessible
    // from the main Sketch class, so that their images can be updated accordingly if/when their
    // status changes. Declaring the "next" icon of this screen privately, as it will need to be accessible from the
    // show screen method of this class so that it's title can be updated when needed. Normally,
    // screen icons are only declared within the constructor of the class, as they will be passed to the
    // super class (Screen) and stored there as the icons for this screen. By changing the scope of these
    // variables, they can now be stored as the screen icons, but still directly accessible when needed.
    public Icon saveIcon;
    public Icon twitterIcon;
    private Icon nextIcon;

    // Creating a public constructor for the class so that an instance of it can be declared in the main sketch
    public SaveShareScreenA(Sketch _sketch) {

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

        // Creating temporary String variables, to store the relevant path to the icon image which should
        // be displayed on the saveIcon and twitterIcon icons on this screen, when the sketch first loads.
        // This will depend on the preferences stored by the user (which will have been loaded in in the main
        // sketch before this screen is created). Using ternary operators to configure the value for these
        // variables.
        String saveIconImage = sketch.autoSaveModeOn ? "saveIconOnImage.png" : "saveIconOffImage.png";
        String twitterIconImage = TwitterLoginActivity.twitterLoggedIn ? "twitterAccountIconOnImage.png" : "twitterAccountIconOffImage.png";

        // Creating the icon/s for this screen, using a locally scoped variable for the cancel icon, as this
        // icon will be only ever be referred to from the allIcons array. Initialising the one protected, and
        // two public icons which were declare earlier in this class, as they will be accessible from outside of
        // this constructor, so that their background images and/or titles can be updated when needed.
        // Setting their x, and y, based on percentages of the width and height (where icon positioning
        // variables are used, these were defined in the main sketch. Not passing in any width or height,
        // so as to allow this icon to be set to the default size in the Icon class of the app. Passing in
        // a name for the icon, followed by a boolean to choose whether this name should be displayed on
        // the icon or not. Finally, passing in a linkTo value of the name of the screen or function they will
        // later link to.
        saveIcon = new Icon(sketch, sketch.iconCenterX * 0.55, sketch.iconCenterY * 1.42, sketch.largeIconSize, sketch.largeIconSize, sketch.loadImage(saveIconImage), "Save", true, "Below", "_toggleSavingOfCurrentImage");
        twitterIcon = new Icon(sketch, sketch.iconCenterX * 1.45, sketch.iconCenterY * 1.42, sketch.largeIconSize, sketch.largeIconSize, sketch.loadImage(twitterIconImage), "Twitter", true, "Below", "_switchSendToTwitter");
        Icon cancelIcon = new Icon(sketch, sketch.appWidth * 0.3, sketch.iconBottomY, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "Cancel", true, "Middle", "CameraLiveViewScreen");
        nextIcon = new Icon(sketch, sketch.appWidth * 0.7, sketch.iconBottomY, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "Next", true, "Middle", "_keepImage");

        // Creating a temporary allIcons array to store the icon/s we have created above, so that they can
        // be passed to the super class (Screen) to be stored as this screen's icons.
        Icon[] allIcons = {twitterIcon, saveIcon, cancelIcon, nextIcon};

        // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
        // the temporary allIcons array to the screenIcons array of the Screen class so that they
        // can be looped through by the showScreen() method, and methods inherited from the Icon
        // class (such as showIcon and checkMouseOver) can be called on them from within this array.
        // This reduces the need for each screen to have to loop through it's icons, or call the
        // same method on multiple icons.
        this.setScreenIcons(allIcons);
    }

    // Creating a public showScreen method, which is called by the draw() function whenever this
    // screen needs to be displayed
    public void showScreen() {

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's icons.
        // This method will then in turn call it's super class's (Rectangle) method, to generate the screen.
        this.drawScreen();

        // Checking that at least one of the two option (saving or sharing) is currently turned on or not
        if(sketch.saveThisImageOn || sketch.sendToTwitterOn){
            // Since the user has chosen to complete at lease one of these tasks, the nextIcon will now
            // trigger the keepImage() method of the main sketch, and it's title will appear as "Next"
            nextIcon.setIconLinkTo("_keepImage");
            nextIcon.setIconTitle("Next");
        } else {
            // Since the user has not chosen to complete at lease one of these tasks, the nextIcon will now
            // just return them to the CameraLiveViewScreen, and it's title will appear as "Done" (as there
            // will be no more steps to be completed after this screen unless one of these options was
            // selected).
            nextIcon.setIconLinkTo("CameraLiveViewScreen");
            nextIcon.setIconTitle("Done");
        }

        // Adding this the current compiledImage to the screen, using the addImage() method, as inherited from the
        // Rectangle class, so it will appear as part of this screen. Calculating the x, y, width and height
        // based on the current width and height of the device this app is running on. This image contains the Google
        // street view background, the keyed image of the user, and the overlay image of the "Wish I Was Here Logo".
        this.addImage(sketch.compiledImage, sketch.iconCenterX, sketch.iconCenterY * 0.65, sketch.appWidth * 0.5, sketch.appHeight * 0.5, 1, sketch.compiledImageOrientation);
    }
}