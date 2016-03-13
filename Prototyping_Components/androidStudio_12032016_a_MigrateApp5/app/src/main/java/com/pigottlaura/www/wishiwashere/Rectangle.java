package com.pigottlaura.www.wishiwashere;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.*;

public class Rectangle extends Sketch {

    private PApplet p;

    // Creating private variables to store the properties of each object.
    // These variables are private so that they can only be accessed within
    // this class i.e. must be set through the constructor of the object, or
    // retrieved using the relevant get methods of this class
    private float rectX;
    private float rectY;
    private float rectWidth;
    private float rectHeight;
    private int rectCol;
    private float rectRotation;
    private PImage rectImage = null;
    public PImage rectBackgroundImg = null;
    private int rectBackgroundImgScaleX = 1;
    private int rectBackgroundImgRotate = 0;

  /*-------------------------------------- Constructor() ------------------------------------------------*/

    // This constructor is used by screens that want to accept all the defaults
    protected Rectangle(PApplet _p) {
        // If no x, y, or rotation are passed in, defaulting these to half of the sketch's
        // width and height (i.e. so that the rectangle will appear centered). If no width
        // and height are passed in, defaulting these to the current width and height of
        // the sketch. If no rotation value is specified, defaulting this value to 0, and
        // finally, if no color is specified, defaulting this to white
        this(_p, _p.width/2, _p.height/2, _p.width, _p.height, 0xFFFFFF, null);
    }

    // This constructor is used by screens that want to accept all the defaults, as well
    // as setting a background image
    protected Rectangle(PApplet _p, PImage img) {
        // If no x, y passed in, defaulting these to half of the sketch's width and height
        // (i.e. so that the rectangle will appear centered). If no width and height are
        // passed in, defaulting these to the current width and height of the sketch. If
        // no color is specified, defaulting this to white. Passing these default values,
        // along with the image that was passed in, to the main constructor of this class
        this(_p, _p.width/2, _p.height/2, _p.width, _p.height, 0xFFFFFF, img);
    }

    // This constructor is used by screens that want to accept all the defaults, as well
    // as setting a background color
    protected Rectangle(PApplet _p, int col) {
        // If no x, y passed in, defaulting these to half of the sketch's width and height
        // (i.e. so that the rectangle will appear centered). If no width and height are
        // passed in, defaulting these to the current width and height of the sketch. If
        // no image is specified, defaulting this null. Passing these default values, along
        // with the color that was passed in, to the main constructor of this class
        this(_p, _p.width/2, _p.height/2, _p.width, _p.height, col, null);
    }

    // This constructor is used by icons that do not link to anything, and that
    // want to have an image as their background
    protected Rectangle(PApplet _p, double x, double y, double w, double h, PImage img) {
        // If no color passed in, defaulting it to white and then passing this default value,
        // along with the x, y, width, height and image that was passed in, to the main
        // constructor of this class
        this(_p, x, y, w, h, 0xFFFFFF, img);
    }

    // This constructor is used by text input boxes (TEMPORARILY)
    protected Rectangle(PApplet _p, double x, double y, double w, double h, int col) {
        this(_p, x, y, w, h, col, null);
    }

    // This is the main constructor of this class, to which all other constructors pass
    // their values to be stored as the instance's properties
    protected Rectangle(PApplet _p, double x, double y, double w, double h, int col, PImage img) {
        p = _p;

        // Storing the values that are passed into the constructor in the private
        // variables of this class, so that they can be accessed by other functions
        // within this class, but not from anywhere outside of this class. Defaulting
        // the rotation of the instance to 0 (as it can be changed later using the
        // setRotation() method
        rectX = (float)(x);
        rectY = (float)(y);
        rectWidth = (float)(w);
        p.println("RectWidth = " + rectWidth);
        rectHeight = (float)(h);
        rectCol = p.color(col);
        rectImage = img;
    }

  /*-------------------------------------- show() ------------------------------------------------*/

