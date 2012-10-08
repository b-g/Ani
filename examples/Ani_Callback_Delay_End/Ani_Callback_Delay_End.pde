/**
 * shows how to use the callback feature onDelayEnd
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

  // define a Ani with callback, specify the method name after the keyword: onDelayEnd 
  diameterAni = new Ani(this, 1.5, 1, "diameter", 150, Ani.EXPO_IN_OUT, "onDelayEnd:delayIsOver"); 
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,diameter,diameter);
  //println(diameterAni.isDelaying());
}

void mouseReleased() {
  Ani.to(this, 1.5, "x", mouseX, Ani.ELASTIC_OUT);
  Ani.to(this, 1.5, "y", mouseY, Ani.ELASTIC_OUT);

  // start diameterAni
  diameterAni.start();
}

// called onDelayEnd if the animation begins to tween the value
void delayIsOver() {
  println("diameterAni delayIsOver()");
}
