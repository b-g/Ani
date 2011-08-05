/**
 * show how to use the Ani library in a webbrowser
 * 
 * you have to add "public" for sketches running 
 * in browsers (due to the java security manager).
 * otherwise Ani will not work!
 *
 * MOUSE
 * click           : set end position of the animations and trigger new one
 */

import de.looksgood.ani.*;

public float x = 256;
public float y = 256;

void setup() {
  size(512,512);
  smooth();
  noStroke();

  // Ani.init() must be called always first!
  Ani.init(this);
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,120,120);
}

void mouseReleased() {
  Ani.to(this, 1.5, "x", mouseX);
  Ani.to(this, 1.5, "y", mouseY);
}



