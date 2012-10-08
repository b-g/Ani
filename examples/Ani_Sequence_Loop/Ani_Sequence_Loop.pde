/**
 * shows how to loop a whole sequence
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
  background(255);

  x = 50;
  y = 50;
  diameter = 10;

  // Ani.init() must be called always first!
  Ani.init(this);

  // create a sequence
  // dont forget to call beginSequence() and endSequence()
  seq = new AniSequence(this);
  seq.beginSequence();
  
  // step 0
  seq.add(Ani.to(this, 1, "diameter", 55));

  // step 1
  seq.add(Ani.to(this, 2, "x:400,y:100"));
  
  // step 2
  seq.add(Ani.to(this, 1, "x:450,y:400"));
  
  // step 3
  seq.add(Ani.to(this, 1, "x:100,y:450"));
  
  // step 4
  seq.beginStep();
  seq.add(Ani.to(this, 1, "x:50,y:50"));
  seq.add(Ani.to(this, 2, "diameter", 5, Ani.EXPO_OUT, "onEnd:sequenceEnd"));
  seq.endStep();

  seq.endSequence();

  // start the whole sequence
  seq.start();
}

void draw() {
  fill(255,5);
  rect(0,0,width,height);
  
  //println(seq.isFinished());

  fill(0);
  ellipse(x,y,diameter,diameter);
}

void sequenceEnd() {
  //println("sequenceEnd() restart all again");
  seq.start();
}

// pause and resume animation by pressing SPACE
// or press "s" to start the sequence
void keyPressed() {
  if (key == 's' || key == 'S') seq.start();
  if (key == ' ') {
    if (seq.isPlaying()) seq.pause();
    else seq.resume();
  }
}






