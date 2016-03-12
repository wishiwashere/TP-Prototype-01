package com.pigottlaura.www.wishiwashere;

import processing.core.*;

public class ClickableElement extends Rectangle {
    private PApplet p;

    String elementTitle = "";

  /*-------------------------------------- Constructor() ------------------------------------------------*/

    // This constructor is used by icons
    public ClickableElement(PApplet _p, double x, double y, double w, double h, PImage img, String title) {
        super(_p, x, y, w, h, img);
        elementTitle = title;
        p = _p;
    }

    // This constructor is used by textInputs
    public ClickableElement(PApplet _p, double x, double y, double w, double h, int col, String title) {
        super(_p, x, y, w, h, col);
        elementTitle = title;
        p = _p;
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
        if ((p.mouseX > (this.getX() - (this.getWidth()/2))) &&
                (p.mouseX < (this.getX() + (this.getWidth()/2))) &&
                (p.mouseY > (this.getY() - (this.getHeight()/2))) &&
                (p.mouseY < (this.getY() + (this.getHeight()/2)))) {

            // Logging out the name of the element that was clicked on
            p.println(this.elementTitle + " was clicked");

            // Setting mousePressed back to false, so that if the user still has their
            // mouse pressed after the screen changes, this will not be considered
            // a new click (as otherwise they could inadvertantly click on another button)
            p.mousePressed = false;

            clickedOn = true;
        }
        return clickedOn;
    }
}