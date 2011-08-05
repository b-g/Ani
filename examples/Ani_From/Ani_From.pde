/**
 * shows how to use the Ani.from feature. this can be usefull 
 * if you know already the final postion e.g. moving things from
 * outside into the display
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
  // animate the variables x and y in 1.5 sec from mouse click position to the initial values
  Ani.from(this, 1.5, "x", mouseX, Ani.QUINT_IN_OUT);
  Ani.from(this, 1.5, "y", mouseY, Ani.QUINT_IN_OUT);
}


