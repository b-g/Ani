/**
 * shows how to pause an resume an animation
 *
 * MOUSE
 * click           : set end position of the animations and trigger new one
 * 
 * KEYS
 * space           : toggle, pause and resume animation
 */
import de.looksgood.ani.*;

float x = 256;
float y = 256;

Ani aniX;
Ani aniY;

void setup() {
  size(512,512);
  smooth();
  noStroke();

  // you have to call always Ani.init() first!
  Ani.init(this);

  // save the references to the anis
  aniX = Ani.to(this, 5, "x", random(0,width));
  aniY = Ani.to(this, 5, "y", random(0,height));
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,120,120);
}

void mouseReleased() {
  aniX = Ani.to(this, 3, "x", mouseX);
  aniY = Ani.to(this, 3, "y", mouseY);
}

void keyPressed() {
  if (key == ' ') {
    if (aniX.isPlaying() && aniY.isPlaying()) {
      aniX.pause();
      aniY.pause();
    }
    else {
      aniX.resume();
      aniY.resume();
    }
  }
}


