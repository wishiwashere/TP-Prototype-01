package processing.test.wish_i_was_here;

import processing.core.*;

public class SaveShareScreenB extends Screen {
    private Sketch sketch;

    public TextInput messageInput;

    // Creating a public constructor for the TemplateScreen class, so that
    // an instance of it can be declared in the main sketch
    public SaveShareScreenB(Sketch _sketch, PImage bgImage) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch, bgImage);

        sketch = _sketch;

        messageInput = new TextInput(sketch, sketch.iconCenterX, sketch.iconCenterY * 0.98, sketch.appWidth * 0.8, sketch.appHeight * 0.2, _sketch.color(255, 255, 255), "messageInput", "LEFT-TOP");

        // Creating the icon/s for this screen, using locally scoped variables, as these
        // icons will be only ever be referred to from the allIcons array. Setting their
        // x, and y, based on percentages of the width and height (where icon positioning variables
        // are used, these were defined in the main sketch. Not passing in any width or height, so as
        // to allow this icon to be set to the default size in the Icon class of the app . Passing
        // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
        // whether this name should be displayed on the icon or not. Finally, passing in a linkTo
        // value of the name of the screen they will later link to. The title arguments, as well
        // as the linkTo argument, are optional
        Icon cancelIcon = new Icon(sketch, sketch.appWidth * 0.3, sketch.iconCenterY * 1.3, sketch.appWidth * 0.4, sketch.appHeight * 0.08, sketch.buttonImage, "Cancel", true, "Middle", "SaveShareScreenA");
        //Icon shareIcon = new Icon(p, appWidth * 0.7, iconCenterY * 1.3, appWidth * 0.4, appHeight * 0.08, buttonImage, "Share", true, "Middle", "SharingScreen");
        Icon shareIcon = new Icon(sketch, sketch.appWidth * 0.7, sketch.iconCenterY * 1.3, sketch.appWidth * 0.4, sketch.appHeight * 0.08, sketch.buttonImage, "Share", true, "Middle", "_sendTweet");

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {cancelIcon, shareIcon};

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
        this.setScreenTitle("Save Share Screen B");
    }

    // Creating a public showScreen method, which is called by the draw() funciton whenever this
    // screen needs to be displayed
    public void showScreen() {

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
        // icons. This method will then in turn call it's super class's (Rectangle) method, to
        // generate the size and background of the screen
        this.drawScreen();

        this.addImage(sketch.compiledImage, sketch.iconCenterX, sketch.iconCenterY * 0.4, sketch.appWidth * 0.8, sketch.appWidth * 0.6);

        messageInput.showTextInput();
    }
}