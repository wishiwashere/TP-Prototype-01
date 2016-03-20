package com.pigottlaura.www.wishiwashere;

import processing.core.*;

public class SearchUnsuccessfulScreen extends Screen {
    private Sketch sketch;

    // Creating a public constructor for the SearchFailedScreen class, so that
    // an instance of it can be declared in the main sketch
    public SearchUnsuccessfulScreen(Sketch _sketch, PImage bgImage) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch, bgImage);

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
        Icon homeIcon = new Icon(sketch, sketch.iconRightX, sketch.iconTopY, sketch.homeIconImage, "Home", false, "HomeScreen");
        Icon searchTravelIcon = new Icon(sketch, sketch.appWidth * 0.3, sketch.largeIconBottomY, sketch.largeIconSize, sketch.largeIconSize, sketch.searchPageIconImage, "Search Again", true, "Below", "SearchScreen");
        Icon randomTravelIcon = new Icon(sketch, sketch.appWidth * 0.7, sketch.largeIconBottomY, sketch.largeIconSize, sketch.largeIconSize, sketch.randomPageIconImage, "Random", true, "Below", "_getRandomLocation");

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {homeIcon, searchTravelIcon, randomTravelIcon};

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
        this.setScreenTitle("");
    }

    // Creating a public showScreen method, which is called by the draw() funciton whenever this
    // screen needs to be displayed
    public void showScreen() {

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
        // icons. This method will then in turn call it's super class's (Rectangle) method, to
        // generate the size and background of the screen
        this.drawScreen();

        this.addText("We're sorry :(", sketch.iconCenterX, sketch.appHeight * 0.1);
        this.addText("We could not", sketch.iconCenterX, sketch.appHeight * 0.18);
        this.addText("find what you", sketch.iconCenterX, sketch.appHeight * 0.26);
        this.addText("were looking for...", sketch.iconCenterX, sketch.appHeight * 0.34);

        // Calling the super class's super class (Rectangle) to add an image to the screen, passing
        // in the image, x, y, width and height
        this.addImage(sketch.loadImage("searchUnsuccessfulScreenImage.png"), sketch.iconCenterX, sketch.appHeight * 0.55, sketch.appWidth * 0.8, sketch.appWidth * 0.4);
    }
}