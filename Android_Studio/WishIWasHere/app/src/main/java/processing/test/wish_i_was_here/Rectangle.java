package processing.test.wish_i_was_here;

import processing.core.*;

public class Rectangle extends Sketch {

    private Sketch sketch;

    // Creating private variables to store the properties of each object.
    // These variables are private so that they can only be accessed within
    // this class i.e. must be set through the constructor of the object, or
    // retrieved using the relevant get methods of this class
    private float rectX;
    private float rectY;
    private float rectWidth;
    private float rectHeight;
    private float rectRotation = 0;
    private String rectImageURL = null;
    private PImage rectBackgroundImg = null;
    private int rectBackgroundImgScaleX = 1;
    private int rectBackgroundImgRotate = 0;

  /*-------------------------------------- Constructor() ------------------------------------------------*/

    // This constructor is used by screens that want to accept all the defaults
    protected Rectangle(Sketch _sketch) {
        // If no x, y, or rotation are passed in, defaulting these to half of the sketch's
        // width and height (i.e. so that the rectangle will appear centered). If no width
        // and height are passed in, defaulting these to the current width and height of
        // the sketch. If no rotation value is specified, defaulting this value to 0, and
        // finally, if no color is specified, defaulting this to white
        this(_sketch, _sketch.appWidth/2, _sketch.appHeight/2, _sketch.appWidth, _sketch.appHeight, null);
    }

    // This constructor is used by screens that want to accept all the defaults, as well
    // as setting a background color
    protected Rectangle(Sketch _sketch, String imgURL) {
        // If no x, y passed in, defaulting these to half of the sketch's width and height
        // (i.e. so that the rectangle will appear centered). If no width and height are
        // passed in, defaulting these to the current width and height of the sketch. If
        // no image is specified, defaulting this null. Passing these default values, along
        // with the color that was passed in, to the main constructor of this class
        this(_sketch, _sketch.appWidth/2, _sketch.appHeight/2, _sketch.appWidth, _sketch.appHeight, imgURL);
    }

    // This constructor is used by text input boxes (TEMPORARILY)
    protected Rectangle(Sketch _sketch, double x, double y, double w, double h) {
        this(_sketch, x, y, w, h, null);
    }

    // This is the main constructor of this class, to which all other constructors pass
    // their values to be stored as the instance's properties
    protected Rectangle(Sketch _sketch, double x, double y, double w, double h, String imgURL) {
        sketch = _sketch;

        // Storing the values that are passed into the constructor in the private
        // variables of this class, so that they can be accessed by other functions
        // within this class, but not from anywhere outside of this class. Defaulting
        // the rotation of the instance to 0 (as it can be changed later using the
        // setRotation() method
        this.rectX = (float)(x);
        this.rectY = (float)(y);
        this.rectWidth = (float)(w);
        this.rectHeight = (float)(h);
        this.rectImageURL = imgURL;
    }

  /*-------------------------------------- show() ------------------------------------------------*/

    // Creating a method to redraw the object or "show" it on the screen (i.e so that only
    // descendants of this class can access
    protected void show() {

        // Storing the current state of the matrix
        sketch.pushMatrix();

        // Translating the position of the matrix to the specified x and y of the object
        sketch.translate(this.rectX, this.rectY);

        if(this.rectRotation != 0) {
            // Rotating the matrix by the specified rotation value of the object (which has been
            // stored as a radian value)
            sketch.rotate(this.rectRotation);
        }


        // Checking if a Background Image has been passed in
        if (this.rectBackgroundImg != null) {
            // Calling the addImage() method of the this class, to add the background image to the screen,
            // passing in the image, along with the current x, y, width and height of the instance,
            // so that the image will appear the full size of the object
            this.addPImage(this.rectBackgroundImg, 0, 0, this.rectWidth, this.rectHeight, this.rectBackgroundImgScaleX, this.rectBackgroundImgRotate);
        }

        // Checking if no image has been passed in
        if (this.rectImageURL == null) {
            sketch.fill(255);

            // Setting the drawing mode of the rectangle to be centered. This way, if a rotation has
            // been applied to the rectangle, it will pivot around it's center point
            sketch.rectMode(sketch.CENTER);

            // Drawing the rectangle with x and y values based on half of the width and height of
            // the object, so that it appears centered on it's point of origin. The actual position
            // on the screen will depend on the matrix's translation, as this will control where
            // the object is drawn
            sketch.rect(0, 0, this.rectWidth, this.rectHeight);
        } else {
            // Calling the addImage() method of the this class, to add the image to the screen,
            // passing in the image, along with the current x, y, width and height of the instance,
            // so that the image will appear the full size of the object
            this.addImage(rectImageURL, 0, 0, this.rectWidth, this.rectHeight);
        }
        // Restoring the matrix to it's previous state
        sketch.popMatrix();
    }

