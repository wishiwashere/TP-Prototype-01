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

    String elementTitle = "";

  /*-------------------------------------- Constructor() ------------------------------------------------*/

    // This constructor is used by icons
    public ClickableElement(Sketch _sketch, double x, double y, double w, double h, PImage img, String title) {
        super(_sketch, x, y, w, h, img);
        this.elementTitle = title;

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this and all other classes
        // which extend from it.
        sketch = _sketch;
    }

    // This constructor is used by textInputs
    public ClickableElement(Sketch _sketch, double x, double y, double w, double h, String title) {
        super(_sketch, x, y, w, h);
        this.elementTitle = title;

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this and all other classes
        // which extend from it.
        sketch = _sketch;
    }

  /*-------------------------------------- checkMouseOver() ------------------------------------------------*/

    protected Boolean checkMouseOver() {
        Boolean clickedOn = false;
        if(sketch.mouseClicked) {
            // Checking if the mouse (or finger) is over this icon (called by the Screen
            // class if a mouse event has occurred while this icon's screen is being
            // displayed. Since the icons are drawn from the center, a bit of additional
            // calculations are required to find out if the mouse was over them (i.e.
            // to see if the mouseX was over this icon, we first have to take half
            // the width from the x from the x postion, to get the furthest left point,
            // and then add half of the width to the x position of the icon, to get
            // it's furthest right point. The process is simular for determining the mouseY)
            if ((sketch.mouseX > (this.getX() - (this.getWidth() / 2))) &&
                    (sketch.mouseX < (this.getX() + (this.getWidth() / 2))) &&
                    (sketch.mouseY > (this.getY() - (this.getHeight() / 2))) &&
                    (sketch.mouseY < (this.getY() + (this.getHeight() / 2)))) {

                // Logging out the name of the element that was clicked on
                println(this.elementTitle + " was clicked");

                // Setting mousePressed back to false, so that if the user still has their
                // mouse pressed after the screen changes, this will not be considered
                // a new click (as otherwise they could inadvertantly click on another button)
                sketch.mouseClicked = false;

                clickedOn = true;
            }
        }
        return clickedOn;
    }
}