    // Creating a method to redraw the object or "show" it on the screen (i.e so that only
    // descendants of this class can access
    protected void show() {
        if (rectCol != 0xFFFFFF) {
            // Storing the current state of the matrix
            p.pushMatrix();

            // Translating the position of the matrix to the specified x and y of the object
            p.translate(rectX, rectY);

            // Rotating the matrix by the specified rotation value of the object (which has been
            // stored as a radian value)
            p.rotate(rectRotation);
            // Setting the fill colour of the object to the value specified
            p.fill(rectCol);

            p.noStroke();

            // Setting the drawing mode of the rectangle to be centered. This way, if a rotation has
            // been applied to the rectangle, it will pivot around it's center point
            p.rectMode(CENTER);

            // Drawing the rectangle with x and y values based on half of the width and height of
            // the object, so that it appears centered on it's point of origin. The actual position
            // on the screen will depend on the matrix's translation, as this will control where
            // the object is drawn
            p.rect(0, 0, rectWidth, rectHeight);

            // Restoring the matrix to it's previous state
            p.popMatrix();
        }

        // Checking if a Background Image has been passed in
        if (rectBackgroundImg != null) {
            // Calling the addImage() method of the this class, to add the background image to the screen,
            // passing in the image, along with the current x, y, width and height of the instance,
            // so that the image will appear the full size of the object
            this.addImage(rectBackgroundImg, rectX, rectY, rectWidth, rectHeight, rectBackgroundImgScaleX, rectBackgroundImgRotate);
        }

        // Checking if an image has been passed in
        if (rectImage != null) {
            // Calling the addImage() method of the this class, to add the image to the screen,
            // passing in the image, along with the current x, y, width and height of the instance,
            // so that the image will appear the full size of the object
            this.addImage(rectImage, rectX, rectY, rectWidth, rectHeight);
        }

    }

  /*-------------------------------------- addText() ------------------------------------------------*/

    // Partial addText() method that adds text to the object using the default text size
    protected void addText(String text, double textX, double textY) {
        // If no alignment specified, defaulting it to center on the x-axis. If no
        // text size specified, defaulting it to the defaultTextSize variable (as
        // defined in the main sketch. Passing these default values, along with the
        // specified text, x and y to the main addText() method
        this.addText(text, "CENTER", textX, textY, defaultTextSize);
    }

    // Partial addText() method that adds text to the object, using the default text size, and
    // changing the alignment on the x-axis to a specified alignment (as it will
    // default to CENTER otherwise
    protected void addText(String text, String align, double textX, double textY) {
        // If no text size specified, defaulting it to the defaultTextSize variable
        // (as defined in the main sketch. Passing these default values, along with
        // the specified text, alignment, x and y to the main addText() method
        this.addText(text, align, textX, textY, defaultTextSize);
    }

    // Partial addText() method that adds text to the object, with a specific text size
    protected void addText(String text, double textX, double textY, double textSize) {
        // If no alignment specified, defaulting it to center on the x-axis. Passing
        // this default value, along with the specified text, x, y and text size to
        // the main addText() method
        this.addText(text, "CENTER", textX, textY, textSize);
    }

    // Full addText() method, which takes in the values specified (some of which may have
    // been defaulted by the partial addText() methods above)
    protected void addText(String text, String align, double textX, double textY, double textSize) {
        // Storing the current state of the matrix
        p.pushMatrix();

        // Translating the position of the matrix be equal to the x and y positions
        // passed into the function
        p.translate((float)(textX), (float)(textY));

        // Rotating the matrix by the currnet rotation value of this object (which has been
        // stored as a radian value)
        p.rotate(this.getRotation());

        if (align.equals("LEFT")) {
            // Setting the text align to Left on the x axis, and Center on the y so that
            // the text will be drawn from the center point of it's position on the left of
            // the page
            p.textAlign(LEFT, CENTER);
        } else if (align.equals("LEFT-TOP")) {
            // Setting the text align to center (on both the x and the y) so that
            // the text will be drawn from the center point of it's position on
            // the page
            p.textAlign(LEFT, TOP);
        } else {
            // Setting the text align to center (on both the x and the y) so that
            // the text will be drawn from the center point of it's position on
            // the page
            p.textAlign(CENTER, CENTER);
        }

        // Setting the text size to be responsive to the height of the app
        p.textSize((float)(textSize));

        // Setting the fill color for the text to black
        p.fill(0);

        // Adding the text to the screen, setting the x and y positions to 0,
        // as the actual position on the screen will depend on the matrix's translation,
        // as this will control where the text is drawn
        p.text(text, 0, 0);

        // Restoring the matrix to it's previous state
        p.popMatrix();
    }

