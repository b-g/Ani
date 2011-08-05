/**
 * shows how to delay the start of an animation
 * 	 
 * MOUSE
 * click           : set end position of the animations and trigger new one
 */
 
import de.looksgood.ani.*;

float x = 256;
float y = 256;

void setup() {
  size(512,512);
  smooth();
  noStroke();

  // you have to call always Ani.init() first!
  Ani.init(this);
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,120,120);
}

void mouseReleased() {
    // the animation is 1 sec delayed
    Ani.to(this, 0.5, 1, "x", mouseX);
    Ani.to(this, 0.5, 1, "y", mouseY);
}

