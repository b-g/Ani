/**
 * shows how to seek an animation in time
 * 	 
 * MOUSE
 * drag           : move from left to right to seek the animation
 * 
 * KEYS
 * space          : toggle, pause and resume animation
 */

import de.looksgood.ani.*;

int padding = 50;
float startX, startY, endX, endY;

float x, y;
int diameter = 20;

Ani aniX;
Ani aniY;

void setup() {
  size(512,512);
  smooth();

  startX = 0 + padding;
  startY = height - padding;
  endX = width - padding;
  endY = 0 + padding;

  x = startX;
  y = startY;

  // Ani.init() must be called always first!
  Ani.init(this);
  aniX = Ani.to(this, 5, 1, "x", endX, Ani.LINEAR);
  aniY = Ani.to(this, 5, 1, "y", endY, Ani.ELASTIC_OUT);
}

void draw() {
  // seek
  if (mousePressed == true) {
    float seekValue = mouseX/(float)width;
    aniX.seek(seekValue);
    aniY.seek(seekValue);
  }

  // draw everything
  background(255);
  line(startX, startY, endX, endY);
  fill(0);
  ellipse(x,y,diameter,diameter);
  ellipse(x,startY,diameter/4,diameter/4);
  ellipse(startX,y,diameter/4,diameter/4);
}

// pause
void mousePressed() {
  aniX.pause();
  aniY.pause();
}

// resume
void mouseReleased() {
  aniX.resume();
  aniY.resume();
}

// pause and resume animation by pressing SPACE
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














