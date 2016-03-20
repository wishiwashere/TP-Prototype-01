package com.pigottlaura.www.wishiwashere;

import processing.core.*;

public class ClickableElement extends Rectangle {
    private Sketch sketch;

    String elementTitle = "";

  /*-------------------------------------- Constructor() ------------------------------------------------*/

    // This constructor is used by icons
    public ClickableElement(Sketch _sketch, double x, double y, double w, double h, PImage img, String title) {
        super(_sketch, x, y, w, h, img);
        this.elementTitle = title;
        sketch = _sketch;
    }

    // This constructor is used by textInputs
    public ClickableElement(Sketch _sketch, double x, double y, double w, double h, int col, String title) {
        super(_sketch, x, y, w, h, col);
        this.elementTitle = title;
        sketch = _sketch;
    }

  /*-------------------------------------- checkMouseOver() ------------------------------------------------*/

    protected Boolean checkMouseOver() {
        Boolean clickedOn = false;
        // Checking if the mouse (or finger) is over this icon (called by the Screen
        // class if a mouse event has occurred while this icon's screen is being
        // displayed. Since the icons are drawn from the center, a bit of additional
        // calculations are required to find out if the mouse was over them (i.e.
        // to see if the mouseX was over this icon, we first have to take half
        // the width from the x from the x postion, to get the furthest left point,
        // and then add half of the width to the x position of the icon, to get
        // it's furthest right point. The process is simular for determining the mouseY)
        if ((sketch.mouseX > (this.getX() - (this.getWidth()/2))) &&
                (sketch.mouseX < (this.getX() + (this.getWidth()/2))) &&
                (sketch.mouseY > (this.getY() - (this.getHeight()/2))) &&
                (sketch.mouseY < (this.getY() + (this.getHeight()/2)))) {

            // Logging out the name of the element that was clicked on
            sketch.println(this.elementTitle + " was clicked");

            // Setting mousePressed back to false, so that if the user still has their
            // mouse pressed after the screen changes, this will not be considered
            // a new click (as otherwise they could inadvertantly click on another button)
            sketch.mousePressed = false;

            clickedOn = true;
        }
        return clickedOn;
    }
}