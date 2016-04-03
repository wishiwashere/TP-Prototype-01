package processing.test.wish_i_was_here;

public class FavouriteTab extends ClickableElement {
    private Sketch sketch;

    // Creating two private strings, one to hold the favourite title, and the other to hold
    // the location URL data of the favourite. The title will be used as text on the tab
    // to show what location it links to. The favLocation will be passing in as part of the
    // googleStreetViewImageApiURL in this class's showFavourite() method, if this tab
    // is clicked on
    public String favTitle;
    public String favLocation;

    public FavouriteTab(Sketch _sketch, String title, String location, float y) {
        // Calling the super class (ClickableElement) constructor, passing in the required
        // x value (which is centered), y value (which is incremented by this favourite's
        // position in the FavouriteScreen favourites array - plus 1 so that this value
        // never equals 0), setting a relative width and height, as well as a semi transparent
        // almost white colour (as pure white will not be shown - previous work around in the
        // Rectangle class), and the title of the location (for printing to the console to
        // let us know which tab was clicked on - if one has been clicked).
        super(_sketch, _sketch.width/2, (y + 1) * _sketch.height * 0.25, _sketch.width * 0.7, _sketch.height * 0.2, "placeholder.PNG", title);

        sketch = _sketch;

        // Initialising the two private strings, one to hold the favourite title, and the other to hold
        // the location URL data of the favourite. The title will be used as text on the tab
        // to show what location it links to. The favLocation will be passing in as part of the
        // googleStreetViewImageApiURL in this class's showFavourite() method, if this tab
        // is clicked on
        this.favTitle = title;
        this.favLocation = location;
    }

    public void showFavourite() {
        // Showing this tab (Using the super Rectangle class's method show())
        this.show();

        // Adding the title text to this tab (as specified in the tab's constructor)
        this.addText(this.favTitle, this.getX(), this.getY(), this.getWidth() * 0.1);

        // If the mouse is currently pressed, checking if the mouse was over this
        // tab when the press/click occurred
        if (sketch.mousePressed) {
            // Checking the mouse was over this by using the super class ClickableElement's
            // checkMouseOver() method
            if (this.checkMouseOver()){
                sketch.googleImageLatLng = this.favLocation.split("&")[0];
                sketch.googleImageHeading = Float.parseFloat(this.favLocation.split("heading=")[1].split("&")[0]);
                sketch.googleImagePitch = Float.parseFloat(this.favLocation.split("pitch=")[1]);

                sketch.currentLocationName = this.getFavTitle();

                sketch.loadGoogleImage();
            }
        }
    }

    public String getFavTitle(){
        return favTitle;
    }
}