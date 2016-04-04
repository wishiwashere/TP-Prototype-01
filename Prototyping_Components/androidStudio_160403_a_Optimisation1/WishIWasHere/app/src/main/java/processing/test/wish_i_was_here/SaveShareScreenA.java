package processing.test.wish_i_was_here;

import processing.core.*;

public class SaveShareScreenA extends Screen {
    private Sketch sketch;

    // Creating a public constructor for the TemplateScreen class, so that
    // an instance of it can be declared in the main sketch
    public SaveShareScreenA(Sketch _sketch) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch);

        sketch = _sketch;

        // Creating the icon/s for this screen, using locally scoped variables, as these
        // icons will be only ever be referred to from the allIcons array. Setting their
        // x, and y, based on percentages of the width and height (where icon positioning variables
        // are used, these were defined in the main sketch. Not passing in any width or height, so as
        // to allow this icon to be set to the default size in the Icon class of the app . Passing
        // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
        // whether this name should be displayed on the icon or not. Finally, passing in a linkTo
        // value of the name of the screen they will later link to. The title arguments, as well
        // as the linkTo argument, are optional
        Icon twitterIcon = new Icon(sketch, sketch.iconCenterX * 1.45, sketch.iconCenterY, sketch.largeIconSize, sketch.largeIconSize, "twitterAccountIconImage.png", "Twitter", true, "Below", "_checkTwitterLogin");
        Icon saveIcon = new Icon(sketch, sketch.iconCenterX, sketch.iconCenterY * 1.5, sketch.largeIconSize, sketch.largeIconSize, "saveIconImage.png", "Save", true, "Below", "_keepImage");
        Icon cancelIcon = new Icon(sketch, sketch.appWidth * 0.3, sketch.iconBottomY, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "buttonImage.png", "Cancel", true, "Middle", "CameraLiveViewScreen");
        Icon nextIcon = new Icon(sketch, sketch.appWidth * 0.7, sketch.iconBottomY, sketch.appWidth * 0.4, sketch.appHeight * 0.08, "buttonImage.png", "Next", true, "Middle", "SaveShareScreenB");

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {twitterIcon, saveIcon, cancelIcon, nextIcon};

        // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
        // the temporary allIcons array to the screenIcons array of the Screen class so that they
        // can be looped through by the showScreen() method, and methods inherited from the Icon
        // class (such as showIcon and checkMouseOver) can be called on them from within this array.
        // This reduces the need for each screen to have to loop through it's icons, or call the
        // same method on multiple icons.
        this.setScreenIcons(allIcons);

        // Setting the title of this screen. The screenTitle variable was also declared in this
        // class's super class (Screen), so that it can be accessed when showing the screen
        // (i.e can be displayed as the header text of the page). If no screenTitle were set,
        // then no header text will appear on this page
        this.setScreenTitle("Save Share Screen A");
    }

    // Creating a public showScreen method, which is called by the draw() funciton whenever this
    // screen needs to be displayed
    public void showScreen() {

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
        // icons. This method will then in turn call it's super class's (Rectangle) method, to
        // generate the size and background of the screen
        this.drawScreen();

        this.addPImage(compiledImage, sketch.iconCenterX, sketch.iconCenterY * 0.5, sketch.appWidth * 0.3, sketch.appHeight * 0.3);
    }
}