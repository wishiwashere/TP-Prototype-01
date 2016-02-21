public class FavouriteTab extends ClickableElement{
  private String favTitle;
  
  public FavouriteTab(String title, float y){
    super(iconCenterX, (y + 1) * appHeight * 0.25, appWidth * 0.8, appHeight * 0.2, color(255, 255, 249, 149), title);
    favTitle = title;
  }
  
  public void showFavourite(){
    this.show();
    this.addText(favTitle, this.getX(), this.getY(), this.getWidth() * 0.1);
    if(mousePressed){
      this.checkMouseOver();
    }
  }
}