import http.requests.*;
import java.io.File;

PImage serverImage;

File greenImage;

PostRequest post;


void setup() {
  size(320, 480);
  //greenImage = new File(sketchPath("/data/girlGreenScreen.jpg"));
  
  post = new PostRequest("http://localhost:3000/");
  //post.addHeader("Content-Type", "multipart/form-data");
  //post.addHeader("Content-Type", "text/plain");
  //post.addHeader("Content-Type", "application/json");
  post.addHeader("Content-Type", "application/x-www-form-urlencoded");
  post.addData("name", "laura");
  //post.addFile("GreenImage", greenImage);
  post.send();
  println("Reponse Content: " + post.getContent());
  println("Reponse Content-Length Header: " + post.getHeader("Content-Length"));
  //serverImage = loadImage("http://localhost:3000/", "jpg");
}

void draw() {
  //image(serverImage, 0, 0);
}