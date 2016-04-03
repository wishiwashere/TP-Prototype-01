package processing.test.wish_i_was_here;

import processing.core.*;

public class SharingScreen extends Screen {
    private Sketch sketch;

    // Creating a public constructor for the TemplateScreen class, so that
    // an instance of it can be declared in the main sketch
    public SharingScreen(Sketch _sketch, PImage bgImage) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch, bgImage);

        sketch = _sketch;

        // Setting the title of this screen. The screenTitle variable was also declared in this
        // class's super class (Screen), so that it can be accessed when showing the screen
        // (i.e can be displayed as the header text of the page). If no screenTitle were set,
        // then no header text will appear on this page
        this.setScreenTitle("Sharing...");
    }

    // Creating a public showScreen method, which is called by the draw() funciton whenever this
    // screen needs to be displayed
    public void showScreen() {

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
        // icons. This method will then in turn call it's super class's (Rectangle) method, to
        // generate the size and background of the screen
        this.drawScreen();

        this.addImage(sketch.sharingScreenImage, sketch.appWidth/2, sketch.appHeight/2, sketch.appWidth * 0.8, sketch.appWidth * 0.4);
    }
}