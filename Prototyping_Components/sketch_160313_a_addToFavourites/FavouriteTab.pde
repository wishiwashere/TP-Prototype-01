public class FavouriteTab extends ClickableElement {  
  // Creating two private strings, one to hold the favourite title, and the other to hold
  // the location URL data of the favourite. The title will be used as text on the tab
  // to show what location it links to. The favLocation will be passing in as part of the
  // googleStreetViewImageApiURL in this class's showFavourite() method, if this tab
  // is clicked on  
  private String favTitle;
  private String favLocation;

  public FavouriteTab(String title, String location, float y) {
    // Calling the super class (ClickableElement) constructor, passing in the required
    // x value (which is centered), y value (which is incremented by this favourite's
    // position in the FavouriteScreen favourites array - plus 1 so that this value
    // never equals 0), setting a relative width and height, as well as a semi transparent
    // almost white colour (as pure white will not be shown - previous work around in the 
    // Rectangle class), and the title of the location (for printing to the console to 
    // let us know which tab was clicked on - if one has been clicked).
    super(iconCenterX, (y + 1) * appHeight * 0.25, appWidth * 0.7, appHeight * 0.2, color(255, 255, 255, 149), title);

    // Initialising the two private strings, one to hold the favourite title, and the other to hold
    // the location URL data of the favourite. The title will be used as text on the tab
    // to show what location it links to. The favLocation will be passing in as part of the
    // googleStreetViewImageApiURL in this class's showFavourite() method, if this tab
    // is clicked on 
    favTitle = title;
    favLocation = location;
  }

  public void showFavourite() {
    // Showing this tab (Using the super Rectangle class's method show())
    this.show();

    // Adding the title text to this tab (as specified in the tab's constructor)
    this.addText(favTitle, this.getX(), this.getY(), this.getWidth() * 0.1);

    // If the mouse is currently pressed, checking if the mouse was over this
    // tab when the press/click occurred
    if (mousePressed) {
      // Checking the mouse was over this by using the super class ClickableElement's
      // checkMouseOver() method
      if (this.checkMouseOver()) {
        
        googleImageLatLng = this.favLocation.split("&")[0]; 
        googleImageHeading = float(this.favLocation.split("heading=")[1].split("&")[0]);
        googleImagePitch = float(this.favLocation.split("pitch=")[1]);
        currentLocationName = favTitle;
        
        loadGoogleImage();
      }
    }
  }
  
  public String getFavTitle(){
    return favTitle;
  }
}