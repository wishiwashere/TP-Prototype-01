package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.
import processing.core.*;

// This class extends from the Screen class, which in turn extends from the Rectangle class, and so
// inherits methods and variables from both of these classes. This screen is displayed when a user wants
// to log out of their Twitter account.
public class SocialMediaLogoutScreen extends Screen{

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

    // Creating a public constructor for the class so that an instance of it can be declared in the main sketch
    public SocialMediaLogoutScreen(Sketch _sketch){

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

        // Creating the icon/s for this screen, using locally scoped variables, as these icons will be only
        // ever be referred to from the allIcons array. Setting their x, and y, based on percentages of the
        // width and height (where icon positioning variables are used, these were defined in the main sketch.
        // Not passing in any width or height, so as to allow this icon to be set to the default size in the
        // Icon class of the app. Passing in a name for the icon, followed by a boolean to choose whether this
        // name should be displayed on the icon or not. Finally, passing in a linkTo value of the name of the
        // screen or function they will later link to.
        Icon noIcon = new Icon(sketch, sketch.appWidth * 0.3, sketch.iconBottomY, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "No", true, "Middle", "-returnTo");
        Icon yesIcon = new Icon(sketch, sketch.appWidth * 0.7, sketch.iconBottomY, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "Yes", true, "Middle", "-returnTo");

        // Creating a temporary allIcons array to store the icon created above, so that they can be
        // passed to the super class (Screen) and stored as the icons for this screen.
        Icon[] allIcons = {noIcon, yesIcon};

        // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
        // the temporary allIcons array to the screenIcons array of the Screen class so that they
        // can be looped through by the showScreen() method, and methods inherited from the Icon
        // class (such as showIcon and checkMouseOver) can be called on them from within this array.
        // This reduces the need for each screen to have to loop through it's icons, or call the
        // same method on multiple icons.
        this.setScreenIcons(allIcons);

        // Setting the title of this screen in this class's super class (Screen), so that it can be accessed
        // when showing the screen (i.e can be displayed as the header text of the page).
        this.setScreenTitle("Logout");
    }

    // Creating a public showScreen method, which is called by the draw() function whenever this
    // screen needs to be displayed
    public void showScreen(){

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's icons.
        // This method will then in turn call it's super class's (Rectangle) method, to generate the screen.
        this.drawScreen();

        // Calling the addText() method, which was inherited from the Rectangle class, to add text to the screen
        // which confirms the user's account name (which accessed from a static variable in the TwitterLoginActivity
        // class)
        this.addText("Are you sure you want \r\n to remove your \r\n@" + TwitterLoginActivity.twitterUserUsername + "\r\n Twitter account from \r\n our app?", sketch.iconCenterX, sketch.appHeight * 0.3);
    }
}