  /*-------------------------------------- addText() ------------------------------------------------*/

    // Partial addText() method that adds text to the object using the default text size
    protected void addText(String text, double textX, double textY) {
        // If no alignment specified, defaulting it to center on the x-axis. If no
        // text size specified, defaulting it to the defaultTextSize variable (as
        // defined in the main sketch. Passing these default values, along with the
        // specified text, x and y to the main addText() method
        this.addText(text, "CENTER", textX, textY, sketch.defaultTextSize);
    }

    // Partial addText() method that adds text to the object, using the default text size, and
    // changing the alignment on the x-axis to a specified alignment (as it will
    // default to CENTER otherwise
    protected void addText(String text, String align, double textX, double textY) {
        // If no text size specified, defaulting it to the defaultTextSize variable
        // (as defined in the main sketch. Passing these default values, along with
        // the specified text, alignment, x and y to the main addText() method
        this.addText(text, align, textX, textY, sketch.defaultTextSize);
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
        sketch.pushMatrix();

        // Translating the position of the matrix be equal to the x and y positions
        // passed into the function
        sketch.translate((float)(textX), (float)(textY));

        // Rotating the matrix by the currnet rotation value of this object (which has been
        // stored as a radian value)
        sketch.rotate(this.getRotation());

        if (align.equals("LEFT")) {
            // Setting the text align to Left on the x axis, and Center on the y so that
            // the text will be drawn from the center point of it's position on the left of
            // the page
            sketch.textAlign(sketch.LEFT, sketch.CENTER);
        } else if (align.equals("LEFT-TOP")) {
            // Setting the text align to center (on both the x and the y) so that
            // the text will be drawn from the center point of it's position on
            // the page
            sketch.textAlign(sketch.LEFT, sketch.TOP);
        } else {
            // Setting the text align to center (on both the x and the y) so that
            // the text will be drawn from the center point of it's position on
            // the page
            sketch.textAlign(sketch.CENTER, sketch.CENTER);
        }

        // Setting the text size to be responsive to the height of the app
        sketch.textSize((float) (textSize));

        // Setting the fill color for the text to black
        sketch.fill(0);

        // Adding the text to the screen, setting the x and y positions to 0,
        // as the actual position on the screen will depend on the matrix's translation,
        // as this will control where the text is drawn
        sketch.text(text, 0, 0);

        // Restoring the matrix to it's previous state
        sketch.popMatrix();
    }

  /*-------------------------------------- addImage() ------------------------------------------------*/

    // Partial addImage() method which is used by images that need to be displayed
    // centered in full resolution of the screen
    protected void addImage(String imgURL, int scaleX, int rotate) {
        // If no x, y, width or height passed in, defaulting the width and height to be
        // equal to that of the image (i.e. it's default resolution). Passing these
        // default values to the full addImage() method
        this.addImage(imgURL, sketch.appWidth / 2, sketch.appHeight / 2, 0, 0, scaleX, rotate);
    }

    // Partial addImage() method which is used by images that need to be displayed
    // at their default resolution
    protected void addImage(String imgURL, double imgX, double imgY) {
        // If no image width or height passed in, defaulting the width and height to be
        // equal to that of the image (i.e. it's default resolution). Passing these
        // default values, along with the image, x and y to the full addImage() method
        this.addImage(imgURL, imgX, imgY, 0, 0, 1, (int) (this.getRotation()));
    }

    // Partial addImage() method which is used by images that require a specific width and height
    // (Some of these values may have been defaulted by the partial addImage() method)
    protected void addImage(String imgURL, double imgX, double imgY, double imgWidth, double imgHeight) {
        // If no image width or height passed in, defaulting the width and height to be
        // equal to that of the image (i.e. it's default resolution). Passing these
        // default values, along with the image, x and y to the full addImage() method
        this.addImage(imgURL, imgX, imgY, imgWidth, imgHeight, 1, (int)(this.getRotation()));
    }

