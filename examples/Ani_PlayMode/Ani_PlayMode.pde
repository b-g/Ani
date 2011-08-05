/**
 * shows how to set the play mode and repetitions of animations
 * possible play modes are: 
 * 
 * Ani.FORWARD
 * Ani.BACKWARD
 * Ani.YOYO
 */

import de.looksgood.ani.*;

Ani animation;
float diameter = 10;
float x = 256;
float y = 256;

void setup() {
  size(512,512);
  smooth();
  noStroke();
  textAlign(CENTER);

  // Ani.init() must be called always first!
  Ani.init(this);
  animation = new Ani(this, 2, "diameter", 200);

  // FORWARD, BACKWARD, YOYO
  animation.setPlayMode(Ani.YOYO);
  animation.repeat(4);
}

void draw() {
  background(255);
  fill(0);
  ellipse(x,y,diameter,diameter);

  String txt = animation.getRepeatNumber()+" / "+animation.getRepeatCount();
  text(txt, x, y+diameter);

  text("Direction: "+animation.getDirection()+" PlayMode: "+animation.getPlayMode(), width/2,height-5);
}









