public class AboutScreen extends Screen{
  private String aboutText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent maximus, turpis sit amet condimentum gravida, est quam bibendum purus, ac efficitur lectus justo in tortor. Phasellus et interdum mi.";
  private Icon[] pageIcons;
  public Boolean loaded = false;
  
  // Creating a public constructor for the AboutScreen class, so that
  // an instance of it can be declared in the main sketch
  public AboutScreen(color col){
    
    // Passing the color parametre to the super class (Screen), which will in
    // turn call it's super class (Rectangle) and create a rectangle with the 
    // default values i.e. fullscreen, centered etc.
    super(appWidth/2, appHeight/2, appWidth, appHeight * 2, col);
    
    this.addBackgroundImage(loadImage("aboutScreenBackgroundImage.png"), appWidth, appHeight, 1, 0);
    
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
    Icon facebookIcon = new Icon(appWidth * 0.2, iconBottomY, facebookAccountIconImage, "Facebook", false, "https://www.facebook.com/wishiwashereapp");
    Icon twitterIcon = new Icon(appWidth * 0.5, iconBottomY, twitterAccountIconImage, "Twitter", false, "https://twitter.com/wishiwashere");
    Icon instagramIcon = new Icon(appWidth * 0.8, iconBottomY, instagramAccountIconImage, "Instagram", false, "https://www.instagram.com/wishiwashereapp/");
    //Icon emailIcon = new Icon(appWidth * 0.8, iconBottomY, emailIconImage, "Email", false, "mailto:wishiwashere.thenopayholiday@gmail.com");
   
    
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
    
    // Setting the title of this screen. The screenTitle variable was also declared in this
    // class's super class (Screen), so that it can be accessed when showing the screen 
    // (i.e can be displayed as the header text of the page). If no screenTitle were set,
    // then no header text will appear on this page
    this.setScreenTitle("About");
  }
  
  // Creating a public showScreen method, which is called by the draw() funciton whenever this
  // screen needs to be displayed
  public void showScreen(){
    if(!loaded){
      // Resetting the position values of the element so on the screen every time the page is opened,
      // so that if a user leaves the screen half scrolled, it will still be reset upon their return
      this.setY(appHeight/2);
      this.getScreenIcons()[0].setY(iconTopY);
      this.getScreenIcons()[1].setY(iconBottomY);
      this.getScreenIcons()[2].setY(iconBottomY);
      this.getScreenIcons()[3].setY(iconBottomY);
      
      // Setting loaded to true, so that this block of code will only run once (each time this page
      // is opened). This value will be reset to false in the Icon class's checkMouseOver function,
      // when an icon that links to another page has been clicked.
      loaded = true;
      println("firstLoad");
    }
    
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
      
      // Calculating the amount scolled, based on the distance between the previous y position, 
      // and the current y position. When the mouse is first pressed, the previous y position
      // is initialised (in the main sketch) but then while the mouse is held down, the previous
      // y position gets updated each time this function runs (so that the scrolling can occur
      // while the person is still moving their hand (and not just after they release the screen)
      float amountScrolled = dist(0, previousMouseY, 0, mouseY);
      
      Icon[] icons = this.getScreenIcons();
      
      // Looping through each of the page icons, which are only being stored in an array within
      // this class so that they can be looped through to be repositioned (i.e. in every other
      // screen, these icons would be stored only in the super class, and not directly accessible
      // within the individual screen classes
      for(int i = 0; i < icons.length; i++){
        // Checking which direction the user scrolled
        if(previousMouseY > mouseY){
          // The user has scrolled UP
          // Setting the y position of the icon to it's current position, minus the amount scrolled i.e.
          // moving the icon up the screen
          icons[i].setY(icons[i].getY() - amountScrolled);
        } else {
          // The user has scrolled DOWN
          // Checking if the screen's y position is less than or equal to half of the height i.e. is 
          // so that the screen cannot be down any further once you reach the top
          if(this.getY() <= appHeight/2){
            // Setting the y position of the icon to it's current position, plus the amount scrolled i.e.
            // moving the icon down the screen
            icons[i].setY(icons[i].getY() + amountScrolled);
          }
        }
      }
      
      // Checking which direction the user scrolled (the reason I have to do this seperatley from above is
      // that including these lines within the icons loop above makes these elements move faster than the
      // page icons
      if(previousMouseY > mouseY){
        // The user has scrolled UP
        // Setting the screen's y postion to it's current y position, minus the amount scrolled
        this.setY(this.getY() - amountScrolled);
        // Setting the global positioning variable screenTitleY to be decremented by the amount scrolled. Note:
        // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
        // an icon's link is passed in to change a page)
        screenTitleY -= amountScrolled;
      } else {
        // The user has scrolled DOWN
        // Checking if the screen's y position is less than or equal to half of the height i.e. is 
        // so that the screen cannot be down any further once you reach the top
        if(this.getY() <= appHeight/2){
          // Setting the screen's y postion to it's current y position, plus the amount scrolled
          this.setY(this.getY() + amountScrolled);
          // Setting the global positioning variable screenTitleY to be incremented by the amount scrolled. Note:
        // this variable gets reset everytime the page is changed (in the Icon class's checkMouseOver function, when
        // an icon's link is passed in to change a page)
          screenTitleY += amountScrolled;
        }
      }
      
      // Updating the previous mouse Y to be equal to the current mouse y, so that the next time this function is
      // called, the scrolling will be detected from this point i.e. so that scrolling appears continous, even if the
      // user keeps there finger/mouse held on the screen while moving up and down
      previousMouseY = mouseY;
    }
  }
}