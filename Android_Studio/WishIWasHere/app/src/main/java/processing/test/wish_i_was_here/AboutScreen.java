package processing.test.wish_i_was_here;

// Importing the Processing library, so this class can declare variables using Processing specific
// datatypes i.e. PImage objects.
import processing.core.*;

// This class extends from the Screen class, which in turn extends from the Rectangle class, and so
// inherits methods and variables from both of these classes. This screen is displayed when a user
// wants to read more about the app. This screen was a requirement based on the Google attribution
// requirements, which specify that their attribution must be clearly visible with a "legal" or "about"
// page, accessible from the main app.
public class AboutScreen extends Screen {

    // Creating a private variable to store the instance of the main sketch which will be passed into
    // the constructors of this class when they are initialised. The purpose of this variable is so that
    // we can access the Processing library, along with other global methods and variables of the main
    // sketch class, from within this class. Every reference to a Processing method/variable, or a public
    // method/variable of the main sketch, must be prefixed with this object while within this class.
    private Sketch sketch;

    private String aboutText = "We are 3rd Year Creative Multimedia students in Limerick Institute of Technology, Clonmel. As part of a team project, we decided to design, create and build an application that will transport you to different locations around the world. Our aim is to make the world a little bit smaller, one click at a time. ";
    private String attributionHeading = "Attributions: ";
    //private String attributionText1 = "Google Maps Geolocation API, Copyright © 2016 , This program is licensed under a Creative Commons Attribution 3.0. It is attributed to Google Inc" + "/n/n" + "Google Street View Image API, Copyright © 2016 , This program is licensed under a Creative Commons Attribution 3.0. It is attributed to Google Inc" + "/n/n" + "Android Studio, Copyright © 2016 , This program is licensed under a Creative Commons Attribution 2.5 Generic. It is attributed to Google Inc and the Open Handset Alliance" + "/n/n" + "Ketai, Copyright © 2015, Licensed by Processing Foundation" + "/n/n" + "Processing, Copyright © 2004, Licensed by Processing Foundation" + "/n/n" + "Fabric.io, Copyright © 2016, Licensed by Twitter Inc" + "/n/n" + "ExpressJS, Copyright © 2016 StrongLoop, IBM, and other expressjs.com contributors. By the Node JS Foundation" + "/n/n" + "Jimp, Copyright © 2016 Licensed by MIT" + "/n/n" + "Onecolor, Copyright © 2011, Licensed by One.com";
    private String attributionText1 = "Google Maps Geolocation API, Copyright © 2016 , This program is licensed under a Creative Commons Attribution 3.0. It is attributed to Google Inc";
    private String attributionText2 = "Google Street View Image API, Copyright © 2016 , This program is licensed under a Creative Commons Attribution 3.0. It is attributed to Google Inc";
    private String attributionText3 = "Android Studio, Copyright © 2016 , This program is licensed under a Creative Commons Attribution 2.5 Generic. It is attributed to Google Inc and the Open Handset Alliance";
    private String attributionText4 = "Ketai, Copyright © 2015, Licensed by Processing Foundation";
    private String attributionText5 = "Processing, Copyright © 2004, Licensed by Processing Foundation";
    private String attributionText6 = "Fabric.io, Copyright © 2016, Licensed by Twitter Inc";
    private String attributionText7 = "ExpressJS, Copyright © 2016 StrongLoop, IBM, and other expressjs.com contributors. By the Node JS Foundation";
    private String attributionText8 = "Jimp, Copyright © 2016 Licensed by MIT";
    private String attributionText9 = "Onecolor, Copyright © 2011, Licensed by One.com";


    private Icon[] pageIcons;
    public Boolean loaded = false;

