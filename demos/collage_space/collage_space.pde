/**
 * animated radial collage generator.
 * if you use your own footage, make sure to rename the files or adjust the folder names.
 * 
 * KEYS
 * 1-3                  : create a new animated collage layer
 * s                    : save png
 * 
 * Ani (a processing animation library) 
 * Copyright (c) 2010 Benedikt Gross
 * http://www.looksgood.de/libraries/Ani
 * 
 * based on P_4_2_1_02.pde out of the book
 * Generative Gestaltung, ISBN: 978-3-87439-759-9
 * Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
 * http://www.generative-gestaltung.de
 */

import processing.opengl.*; 
import de.looksgood.ani.*;

ArrayList layer1Images, layer2Images, layer3Images;
ArrayList layer1Items, layer2Items, layer3Items;


void setup() {
  size(800, 600, OPENGL);
  imageMode(CENTER);
  background(255);

  layer1Images = new ArrayList();
  layer2Images = new ArrayList();
  layer3Images = new ArrayList();

  layer1Items = new ArrayList();
  layer2Items = new ArrayList();
  layer3Items = new ArrayList();

  Ani.init(this);

  // ------ load images ------
  layer1Images = loadImages(sketchPath+"/data/sky/");
  layer2Images = loadImages(sketchPath+"/data/paper/");
  layer3Images = loadImages(sketchPath+"/data/urban/");

  // create fist layer, press 2-3 for more
  editCollageItems(layer1Items, layer1Images, (int)random(75,250), 0.25,1);
}

void draw() {
  // draw collage
  background(255); 
  for (int i = 0 ; i < layer1Items.size(); i++) ((CollageItem)layer1Items.get(i)).draw();
  for (int i = 0 ; i < layer2Items.size(); i++) ((CollageItem)layer2Items.get(i)).draw();   
  for (int i = 0 ; i < layer3Items.size(); i++) ((CollageItem)layer3Items.get(i)).draw();
  //println(frameRate+" "+layer1Items.size()+" "+layer2Items.size()+" "+layer3Items.size()+" "+Ani.size());
}

// ------ interactions ------
void keyPressed() {
  if (key == 's' || key == 'S') saveFrame(timestamp()+"_####.png");

  if (key == '1') editCollageItems(layer1Items, layer1Images, (int)random(75,250), 0.25,1);
  if (key == '2') editCollageItems(layer2Items, layer2Images, (int)random(30,150), 0.5,2);
  if (key == '3') editCollageItems(layer3Items, layer3Images, (int)random(20,35), 0,1.5);
}
// ------ collage items helper functions ------
void editCollageItems(ArrayList theItems, ArrayList theImages, int theCount, float theScaleStart, float theScaleEnd) {
  for (int i = 0 ; i < theCount; i++) {
    if (theCount > theItems.size()) {
      theItems.add( new CollageItem() );
    }
    CollageItem tmpItem = (CollageItem) theItems.get(i);
    tmpItem.img = (PImage) theImages.get(i%theImages.size());
    tmpItem.scaleStart = theScaleStart;
    tmpItem.scaleEnd = theScaleEnd;
    tmpItem.update();
  }
}

// ------ load the images in arraylists ------
ArrayList loadImages(String thePath) {
  println("\nloadImages: "+thePath);
  File dir = new File(thePath);
  ArrayList images = new ArrayList();
  if (dir.isDirectory()) {
    String[] contents = dir.list();
    for (int i = 0 ; i < contents.length; i++) {
      // skip hidden files and folders starting with a dot, load .png files only
      if (contents[i].charAt(0) == '.') continue;
      else if (contents[i].toLowerCase().endsWith(".png")) {
        File childFile = new File(dir, contents[i]);        
        images.add(loadImage(childFile.getPath()));
        println(images.size()+" "+contents[i]+"  "+childFile.getPath());          
      }
    }
  }
  return images;
}

// timestamp
String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}
