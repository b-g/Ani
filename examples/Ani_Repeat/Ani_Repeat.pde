/**
 * shows how to set the repeat count of animations
 * 
 */

import de.looksgood.ani.*;

Ani aniA, aniB, aniC;
float diaA = 0, diaB = 0, diaC = 0;

void setup() {
  size(600,300);
  smooth();
  noStroke();
  textAlign(CENTER);

  // Ani.init() must be called always first!
  Ani.init(this);

  aniA = new Ani(this, 2, "diaA", 100);

  aniB = new Ani(this, 2, "diaB", 100);
  // repeat 3 times
  aniB.repeat(3);

  aniC = new Ani(this, 2, "diaC", 100);
  // repeat forever
  aniC.repeat();
}

void draw() {
  background(255);
  fill(0);

  float x = width/3;
  float y = 150;

  translate(x/2,0);

  String txtA = "aniA: "+aniA.getRepeatNumber()+"/"+aniA.getRepeatCount();
  text(txtA, x*0, y+diaA);
  ellipse(x*0, y, diaA, diaA);

  String txtB = "aniB: "+aniB.getRepeatNumber()+"/"+aniB.getRepeatCount();
  text(txtB, x*1, y+diaB);
  ellipse(x*1, y, diaB, diaB);

  String txtC = "aniC: "+aniC.getRepeatNumber()+"/"+aniC.getRepeatCount();
  text(txtC, x*2, y+diaC);
  ellipse(x*2, y, diaC, diaC);

  println(aniA.isRepeating()+" "+aniB.isRepeating()+" "+aniC.isRepeating());
}















