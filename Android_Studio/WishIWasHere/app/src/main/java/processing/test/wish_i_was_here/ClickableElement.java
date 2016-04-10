package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.

import processing.core.*;

// This class extends from the Rectangle class, and so inherits the methods and variables this class.
// This class acts as a super class for all clickable elements in the app, such as TextInput and Icon
// classes, so they will all inherit the methods and variables from this class.
public class ClickableElement extends Rectangle {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this and all
    // other classes which extend from it.
    private Sketch sketch;

    // Creating a private variable to store the title of the element. This title will only be used for
    // logging out which element was clicked on
    private String elementTitle = "";

    /*-------------------------------------- Constructor() ------------------------------------------------*/
    // Full Constructor
    // This constructor is used by the Icon class
    public ClickableElement(Sketch _sketch, double x, double y, double w, double h, PImage img, String title) {

        // Calling this class's super class (Rectangle) to create an element with the specified x, y
        // width, height and background image. Also passing the instance of the Sketch class, which was
        // just passed to this constructor, so that the super class can also access the Processing library,
        // as well as the global methods and variables of the Sketch class.
        super(_sketch, x, y, w, h, img);

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this and all other classes
        // which extend from it.
        sketch = _sketch;


    }

    // Full Constructor
    // This constructor is used by the TextInput class
    public ClickableElement(Sketch _sketch, double x, double y, double w, double h, String title) {

        // Calling this class's super class (Rectangle) to create an element with the specified x, y
        // width and height. Also passing the instance of the Sketch class, which was just passed to
        // this constructor, so that the super class can also access the Processing library, as well
        // as the global methods and variables of the Sketch class.
        super(_sketch, x, y, w, h);

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this and all other classes
        // which extend from it.
        sketch = _sketch;

        // Setting the title of this element to be equal to the title passed in in the constructor. This
        // title will only be used for logging out which element was clicked on
        this.elementTitle = title;
    }

  /*-------------------------------------- checkMouseOver() ------------------------------------------------*/

    // Creating a protected checkMouseOver() method, which can only be called from descendants of this class.
    // The purpose of this method is that Icons, TextInputs or any other clickable elements in the app, do
    // not need to have the code to check if the mouse was over them when the click occurred, they can just
    // inherit this function and call it when needed. This method returns a boolean value, to indicate whether
    // or not the mouse was over this element when the click occurred.
    protected Boolean checkMouseOver() {

        // Creating a local variable to store the value that will be returned by this method. Defaulting this
        // to false, so that we assume the element was not clicked on, unless proven otherwise in this
        // method.
        Boolean clickedOn = false;

        // Checking if the mouseClicked variable from the main Sketch is currently true. This is a custom variable,
        // as the default mousePressed variable is being used to detect scrolling events, so could not be used here.
        if (sketch.mouseClicked) {

            // Checking if the mouse is over this icon (called by the Icon class if a mouse event has occurred
            // while this icon's screen is being displayed). Since the icons are drawn from the center, a bit
            // of additional calculations are required to find out if the mouse was over them (i.e. to see if
            // the mouseX was over this icon, we first have to take half the width from the x from the x position,
            // to get the furthest left point, and then add half of the width to the x position of the icon, to get
            // it's furthest right point. The process is similar for determining the mouseY)
            if ((sketch.mouseX > (this.getX() - (this.getWidth() / 2))) &&
                    (sketch.mouseX < (this.getX() + (this.getWidth() / 2))) &&
                    (sketch.mouseY > (this.getY() - (this.getHeight() / 2))) &&
                    (sketch.mouseY < (this.getY() + (this.getHeight() / 2)))) {

                // Logging out the name of the element that was clicked on
                println(this.elementTitle + " was clicked");

                // Setting mouseClicked back to false, so that if the user still has their
                // mouse down after the screen changes, this will not be considered
                // a new click (as otherwise they could inadvertently click on another button)
                sketch.mouseClicked = false;

                // Changing the value which will be returned from this function to true, so that
                // the caller of this function will know that this was the element which was clicked on
                clickedOn = true;
            }
        }

        // Returning the result from this function, which will be a boolean value of true or false, so that the caller
        // of this function will know whether this element was clicked on or not
        return clickedOn;
    }
}