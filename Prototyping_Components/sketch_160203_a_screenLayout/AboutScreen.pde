public class AboutScreen extends Screen{
  private Icon[] allIcons;
  private Icon homeIcon;
  
  public AboutScreen(color col){
    super(col);
    
    homeIcon = new Icon(width/2, 100, 260, 50, 0, #ffffff, "HomeScreen", "Go Home");
    
    allIcons = new Icon[1];
    allIcons[0] = homeIcon;
  }
  
  public void showAboutScreen(){
    this.show();
    for(int i = 0; i < allIcons.length; i++){
      allIcons[i].showIcon();
      if(mousePressed)
      {
        allIcons[i].checkMouseOver();
      }
    }
  }
}