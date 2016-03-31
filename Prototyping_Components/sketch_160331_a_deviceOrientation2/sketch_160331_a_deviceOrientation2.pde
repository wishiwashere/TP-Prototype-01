import ketai.sensors.*;

KetaiSensor sensor;

float accelerometerX;
float accelerometerY;
float accelerometerZ; 

void setup() {
  sensor = new KetaiSensor(this);
  sensor.start();
  textSize(26);
}

void draw() {
  //prints the hardware and software sensors
  //println(sensor.list());
  background(0);
  
  text("Accelerometer: \n" +
    "x: " + nfp(accelerometerX, 2, 3) + "\n", width/2, height/2);
}


void onAccelerometerEvent(float x, float y, float z) {
  println("linear accelerometer event"); 
  //println(sensor.isLinearAccelerationAvailable());
  println(" x is: " + x);
  //accelerometerX = x; 
  accelerometerX = x; 
  //accelerometerZ = z;
  
  if(x < -7){
    println("Turned right");
    fill(185,70,198);
  }else if(x > 7){
    println("Turned Left");
    fill(70,198,187);
  }else{
    println("In the middle/standing up");
    fill(146,227,55);
  }
}