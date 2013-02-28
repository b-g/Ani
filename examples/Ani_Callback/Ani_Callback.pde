/**
 * shows how to use the callback features onStart and onEnd
 * 	 
 * MOUSE
 * click           : set end of animation and trigger a new one
 */
 
import de.looksgood.ani.*;

float x = 256;
float y = 256;
int diameter = 50;

Ani diameterAni;

void setup() {
  size(512,512);
  smooth();
  noStroke();

  // Ani.init() must be called always first!
  Ani.init(this);

  // define a Ani with callbacks, specify the method name after the keywords: onStart, onEnd, onDelayEnd and onUpdate 
  diameterAni = new Ani(this, 1.5, "diameter", 150, Ani.EXPO_IN_OUT, "onStart:itsStarted, onEnd:newRandomDiameter");  
  
  // side info: it's also possible to define this in a static way
  //diameterAni = Ani.to(this, 1.5, "diameter", 150, Ani.EXPO_IN_OUT, "onStart:itsStarted, onEnd:newRandomDiameter"); 
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,diameter,diameter);
}

void mouseReleased() {
  Ani.to(this, 1.5, "x", mouseX, Ani.ELASTIC_OUT);
  Ani.to(this, 1.5, "y", mouseY, Ani.ELASTIC_OUT);

  // start diameterAni
  diameterAni.start();
}

// there are two possiblities to declare the methods for the callbacks:
// without any parameters or with one parameter of type Ani

// called onStart of diameterAni animation
void itsStarted() {
  println("diameterAni started");
}

// called onEnd of diameterAni animation
void newRandomDiameter(Ani theAni) {
  float end = theAni.getEnd();
  float newEnd = end + random(-20,20);
  theAni.setEnd(newEnd);
  println("diameterAni finished. current end: "+end+" -> "+"new end: "+newEnd);
}


