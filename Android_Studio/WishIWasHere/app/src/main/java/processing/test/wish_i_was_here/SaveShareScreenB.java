package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.
import processing.core.*;

// This class extends from the Screen class, which in turn extends from the Rectangle class, and so
// inherits methods and variables from both of these classes. This screen is displayed when a user
// decides to share their image to Twitter, and allows them to add a message to the Tweet.
public class SaveShareScreenB extends Screen {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

    // Creating a private TextInput variable, to create a new TextInput within this screen (for users to
    // enter to enter a message to send with their tweet. This object will never be directly accessed by other
    // classes from within this class, but instead will be passed into the global currentTextInput variable
    // in the main Sketch class, when it is click on, by the TextInput class's showTextInput() method.
    public TextInput messageInput;

    // Creating a public constructor for the class so that an instance of it can be declared in the main sketch
    public SaveShareScreenB(Sketch _sketch) {

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

        // Initialising the TextInput variable, passing in an x, y, width and height. Also passing in the
        // title of this TextInput as well as the text alignment that will be used to display the text
        // within it.
        messageInput = new TextInput(sketch, sketch.iconCenterX, sketch.iconCenterY * 1.07, sketch.appWidth * 0.8, sketch.appHeight * 0.3, "messageInput", "LEFT-TOP");

        // Setting the maximum amount of characters allowed in this TextInput, so that the user's tweet
        // will not exceed the maximum limit specified by Twitter (as the tweet will also include #WishIWasHere,
        // we are accounting for this as well)
        messageInput.setMaxTextLength(126);

        // Creating the icon/s for this screen, using locally scoped variables, as these icons will be only
        // ever be referred to from the allIcons array. Setting their x, and y, based on percentages of the
        // width and height (where icon positioning variables are used, these were defined in the main sketch.
        // Not passing in any width or height, so as to allow this icon to be set to the default size in the
        // Icon class of the app. Passing in a name for the icon, followed by a boolean to choose whether this
        // name should be displayed on the icon or not. Finally, passing in a linkTo value of the name of the
        // screen or function they will later link to.
        Icon cancelIcon = new Icon(sketch, sketch.appWidth * 0.3, sketch.iconCenterY * 1.45, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "Cancel", true, "Middle", "SaveShareScreenA");
        Icon shareIcon = new Icon(sketch, sketch.appWidth * 0.7, sketch.iconCenterY * 1.45, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "Share", true, "Middle", "_sendTweet");

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {cancelIcon, shareIcon};

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

        // Adding this screen's private searchingImage, using the addImage() method, as inherited from the
        // Rectangle class, so it will appear as part of this screen. Calculating the x, y, width and height
        // based on the current width and height of the device this app is running on.
        this.addImage(sketch.compiledImage, sketch.iconCenterX, sketch.iconCenterY * 0.4, sketch.appWidth * 0.8, sketch.appWidth * 0.6);

        // Displaying the text input, along with it's value, on screen by calling the showTextInput() method
        // of the TextInput class.
        messageInput.showTextInput();
    }
}

