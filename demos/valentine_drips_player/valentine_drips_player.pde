/**
 * this sketch plays back the recorded values of valentine_drips_player.pde
 * 
 * KEYS
 * space               : restart
 * 
 * Ani (a processing animation library) 
 * Copyright (c) 2010 Benedikt Groß
 * 
 * http://www.looksgood.de/libraries/Ani
 */


import de.looksgood.ani.*;

ArrayList drips;
float x, y, diameter, col;
color currentColor;

AniSequence seq;
String easing = Ani.QUAD_IN_OUT;

void setup() {
  // use full screen size 
  //size(screen.width, screen.height);
  size(600,800);
  background(255);
  smooth();
  noStroke();
  cursor(CROSS);
  drips = new ArrayList();

  Ani.init(this);

  // create an empty sequence
  seq = new AniSequence(this);
  seq.beginSequence();

  // load the data and put it in the sequence
  String[] tokens = loadStrings("recording.txt");
  for (int i = 0; i < tokens.length; i++) { 
    String[] rawValues = split(tokens[i],";");
    float[] values = new float[rawValues.length];
    for (int ii = 0; ii < values.length; ii++) values[ii] = new Float(rawValues[ii]);
    if (i == 0) {
      x = values[0];
      y = values[1];
      diameter = values[3];
      col = values[4];
    } 
    else { 
      seq.beginStep();
      seq.add(Ani.to(this, values[2], "x", values[0], easing ));
      seq.add(Ani.to(this, values[2], "y", values[1], easing ));
      seq.add(Ani.to(this, 0.1, "diameter", values[3], easing ));
      seq.add(Ani.to(this, values[2]/2, "col", values[4], easing ));
      seq.endStep();
    }
  }
  seq.endSequence();
  // go!
  seq.start();

  //seq.pause();
  //seq.seek(0.70);
  //seq.resume();
}


void draw() {
  if (seq.isEnded() == false) {
    currentColor = lerpColor(color(0,0,0),color(237,77,53),col);
    // create new drips
    if (random(10) > 8.75 && diameter > 0) {
      drips.add(new Drip(x,y+diameter/2,currentColor));
    }

    for (int i = 0; i < drips.size(); i++) { 
      Drip tmp = (Drip) drips.get(i);
      tmp.draw();
    }
    fill(currentColor);
    ellipse(x,y,diameter,diameter);
  } 
  else {
    restart();
  }
}

void restart() {
  delay(7000);
  // kill all anis
  Ani.killAll();
  // kill all drips
  for (int j = drips.size()-1; j >= 0 ; j--) { 
    drips.remove(j);
  }
  // restart sequence
  seq.start();
  background(255);
}

// press SPACE to start/restart the sequence
void keyPressed() {
  if (key == ' ') {
    restart();
  }
}




