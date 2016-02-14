public class AboutScreen extends Screen{
  
  String aboutText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent maximus, turpis sit amet condimentum gravida, est quam bibendum purus, ac efficitur lectus justo in tortor. Phasellus et interdum mi.";
  Icon[] pageIcons;
  float previousY = 0; 
  
  // Creating a public constructor for the AboutScreen class, so that
  // an instance of it can be declared in the main sketch
  public AboutScreen(color col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(appWidth/2, appHeight/2, appWidth, appHeight * 2, col);
    
    // Creating the icon/s for this screen, using locally scoped variables, as these
    // icons will be only ever be referred to from the allIcons array. Setting their
    // x, and y, based on percentages of the width and height (where icon positioning variables
    // are used, these were defined in the main sketch. Not passing in any width or height, so as 
    // to allow this icon to be set to the default size in the Icon class of the app . Passing
    // in a colour value of white. Passing in a name for the icon, followed by a boolean to choose
    // whether this name should be displayed on the icon or not. Finally, passing in a linkTo 
    // value of the name of the screen they will later link to. The title arguments, as well
    // as the linkTo argument, are optional
    Icon homeIcon = new Icon(iconRightX, iconTopY, homeIconImage, "Home", false, "HomeScreen");
    Icon facebookIcon = new Icon(appWidth * 0.2, iconBottomY, loadImage("placeholder.PNG"), "Facebook", false, "https://www.facebook.com/wishiwashereapp");
    Icon twitterIcon = new Icon(appWidth * 0.4, iconBottomY, loadImage("placeholder.PNG"), "Twitter", false, "https://twitter.com/wishiwashere");
    Icon instagramIcon = new Icon(appWidth * 0.6, iconBottomY, loadImage("placeholder.PNG"), "Instagram", false, "https://www.instagram.com/wishiwashereapp/");
    Icon emailIcon = new Icon(appWidth * 0.8, iconBottomY, loadImage("placeholder.PNG"), "Email", false, "mailto:wishiwashere.thenopayholiday@gmail.com");
   
    
    // Creating a temporary allIcons array to store the icon/s we have created above.
    Icon[] allIcons = {homeIcon, facebookIcon, twitterIcon, instagramIcon, emailIcon};
    pageIcons = allIcons;
    
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
    this.setScreenTitle("About");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    
    // Calling the super class's (Screen) drawScreen() method, to display each of this screen's
    // icons. This method will then in turn call it's super class's (Rectangle) method, to 
    // generate the size and background of the screen
    this.drawScreen();
    
    this.addImage(loadImage("placeholder.PNG"), appWidth/2, this.getY() + (appHeight * -0.25), appWidth * 0.8, appHeight * 0.2);
    
    rectMode(CORNER);
    textAlign(LEFT);
    textSize(appWidth * 0.05);
   
    text(aboutText, appWidth * 0.1, this.getY()  + (appHeight * -0.1), appWidth * 0.8, appHeight * 0.9);
    
    // Checking if the page is being scrolled
    if(mousePressed){
      float amountScrolled = dist(0, previousMouseY, 0, mouseY);
      for(int i = 0; i < pageIcons.length; i++){
        if(previousMouseY > mouseY){
          pageIcons[i].setY(pageIcons[i].getY() - amountScrolled);
          this.setY(this.getY() - amountScrolled);
        } else {
          pageIcons[i].setY(pageIcons[i].getY() + amountScrolled);
          this.setY(this.getY() + amountScrolled);
        }
      }
      previousMouseY = mouseY;
    }
  }
}