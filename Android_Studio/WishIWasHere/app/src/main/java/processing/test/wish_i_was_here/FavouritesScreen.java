package processing.test.wish_i_was_here;

import java.util.ArrayList;

import processing.core.*;

public class FavouritesScreen extends Screen {
    private Sketch sketch;

    // Creating a private array of all the favourite locations we want to display
    // in the favourites screen. The data before the "@" represents the location's title
    // while the data after is used to generate a URL to request this location's Google
    // Street View Image (in this class's showScreen() method). (Note - this is a naming
    // convention that we have created, so that the favourite title and location details
    // can all be stored in the one string). The values from these strings will be split and
    // passed into the relevant constructor of each favTab (in the constructor of this class).
    // The first two numbers after the "@" represent the latitude and longitude of the location
    // The heading represents the left/right positioning of the view (between 0 and 360)
    // The pitch represents the up/down angle of the view (between -90 and 90)
    // In the original Google Street View URL (from the browser) i.e. the Colosseum
    // url was https://www.google.ie/maps/@41.8902646,12.4905161,3a,75y,90.81h,95.88t/data=!3m6!1e1!3m4!1sR8bILL5qdsO7_m5BHNdSvQ!2e0!7i13312!8i6656!6m1!1e1
    // the first two numbers after the @ represent the latitude and longitude, the number
    // with the h after it represents the heading, and the number with the t after it
    // seems to be to do with the pitch, but never works that way in this
    // method so I just decided the pitch value based on what looks good
    private String[] favourites = {
            "Pyramids Of Giza@29.9752572,31.1387288&heading=292.67&pitch=-0.76",
            "Eiffel Tower@48.8568402,2.2967311&heading=314.59&pitch=20",
            "Colosseum@41.8902646,12.4905161&heading=80&pitch=10",
            "Taj Mahal@27.1738903,78.0419927&heading=10&&pitch=10",
            "Big Ben@51.500381,-0.1262538&heading=105&pitch=10",
            "Leaning Tower Of Piza@43.7224555,10.3960728&heading=54.4&pitch=10",
            "TimesSquare@40.7585806,-73.9850687&heading=30&pitch=20"
    };

    public Boolean loaded = false;

    // Declaring a private favTabs array, to store each of the favourite tabs we create,
    // so that they can be looped through in this class's showScreen() method, to display
    // the tabs, as well as checking if they are being clicked on.
    public ArrayList<FavouriteTab> favTabs;

    // Creating a public constructor for the FavouriteScreen class, so that
    // an instance of it can be declared in the main sketch
    public FavouritesScreen(Sketch _sketch, PImage bgImage) {

        // Passing the color parametre to the super class (Screen), which will in
        // turn call it's super class (Rectangle) and create a rectangle with the
        // default values i.e. fullscreen, centered etc.
        super(_sketch, bgImage);

        sketch = _sketch;

        // Creating the favTabs array to be long enough to contain each of the favourite
        // places we have declared in the favourites array above i.e. so that we will have
        // enough tabs to display each of the favourites
        this.favTabs = new ArrayList<FavouriteTab>();

        // Looping through the favourites array, so that we can create a new tab for
        // each favourite place
        for (int f = 0; f < sketch.favouriteLocationsData.length; f++) {

            // Getting the title of the favourite location by splitting the value stored for
            // the favourite at the "@" symbol (Note - this is a naming convention that we have
            // created, so that the favourite title and location details can all be stored in
            // the one string). Once the string has been split, it results in a new array that
            // contains the two half's, the first being the title of the favourite, so in this
            // instance we want to get the portion of the string at index 0
            String favTitle = sketch.favouriteLocationsData[f].getString("name");

            // As with the favTitle, we are splitting the favourite string at the "@", except
            // this time it is the portion of the string at index 1 we want (i.e. the second
            // half of the string which contains the URL data we require to request this
            // specific location (longitude, latitude, heading and pitch).
            String favLocation = sketch.favouriteLocationsData[f].getString("latLng")
                    + "&heading=" + sketch.favouriteLocationsData[f].getString("heading")
                    + "&pitch=" + sketch.favouriteLocationsData[f].getString("pitch");

            // Creating a temporary variable to hold the new Favourite Tab, passing in the title
            // and location URL data for the current favourite, as well as the increment variable,
            // which will be used to space out the favourite tabs on the page within the constructor
            // of the FavouriteTab class (as it passes the y value to the super class, it multiplies
            // the y value by this number, so that it increases with each tab i.e. they are spaced
            // vertically down along the screen)
            FavouriteTab newFavTab = new FavouriteTab(sketch, favTitle, favLocation, f);

            // Adding the new FavTab to this class's favTabs array, so that we can loop through them
            // in the showScreen() method, to display them, as well as checking if they are being
            // clicked on.
            favTabs.add(newFavTab);
        }

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

        // Creating a temporary allIcons array to store the icon/s we have created above.
        Icon[] allIcons = {homeIcon};

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
        this.setScreenTitle("Favourites");
    }

