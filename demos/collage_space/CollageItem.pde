class CollageItem {
  // polar coordinates
  float a, l;

  // image
  float rotation, newRotation;
  float scaling, newScaling;
  float scaleStart, scaleEnd;

  PImage img = new PImage();

  // animation
  float x=0, y=0, newX=0, newY=0;
  Ani aniX, aniY, aniRotation, aniScaling;

  CollageItem() {
    init();
  }

  void init() {
    x = width/2;
    y = height/2;
    scaling = 0.1;
    l = width;
  }

  void randomize() {
    a = random(TWO_PI);
    newScaling = random(scaleStart,scaleEnd);
    //float tmpRotation = AniUtil.shortRotation(rotation,newRotation);
    newRotation = a + random(-PI,PI);
  }

  void update() {
    init();
    randomize();
    newX  = width/2 + cos(a) * l;
    newY  = height/2 + sin(a) * l;
    aniX = Ani.to(this, random(5,10), "x", newX, Ani.QUAD_IN_OUT);
    aniY = Ani.to(this, random(5,10), "y", newY, Ani.QUAD_IN_OUT); 
    aniRotation = Ani.to(this, random(5,10), "rotation", newRotation, Ani.QUAD_IN_OUT, "onEnd:update");
    aniScaling = Ani.to(this, random(1,5), "scaling", newScaling, Ani.SINE_IN_OUT);
  } 

  void draw() {
    pushMatrix();
    translate(x, y);
    rotate(rotation);
    scale(scaling);
    image(img, 0,0);
    popMatrix();
  }
}





