PImage serverImage;

void setup() {
  size(320, 480);
  serverImage = loadImage("http://localhost:3000/", "jpg");
}

void draw() {
  image(serverImage, 0, 0);
}