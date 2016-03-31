import ketai.sensors.*;

KetaiSensor sensor;

float accelerometerX;
float accelerometerY;
float accelerometerZ; 

void setup() {
  sensor = new KetaiSensor(this);
  sensor.start();
}

void draw() {
  //prints the hardware and software sensors
  //println(sensor.list());
  text("Accelerometer: \n" +
    "x: " + nfp(accelerometerX, 2, 3) + "\n" +
    "y: " + nfp(accelerometerY, 2, 3) + "\n" +
    "z: " + nfp(accelerometerZ, 2, 3), width/2, height/2);
}
void enableOrientation() {
  println("enables");
}

void enableAllSensors() {
}

void onRotationVectorEvent(float x, float y, float z) {
  println("rotation vector event"); 
  //println(sensor.isLinearAccelerationAvailable());
  println("x is: " + x + " y is: " + y + " z is: " + z);
}

void onOrientationEvent(float x, float y, float z) {
  println("orientation event"); 
  //println(sensor.isLinearAccelerationAvailable());
  println("x is: " + x + " y is: " + y + " z is: " + z);
}

void onLinearAccelerationEvent(float x, float y, float z) {
  println("orientation event"); 
  //println(sensor.isLinearAccelerationAvailable());
  println("x is: " + x + " y is: " + y + " z is: " + z);
}

void onAccelerometerEvent(float x, float y, float z) {
  println("linear accelerometer event"); 
  //println(sensor.isLinearAccelerationAvailable());
  println("x is: " + x + " y is: " + y + " z is: " + z);
  accelerometerX = x; 
  accelerometerY = y; 
  accelerometerZ = z;
}