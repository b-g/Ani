/**
 * This example is a workaround to get Ani running on android.
 * Basically the example mimics the registerPre() mechanism (to hook into
 * the draw loop from a library). Unfortunatelly registerPre() is not
 * available at processing for android. This sketch is a hack to get around
 * this issue: More infos here:
 * 
 * http://wiki.processing.org/w/Android#API_Changed.2C_Gone.2C_or_Forgotten
 * https://forum.processing.org/topic/android-api-changes-registering-methods-make-library-also-working-on-android
 * https://github.com/b-g/Ani/issues/1#issuecomment-4799082
 */

import de.looksgood.ani.*;

float x = 256;
float y = 256;

ArrayList<Ani> anis; // workaround
ArrayList anisToUnregister; // workaround

void setup() {
  size(512, 512);
  smooth();
  noStroke();
  
  Ani.init(this);
  
  anis = new ArrayList<Ani>(); // workaround
  anisToUnregister = new ArrayList(); // workaround
}

void draw() {
  background(255);
  fill(0);
  ellipse(x, y, 120, 120);
  
  updateAnis(); // workaround
}

void mouseReleased() {
  Ani.to(this, 1.5, "x", mouseX);
  Ani.to(this, 1.5, "y", mouseY);
}

// workaround -------------------------------------------
void updateAnis(){
  if (anis.size() == 0) return;
  
  for (int i=0; i < anis.size(); i++) {
    Ani aniTmp = (Ani)anis.get(i);
    aniTmp.pre();
  }

  if(anisToUnregister.size() > 0) {
      for (int i=0; i < anisToUnregister.size(); i++) {
        anis.remove(i);
        anisToUnregister.remove(i);
        println("removed");
      }
  }
  println(anis.size());
}

void registerPre(Object obj) {
  anis.add( (Ani)obj );
}

void unregisterPre(Object obj) {
  int index = anis.indexOf(  (Ani)obj );
  anisToUnregister.add(index);
}
// workaround -------------------------------------------
