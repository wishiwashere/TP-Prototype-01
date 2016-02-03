protected class Screen extends Rectangle{
  
  protected Icon[] allIcons;
  protected String screenTitle = "";
  
  // Creating a public constructor for the Screen class, so that
  // instances of it can be created anywhere in the sketch
  protected Screen(){
    super();
  }
  
  protected Screen(color col){
    
    // Passing all of the parametres from the constructor
    // class into the super class (Rectangle)
    super(col);
  }
  
  public void showScreen(){
    this.show();
    if(screenTitle.length() > 0)
    {
      fill(0);
      text(screenTitle, width/2, 50);
    }
    if(allIcons.length > 0)
    {
      for(int i = 0; i < allIcons.length; i++){
        allIcons[i].showIcon();
        if(mousePressed)
        {
          allIcons[i].checkMouseOver();
        }
      }
    }
  }
}