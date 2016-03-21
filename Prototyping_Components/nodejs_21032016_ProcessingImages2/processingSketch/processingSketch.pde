String[] response;

void setup(){
  response = loadStrings("http://localhost:3000/insertCameraImageHere");
  println(response[0]);
}

void draw(){
}