    // Creating a public showScreen method, which is called by the draw() funciton whenever this
    // screen needs to be displayed
    public void showScreen() {

        if (!this.loaded) {
            // Resetting the position values of the element so on the screen every time the page is opened,
            // so that if a user leaves the screen half scrolled, it will still be reset upon their return
            this.setY(sketch.appHeight/2);
            this.getScreenIcons()[0].setY(sketch.iconTopY);

            for (int i = 0; i < favTabs.size(); i++) {
                favTabs.get(i).setY((float) ((i + 1) * sketch.appHeight * 0.25));
            }

            // Setting loaded to true, so that this block of code will only run once (each time this page
            // is opened). This value will be reset to false in the Icon class's checkMouseOver function,
            // when an icon that links to another page has been clicked.
            this.loaded = true;
            sketch.println("firstLoad");
        }

        // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
        // icons. This method will then in turn call it's super class's (Rectangle) method, to
        // generate the size and background of the screen
        this.drawScreen();

        // Looping through our array of favourite tabs, and calling the showFavourite() method (of
        // the FavouriteTab class) to display the tab on screen
        for (int f = 0; f < this.favTabs.size(); f++) {
            this.favTabs.get(f).showFavourite();
        }

        if (sketch.mousePressed) {
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
                    if (this.getY() <= sketch.appHeight/2) {
                        // Setting the y position of the icon to it's current position, plus the amount scrolled i.e.
                        // moving the icon down the screen
                        icons[i].setY(icons[i].getY() + amountScrolled);
                    }
                }
            }

            // Looping through each of the page icons, which are only being stored in an array within
            // this class so that they can be looped through to be repositioned (i.e. in every other
            // screen, these icons would be stored only in the super class, and not directly accessible
            // within the individual screen classes
            for (int i = 0; i < favTabs.size(); i++) {
                // Checking which direction the user scrolled
                if (sketch.previousMouseY > sketch.mouseY) {
                    // The user has scrolled UP
                    // Setting the y position of the icon to it's current position, minus the amount scrolled i.e.
                    // moving the icon up the screen
                    this.favTabs.get(i).setY(this.favTabs.get(i).getY() - amountScrolled);
                } else {
                    // The user has scrolled DOWN
                    // Checking if the screen's y position is less than or equal to half of the height i.e. is
                    // so that the screen cannot be down any further once you reach the top
                    if (this.getY() <= sketch.appHeight/2) {
                        // Setting the y position of the icon to it's current position, plus the amount scrolled i.e.
                        // moving the icon down the screen
                        this.favTabs.get(i).setY(this.favTabs.get(i).getY() + amountScrolled);
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
                if (this.getY() <= appHeight/2) {
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

    public String getRandomFavourite(){
        // Splitting the favourite string at the "@". Getting data at index 1 of the new split
        // string array (i.e. the second half of the string which contains the URL data we
        // require to request this specific location (longitude, latitude, heading and pitch).
        // Getting a random location by generating a random index value within the length of the
        // favourites array (rounding it off so that it will always equal an int)
        int randomIndex = round(random(this.favTabs.size() - 1));

        String locationURLData = this.favTabs.get(randomIndex).favTitle + "@" + this.favTabs.get(randomIndex).favLocation;
        return locationURLData;
    }

    public ArrayList<FavouriteTab> getFavTabs(){
        return favTabs;
    }
}