    // Full addImage() method which is used by images that require a specific width and height
    // (Some of these values may have been defaulted by the partial addImage() method)
    protected void addImage(String imgURL, double imgX, double imgY, double imgWidth, double imgHeight, int scaleX, int rotate) {
        // Storing the current state of the matrix
        sketch.pushMatrix();

        // Translating the position of the matrix be equal to the x and y positions
        // passed into the function
        sketch.translate((float) (imgX), (float) (imgY));

        // Flipping the image so that it better represents the camera it is using i.e.
        // on front facing cameras, the image will be flipped horizontally, so that things
        // don't appear in reverse.
        sketch.scale(scaleX, 1);

        // Rotating the matrix by the current rotation value of this screen (which has been
        // stored as a radian value)
        sketch.rotate(sketch.radians(rotate));

        // Setting the imageMode to center so that the image will be drawn from the center
        // point of it's position on the page
        sketch.imageMode(sketch.CENTER);

        PImage tempImage = sketch.loadImage(imgURL);

        imgWidth = imgWidth == 0 ? tempImage.width : imgWidth;
        imgHeight = imgHeight == 0 ? tempImage.height : imgHeight;

        // Adding the image to the screen, setting the x and y positions to 0,
        // as the actual position on the screen will depend on the matrix's translation,
        // as this will control where the text is drawn. Setting the width and height of the image
        // to be equal to the values passed into the function
        sketch.image(tempImage, 0, 0, (float)(imgWidth), (float)(imgHeight));

        // Restoring the matrix to it's previous state
        sketch.popMatrix();
    }

    protected void addPImage(PImage img, double imgX, double imgY, double imgWidth, double imgHeight) {
        this.addPImage(img, imgX, imgY, imgWidth, imgHeight, 1, 0);
    }

    protected void addPImage(PImage img, double imgX, double imgY, double imgWidth, double imgHeight, int scaleX, int rotate) {
        // Storing the current state of the matrix
        sketch.pushMatrix();

        // Translating the position of the matrix be equal to the x and y positions
        // passed into the function
        sketch.translate((float) (imgX), (float) (imgY));

        // Flipping the image so that it better represents the camera it is using i.e.
        // on front facing cameras, the image will be flipped horizontally, so that things
        // don't appear in reverse.
        sketch.scale(scaleX, 1);

        // Rotating the matrix by the current rotation value of this screen (which has been
        // stored as a radian value)
        sketch.rotate(sketch.radians(rotate));

        // Setting the imageMode to center so that the image will be drawn from the center
        // point of it's position on the page
        sketch.imageMode(sketch.CENTER);

        // Adding the image to the screen, setting the x and y positions to 0,
        // as the actual position on the screen will depend on the matrix's translation,
        // as this will control where the text is drawn. Setting the width and height of the image
        // to be equal to the values passed into the function
        sketch.image(img, 0, 0, (float)(imgWidth), (float)(imgHeight));

        // Restoring the matrix to it's previous state
        sketch.popMatrix();
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
        return this.rectWidth;
    }

    // Get method that returns the instance's height
    protected float getHeight() {
        return this.rectHeight;
    }

    // Get method that returns the instance's rotation
    protected float getRotation() {
        return this.rectRotation;
    }

    protected void setImage(String imgURL) {
        this.rectImageURL = imgURL;
    }

    // Set method that sets the rotation of instance
    protected void setRotation(int r) {
        rectRotation = sketch.radians(r);
    }

    protected void setBackgroundImgScaleX(int sX) {
        if (sX == 1 || sX == -1) {
            this.rectBackgroundImgScaleX = sX;
        }
    }

    protected void addBackgroundImage(PImage bgImg, float w, float h, int scaleX, int rotate) {
        this.rectBackgroundImg = bgImg;
        this.rectWidth = w;
        this.rectHeight = h;
        this.rectBackgroundImgScaleX =  scaleX;
        this.rectBackgroundImgRotate = rotate;
    }

    public PImage getBackgroundImage() {
        return this.rectBackgroundImg;
    }
}