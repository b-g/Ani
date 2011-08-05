/**
 * shows how to seek a sequence of animations in time
 * 	 
 * MOUSE
 * drag           : move from left to right to seek the sequence
 * 
 * KEYS
 * space           : toggle, pause and resume sequence
 * s               : start or restart sequence
 */


import de.looksgood.ani.*;

float x, y, diameter;

AniSequence seq;

void setup() {
  size(512,512);
  smooth();
  noStroke();
  textAlign(CENTER);

  x = 50;
  y = 50;
  diameter = 10;

  // Ani.init() must be called always first!
  Ani.init(this);

  // create a sequence
  seq = new AniSequence(this);
  seq.beginSequence();

  seq.beginStep();
  seq.add(Ani.to(this, 1, "diameter", 55));
  seq.endStep();
  seq.add(Ani.to(this, 2, "x:400,y:100"));
  seq.add(Ani.to(this, 1, "x:450,y:400"));
  seq.add(Ani.to(this, 1, "x:100,y:450"));
  seq.beginStep();
  seq.add(Ani.to(this, 1, "x:50,y:50"));
  seq.add(Ani.to(this, 2, "diameter", 5));
  seq.endStep();

  seq.endSequence();
  seq.start();

  println("total length of this sequence: "+seq.getDuration());
}

void draw() {
  background(255);
  // calc seek value
  if (mousePressed == true) {
    float seekValue = mouseX/(float)width;
    seq.seek(seekValue);
  }

  fill(0);
  ellipse(x,y,diameter,diameter);
  text((int)x+" "+(int)y ,x,y+diameter);

  if (seq.isEnded()) println("done");
  else println("current time: "+seq.getTime()+"  current step: "+seq.getStepNumber()+" of "+seq.getStepCount());
}

// pause
void mousePressed() {
  seq.pause();
}

// resume
void mouseReleased() {
  seq.resume();
}

// pause and resume animation by pressing SPACE
// or press "s" to start/restart the sequence
void keyPressed() {
  if (key == 's' || key == 'S') seq.start();
  if (key == ' ') {
    if (seq.isPlaying()) seq.pause();
    else seq.resume();
  }
}









