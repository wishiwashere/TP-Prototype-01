public class Rectangle{
  public float rectWidth;
  public float rectHeight;
  public color rectCol;
  
  public Rectangle(float w, float h, color col){
    rectWidth = w;
    rectHeight = h;
    rectCol = color(col);
    drawRectangle();
  }
  public void drawRectangle(){
    fill(rectCol);
    rect(0, 0, rectWidth, rectHeight);
  }
}