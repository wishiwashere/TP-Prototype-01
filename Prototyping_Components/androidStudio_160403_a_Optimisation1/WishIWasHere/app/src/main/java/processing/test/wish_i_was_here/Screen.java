package processing.test.wish_i_was_here;

import processing.core.*;

public class Screen extends Rectangle {
    private Sketch sketch;

    // Declaring the screenIcons array, which will be used to store the
    // icons from each screen. This array will be initialised through the
    // setScreenIcons() method, where each screen will pass in an array
    // or the icons that are present on it's screen. The purpose of storing
    // this array within the Screen class, as opposed to in each instance's
    // own class, is that now we can deal with looping through, and calling
    // methods on multiple icons of a screen only when their screen is being
    // shown i.e. when showScreen() is being called
    private Icon[] screenIcons = {};

    // Creating and initialising the screenTitle property of the screen to be
    // equal to an empty string. Each instance of the Screen class can then
    // use the setScreenTitle() method to specify a custom title, which
    // will then be used as the header text on that instance of the screen.
    // If no title is specified, then header text will not be added to the
    // screen
    public String screenTitle = "";

    // Creating protected constructors for the Screen class, so that they can
    // only be accessed by classes which extend from this class

  /*-------------------------------------- Constructor() ------------------------------------------------*/

    protected Screen(Sketch _sketch) {
        // Calling this class's super class (Screen) to create a screen using
        // the default settings
        super(_sketch);

        sketch = _sketch;
    }

    protected Screen(Sketch _sketch, String imgURL) {
        // Calling this class's super class (Screen) to create a screen using
        // the default settings, along with setting the colour as specified
        super(_sketch, imgURL);

        sketch = _sketch;
    }

    protected Screen(Sketch _sketch, double x, double y, double w, double h, String imgURL) {
        super(_sketch, x, y, w, h, imgURL);

        sketch = _sketch;
    }

  /*-------------------------------------- drawScreen() ------------------------------------------------*/

    // Creating a public method so that this screen can be displayed from
    // within the main sketch
    public void drawScreen() {
        // Calling the show() method (as inherited from the Rectangle class)
        // to display this screen's background
        this.show();

        // Checking if this screen has been given a title (i.e. if the contents
        // of the screenTitle is at least one character long
        if (this.screenTitle.length() > 0)
        {
            sketch.fill(0);
            this.addText(this.screenTitle, sketch.appWidth/2, sketch.screenTitleY, sketch.appHeight * 0.08);
        }

        // Checking if this screen has any icons to be displayed
        if (this.screenIcons.length > 0)
        {
            // Looping through each of the screen's icons
            for (int i = 0; i < this.screenIcons.length; i++) {

                // Calling the showIcon() method (as inherited from the Icon class)
                // to display the icon on screen
                this.screenIcons[i].showIcon();

                // Checking if the mouse is currently pressed i.e. if a click has
                // been detected
                if (sketch.mousePressed)
                {
                    // Calling the checkMouseOver() method (as inherited from the Icon
                    // class, to see if the mouse was over this icon when it was clicked
                    this.screenIcons[i].checkMouseOver();
                }
            }
        }
    }

  /*-------------------------------------- get() and set() ------------------------------------------------*/

    protected void setScreenTitle(String title) {
        sketch.println("FAV - Setting Screen Title to " + title);
        // Setting the screenTitle to the title passed in by each screen. If no
        // title is passed, this variable has already been initialised to an
        // empty string above
        this.screenTitle = title;
    }

    protected Icon[] getScreenIcons() {
        return this.screenIcons;
    }

    protected void setScreenIcons(Icon[] icons) {
        // Initialising the screenIcons array with the contents from the allIcons
        // array that each screen will pass in
        this.screenIcons = icons;
    }
}
