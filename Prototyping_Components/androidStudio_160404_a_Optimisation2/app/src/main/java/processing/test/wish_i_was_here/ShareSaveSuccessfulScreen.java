package processing.test.wish_i_was_here;

import processing.core.*;

public class ShareSaveSuccessfulScreen extends Screen {
    private Sketch sketch;

    private String methodUsed = "";

    // Creating a public constructor for the TemplateScreen class, so that
    // an instance of it can be declared in the main sketch
    public ShareSaveSuccessfulScreen(Sketch _sketch, PImage bgImage) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch, bgImage);

        sketch = _sketch;

        Icon shareImageToDeviceAppsIcon = new Icon(sketch, sketch.iconCenterX, sketch.iconCenterY * 1.6, sketch.largeIconSize, sketch.largeIconSize, sketch.shareImageToDeviceAppsImage, "Share", true, "Below", "_shareImageToDeviceApps");

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {shareImageToDeviceAppsIcon};

        this.setScreenIcons(allIcons);

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

    // Creating a public showScreen method, which is called by the draw() function whenever this
    // screen needs to be displayed
    public void showScreen() {

        if(sketch.imageSaved && sketch.imageShared){
            methodUsed = "shared & saved";
        } else if (sketch.imageSaved){
            methodUsed = "saved";
        } else if (sketch.imageShared){
            methodUsed = "shared";
        }

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
        // icons. This method will then in turn call it's super class's (Rectangle) method, to
        // generate the size and background of the screen
        this.drawScreen();
        this.addText("Your postcard", sketch.iconCenterX, sketch.appHeight * 0.1);
        this.addText("has been", sketch.iconCenterX, sketch.appHeight * 0.18);
        this.addText("successfully", sketch.iconCenterX, sketch.appHeight * 0.26);
        this.addText(methodUsed, sketch.iconCenterX, sketch.appHeight * 0.34);
        this.addImage(sketch.loadImage("sharingScreenImage.png"), sketch.appWidth/2, sketch.appHeight * 0.5, sketch.appWidth * 0.8, sketch.appWidth * 0.4);
    }
}