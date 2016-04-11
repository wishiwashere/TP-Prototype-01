package processing.test.wish_i_was_here;

import android.graphics.Rect;

public class LearningScreen extends Screen {
    private Sketch sketch;

    private Rectangle[] allLearningRects = new Rectangle[5];
    private String[] allLearningRectsText = {
            "Save\r\nyour\r\nlocation",
            "Return to\r\nhome",
            "Move to\r\nview\r\nup/down",
            "Take a\r\npicture",
            "Switch\r\ncamera\r\nviews"
    };

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
            allLearningRects[i].setRotation(sketch.deviceOrientation);
            allLearningRects[i].setBackgroundColor(sketch.color(255));
            allLearningRects[i].show();
            allLearningRects[i].addText(allLearningRectsText[i], allLearningRects[i].getX(), allLearningRects[i].getY());
        }
    }
}
