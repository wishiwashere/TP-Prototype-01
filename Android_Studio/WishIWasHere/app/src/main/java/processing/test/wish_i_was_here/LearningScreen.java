package processing.test.wish_i_was_here;

import android.graphics.Rect;

public class LearningScreen extends Screen {
    private Sketch sketch;

    private Rectangle[] allLearningRects = new Rectangle[5];
    private String[] allLearningRectsText = {
            "Save your location",
            "Return to home",
            "Move to view up/down",
            "Take a picture",
            "Switch camera views"
    };

    private String favText = "Save your location";
    private String homeText = "Return to home";
    private String shakeMovementText = "Tilt your phone forwards and backwards to look up and down";
    private String captureText = "Take a picture";
    private String switchCameraText = "Switch camera views";
    private String movement = "Swipe left or right to move around your current location";

    private float learningBoxWidth;
    private float learningBoxHeight;
    public LearningScreen(Sketch _sketch) {
        super(_sketch);
        sketch = _sketch;
        this.setBackgroundColor(sketch.color(33, 33, 33, 100));
        learningBoxWidth = sketch.appWidth / 4;
        learningBoxHeight = sketch.appHeight / 6;

        allLearningRects[0] = new Rectangle(sketch, sketch.iconLeftX, sketch.iconTopY + sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[1] = new Rectangle(sketch, sketch.iconRightX, sketch.iconTopY + sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[2] = new Rectangle(sketch, sketch.iconLeftX, sketch.iconBottomY - sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[3] = new Rectangle(sketch, sketch.iconCenterX, sketch.iconBottomY - sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
        allLearningRects[4] = new Rectangle(sketch, sketch.iconRightX, sketch.iconBottomY - sketch.largeIconSize, learningBoxWidth, learningBoxHeight);
    }

    public void showScreen() {
        super.show();

        for (int i = 0; i < allLearningRects.length; i++) {
            double x1 = allLearningRects[i].getX() - (learningBoxWidth * 0.48);
            double y1 = allLearningRects[i].getY() - (learningBoxHeight * 0.48);
            double x2 = allLearningRects[i].getX() + (learningBoxWidth * 0.48);
            double y2 = allLearningRects[i].getY() + (learningBoxHeight * 0.48);

            allLearningRects[i].setBackgroundColor(sketch.color(255));
            allLearningRects[i].show();
            allLearningRects[i].addTextBox(allLearningRectsText[i], x1, y1, x2, y2, sketch.CENTER, sketch.CENTER);
        }
        //Colour for the background of the learning mode box, filling it a light shade od grey
      /*  sketch.fill(240, 240, 240);
        sketch.rect((float)(sketch.appWidth / 32), (float)(sketch.appHeight / 16), learningBoxX2, (float)(sketch.appHeight / 6));
        sketch.rect((float)(sketch.appWidth / 1.4), (float)(sketch.appHeight / 16), learningBoxX2, (float)(sketch.appHeight / 6));
        sketch.rect((float)(sketch.appWidth / 32), (float)(sketch.appHeight / 1.3), learningBoxX2, (float)(sketch.appHeight / 4.8));
        sketch.rect((float) (sketch.appWidth / 2.8), (float) (sketch.appHeight / 1.3), learningBoxX2, (float) (sketch.appHeight / 6));
        sketch.rect((float) (sketch.appWidth / 1.4), (float) (sketch.appHeight / 1.3), learningBoxX2, (float) (sketch.appHeight / 6));
*/


        //For the text
    /*    sketch.textSize(sketch.defaultTextSize);

        sketch.fill(0);
        sketch.text(favText, (float)(sketch.appWidth / 24.5), (float)(sketch.appHeight / 14.2), learningBoxX2, (float)(sketch.appHeight / 6));
        sketch.text(homeText, (float)(sketch.appWidth / 1.38), (float)(sketch.appHeight / 14.2), learningBoxX2, (float)(sketch.appHeight / 6));
        sketch.text(shakeMovementText, (float)(sketch.appWidth / 24.5), (float)(sketch.appHeight / 1.29), learningBoxX2, (float)(sketch.appHeight / 4.8));
        sketch.text(captureText, (float)(sketch.appWidth / 2.7), (float)(sketch.appHeight / 1.29), learningBoxX2, (float)(sketch.appHeight / 6));
        sketch.text(switchCameraText, (float)(sketch.appWidth / 1.38), (float)(sketch.appHeight / 1.29), learningBoxX2, (float)(sketch.appHeight / 6));
    */
    }
}
