package processing.test.wish_i_was_here;

import processing.core.*;

public class LoadingScreen extends Screen {
    private Sketch sketch;

    // Creating a public constructor for the TemplateScreen class, so that
    // an instance of it can be declared in the main sketch
    public LoadingScreen(Sketch _sketch, String bgImageURL) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch, bgImageURL);

        sketch = _sketch;
    }

    // Creating a public showScreen method, which is called by the draw() funciton whenever this
    // screen needs to be displayed
    public void showScreen() {

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
        // icons. This method will then in turn call it's super class's (Rectangle) method, to
        // generate the size and background of the screen
        this.drawScreen();
    }
}