  /*-------------------------------------- addImage() ------------------------------------------------*/

    // Partial addImage() method which is used by images that need to be displayed
    // centered in full resolution of the screen
    protected void addImage(PImage img, int scaleX, int rotate) {
        // If no x, y, width or height passed in, defaulting the width and height to be
        // equal to that of the image (i.e. it's default resolution). Passing these
        // default values to the full addImage() method
        this.addImage(img, appWidth/2, appHeight/2, img.width, img.height, scaleX, rotate);
    }

    // Partial addImage() method which is used by images that need to be displayed
    // at their default resolution
    protected void addImage(PImage img, double imgX, double imgY) {
        // If no image width or height passed in, defaulting the width and height to be
        // equal to that of the image (i.e. it's default resolution). Passing these
        // default values, along with the image, x and y to the full addImage() method
        this.addImage(img, imgX, imgY, img.width, img.height, 1, (int)this.getRotation());
    }

    // Partial addImage() method which is used by images that require a specific width and height
    // (Some of these values may have been defaulted by the partial addImage() method)
    protected void addImage(PImage img, double imgX, double imgY, double imgWidth, double imgHeight) {
        // If no image width or height passed in, defaulting the width and height to be
        // equal to that of the image (i.e. it's default resolution). Passing these
        // default values, along with the image, x and y to the full addImage() method
        this.addImage(img, imgX, imgY, imgWidth, imgHeight, 1, (int)this.getRotation());
    }

    // Full addImage() method which is used by images that require a specific width and height
    // (Some of these values may have been defaulted by the partial addImage() method)
    protected void addImage(PImage img, double imgX, double imgY, double imgWidth, double imgHeight, int scaleX, int rotate) {
        // Storing the current state of the matrix
        p.pushMatrix();

        // Translating the position of the matrix be equal to the x and y positions
        // passed into the function
        p.translate((float)(imgX), (float)(imgY));

        // Flipping the image so that it better represents the camera it is using i.e.
        // on front facing cameras, the image will be flipped horizontally, so that things
        // don't appear in reverse.
        p.scale(scaleX, 1);

        // Rotating the matrix by the current rotation value of this screen (which has been
        // stored as a radian value)
        p.rotate(radians(rotate));

        // Setting the imageMode to center so that the image will be drawn from the center
        // point of it's position on the page
        p.imageMode(CENTER);

        // Adding the image to the screen, setting the x and y positions to 0,
        // as the actual position on the screen will depend on the matrix's translation,
        // as this will control where the text is drawn. Setting the width and height of the image
        // to be equal to the values passed into the function
        p.image(img, 0, 0, (float)(imgWidth), (float)(imgHeight));

        // Restoring the matrix to it's previous state
        p.popMatrix();
    }

  /*-------------------------------------- get() and set() ------------------------------------------------*/

    // Get method that returns the instance's x position
    protected float getX() {
        return rectX;
    }

    // Get method that returns the instance's y position
    protected float getY() {
        return rectY;
    }

    protected void setY(float y) {
        //if((y < appHeight/2) && (y > -appHeight * 0.75)){
        rectY = y;
        //}
    }

    // Get method that returns the instance's width
    public float getWidth() {
        return rectWidth;
    }

    // Get method that returns the instance's height
    protected float getHeight() {
        return rectHeight;
    }

    // Get method that returns the instance's rotation
    protected float getRotation() {
        return rectRotation;
    }

    protected void setImage(PImage img) {
        if (img.width == this.rectImage.width) {
            rectImage = img;
        }
    }

    // Set method that sets the rotation of instance
    protected void setRotation(int r) {
        rectRotation = radians(r);
    }

    protected void setBackgroundImgScaleX(int sX) {
        if (sX == 1 || sX == -1) {
            rectBackgroundImgScaleX = sX;
        }
    }

    protected void addBackgroundImage(PImage bgImg, float w, float h, int scaleX, int rotate) {
        rectBackgroundImg = bgImg;
        rectWidth = w;
        rectHeight = h;
        rectBackgroundImgScaleX =  scaleX;
        rectBackgroundImgRotate = rotate;
    }

    public PImage getBackgroundImage() {
        return rectBackgroundImg;
    }
}