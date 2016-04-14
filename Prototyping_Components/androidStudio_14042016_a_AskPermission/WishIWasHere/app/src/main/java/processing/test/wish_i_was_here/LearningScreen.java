package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.
import processing.core.*;

// This class extends from the Screen class, which in turn extends from the Rectangle class, and so
// inherits methods and variables from both of these classes. This screen is displayed as an overlay
// on the CameraLiveViewScreen, when the user has learningModeOn enabled
public class LearningScreen extends Screen {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

    // Creating a Rectangle array to store the learning rectangles for this screen in
    private Rectangle[] allLearningRects = new Rectangle[5];

    // Creating a String array to store the text that will be displayed on the Rectangles in the
    // allLearningRects array
    private String[] allLearningRectsText = {
            "Save\r\nyour\r\nlocation",
            "Return to\r\nhome",
            "Move to\r\nview\r\nup/down",
            "Take a\r\npicture",
            "Switch\r\ncamera\r\nviews"
    };

    // Creating a string to store the line of text to explain to the user how to "look around" their environment
    private String movement = "Swipe left or right to move around your current location";

    // Creating two private float variables, to store the default with and height for each learning rectangle
    private float learningBoxWidth;
    private float learningBoxHeight;

    // Creating a public constructor for the class so that an instance of it can be declared in the main sketch
    public LearningScreen(Sketch _sketch) {

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

        // Calling the Rectangle class's setBackgroundColor() method, which this class had inherited through the
        // Screen class, so that the learning mode will have a semi-transparent grey fill as it's background
        this.setBackgroundColor(sketch.color(33, 33, 33, 100));

        // Initialising the default width and height of the learning rectangles, based on the devices width and height
        learningBoxWidth = sketch.appWidth / 4;
        learningBoxHeight = sketch.appHeight / 6;

        // Initialising a new Rectangle in each index positon of the allLearningRects array, passing in the
        // relevant positioning values. Where icon positioning variables are used, these were defined in the main sketch.
        allLearningRects[0] = new Rectangle(sketch, sketch.iconLeftX, sketch.iconTopY + sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[1] = new Rectangle(sketch, sketch.iconRightX, sketch.iconTopY + sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[2] = new Rectangle(sketch, sketch.iconLeftX, sketch.iconBottomY - sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[3] = new Rectangle(sketch, sketch.iconCenterX, sketch.iconBottomY - sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[4] = new Rectangle(sketch, sketch.iconRightX, sketch.iconBottomY - sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
    }

    // Creating a public showScreen method, which is called by the draw() function whenever this screen needs to be displayed
    public void showScreen() {

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's icons.
        // This method will then in turn call it's super class's (Rectangle) method, to generate the screen.
        super.show();

        // Looping through each of the learning Rectangles in the allLearningRects array, and adding them, along
        // with their string counterparts in the allLearningRectsText array, to the screen
        for (int i = 0; i < allLearningRects.length; i++) {

            // Setting the rotation of this rectangle to be equal to the device's current location (so that the learning
            // mode rectangle will rotate along with the icons they explain
            allLearningRects[i].setRotation(sketch.deviceOrientation);

            // Setting the background of each learning rectangle to be a semi-transparent white
            allLearningRects[i].setBackgroundColor(sketch.color(255, 255, 255, 100));

            // Calling the show() method (which was inherited from the Rectangle class) so that this Rectangle will
            // be displayed on screen
            allLearningRects[i].show();

            // Calling the addText method (which was also inherited from the Rectangle class), to add the rectangle's
            // text counterpart (from the allLearningRectsText array) to the rectangle. Passing in string of text,
            // as well as the x and y of the current rectangle
            allLearningRects[i].addText(allLearningRectsText[i], allLearningRects[i].getX(), allLearningRects[i].getY());
        }
    }
}