    // Creating a public constructor for the AboutScreen class, so that
    // an instance of it can be declared in the main sketch
    public AboutScreen(Sketch _sketch) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch, _sketch.width / 2, _sketch.height / 2, _sketch.width, _sketch.height * 2, null);

        // Initialising this class's local sketch variable, with the instance which was passed to the
        // constructor of this class. The purpose of this variable is so that we can access the Processing
        // library, along with other global methods and variables of the main sketch class, from within
        // this class. Every reference to a Processing method/variable, or a public method/variable of
        // the main sketch, must be prefixed with this object while within this class.
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
        Icon homeIcon = new Icon(sketch, sketch.iconRightX, sketch.iconTopY, sketch.loadImage("homeIconImage.png"), "Home", false, "HomeScreen");
        Icon facebookIcon = new Icon(sketch, sketch.appWidth * 0.2, sketch.iconBottomY, sketch.loadImage("facebookAccountIconImage.png"), "Facebook", false, "https://www.facebook.com/wishiwashereapp");
        Icon twitterIcon = new Icon(sketch, sketch.appWidth * 0.5, sketch.iconBottomY, sketch.loadImage("twitterAccountIconImage.png"), "Twitter", false, "https://twitter.com/wishiwashere");
        Icon instagramIcon = new Icon(sketch, sketch.appWidth * 0.8, sketch.iconBottomY, sketch.loadImage("instagramAccountIconImage.png"), "Instagram", false, "https://www.instagram.com/wishiwashereapp/");

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {homeIcon, facebookIcon, twitterIcon, instagramIcon};
        pageIcons = allIcons;

        // Calling the setScreenIcons() method of this screen's super class (Screen). This passes
        // the temporary allIcons array to the screenIcons array of the Screen class so that they
        // can be looped through by the showScreen() method, and methods inherited from the Icon
        // class (such as showIcon and checkMouseOver) can be called on them from within this array.
        // This reduces the need for each screen to have to loop through it's icons, or call the
        // same method on multiple icons.
        this.setScreenIcons(allIcons);

        // Setting the title of this screen in this class's super class (Screen), so that it can be accessed
        // when showing the screen (i.e can be displayed as the header text of the page).
        this.setScreenTitle("About");
    }

    // Creating a public showScreen method, which is called by the draw() funciton whenever this
    // screen needs to be displayed
    public void showScreen() {
        if (!this.loaded) {
            // Resetting the position values of the element so on the screen every time the page is opened,
            // so that if a user leaves the screen half scrolled, it will still be reset upon their return
            this.setY(sketch.appHeight/2);
            this.getScreenIcons()[0].setY(sketch.iconTopY);
            this.getScreenIcons()[1].setY(sketch.iconBottomY);
            this.getScreenIcons()[2].setY(sketch.iconBottomY);
            this.getScreenIcons()[3].setY(sketch.iconBottomY);

            // Setting loaded to true, so that this block of code will only run once (each time this page
            // is opened). This value will be reset to false in the Icon class's checkMouseOver function,
            // when an icon that links to another page has been clicked.
            this.loaded = true;
            println("firstLoad");
        }

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's icons.
        // This method will then in turn call it's super class's (Rectangle) method, to generate the screen.
        // Calling this method after the above check has been done to see if the icons etc. on this screen have
        // been reset, so that the screen appears correctly, even on it's first load.
        this.drawScreen();

        this.addImage(sketch.loadImage("aboutPageTeamImage.jpg"), sketch.appWidth / 2, this.getY() + (sketch.appHeight * -0.25), sketch.appWidth * 0.7, sketch.appHeight * 0.2);

        sketch.rectMode(CORNER);
        sketch.textAlign(LEFT);
        sketch.textSize((float) (sketch.appWidth * 0.05));
        sketch.fill(0);
        sketch.text(aboutText, (float) (sketch.appWidth * 0.1), (float) (this.getY() + (sketch.appHeight * -0.1)), (float) (sketch.appWidth * 0.8), (float) (sketch.appHeight * 0.9));

        sketch.text("Attributions" + "\r\n\r\n"
                + attributionText1 + "\r\n\r\n"
                + attributionText2 + "\r\n\r\n"
                + attributionText3 + "\r\n\r\n"
                + attributionText4 + "\r\n\r\n"
                + attributionText5 + "\r\n\r\n"
                + attributionText6 + "\r\n\r\n"
                + attributionText7 + "\r\n\r\n"
                + attributionText8 + "\r\n\r\n"
                + attributionText9
                , (float)(sketch.appWidth * 0.1), (float)(this.getY()  + (sketch.appHeight * 0.48)), (float)(sketch.appWidth * 0.8), (float)(sketch.appHeight * 1.6));

        // Checking if the page is being scrolled
        if (sketch.mousePressed) {
            sketch.mouseClicked = false;
            // Calculating the amount scolled, based on the distance between the previous y position,
            // and the current y position. When the mouse is first pressed, the previous y position
            // is initialised (in the main sketch) but then while the mouse is held down, the previous
            // y position gets updated each time this function runs (so that the scrolling can occur
            // while the person is still moving their hand (and not just after they release the screen)
            float amountScrolled = dist(0, sketch.previousMouseY, 0, sketch.mouseY);

            Icon[] icons = this.getScreenIcons();

            // Looping through each of the page icons, which are only being stored in an array within
            // this class so that they can be looped through to be repositioned (i.e. in every other
            // screen, these icons would be stored only in the super class, and not directly accessible
            // within the individual screen classes
            for (int i = 0; i < icons.length; i++) {
                // Checking which direction the user scrolled
                if (sketch.previousMouseY > sketch.mouseY) {
                    // The user has scrolled UP
                    // Setting the y position of the icon to it's current position, minus the amount scrolled i.e.
                    // moving the icon up the screen
                    icons[i].setY(icons[i].getY() - amountScrolled);
                } else {
                    // The user has scrolled DOWN
                    // Checking if the screen's y position is less than or equal to half of the height i.e. is
                    // so that the screen cannot be down any further once you reach the top
                    if (this.getY()  <= (sketch.appHeight/2) - amountScrolled) {
                        // Setting the y position of the icon to it's current position, plus the amount scrolled i.e.
                        // moving the icon down the screen
                        icons[i].setY(icons[i].getY() + amountScrolled);
                    }
                }
            }

            // Checking which direction the user scrolled (the reason I have to do this seperatley from above is
            // that including these lines within the icons loop above makes these elements move faster than the
            // page icons
            if (sketch.previousMouseY > sketch.mouseY) {
                // The user has scrolled UP
                // Setting the screen's y postion to it's current y position, minus the amount scrolled
                this.setY(this.getY() - amountScrolled);
                // Setting the global positioning variable screenTitleY to be decremented by the amount scrolled. Note:
                // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
                // an icon's link is passed in to change a page)
                sketch.screenTitleY -= amountScrolled;
            } else {
                // The user has scrolled DOWN
                // Checking if the screen's y position is less than or equal to half of the height i.e. is
                // so that the screen cannot be down any further once you reach the top
                if (this.getY() <= (sketch.appHeight/2) - amountScrolled) {
                    // Setting the screen's y postion to it's current y position, plus the amount scrolled
                    this.setY(this.getY() + amountScrolled);
                    // Setting the global positioning variable screenTitleY to be incremented by the amount scrolled. Note:
                    // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
                    // an icon's link is passed in to change a page)
                    sketch.screenTitleY += amountScrolled;
                }
            }

            // Updating the previous mouse Y to be equal to the current mouse y, so that the next time this function is
            // called, the scrolling will be detected from this point i.e. so that scrolling appears continous, even if the
            // user keeps there finger/mouse held on the screen while moving up and down
            sketch.previousMouseY = sketch.mouseY;
        }
    }

}
