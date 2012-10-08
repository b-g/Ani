/*
Ani (a processing animation library) 
Copyright (c) 2010 Benedikt Gro§

http://www.looksgood.de/libraries/Ani

Standing on the shoulders of giants:
Jack Doyle - TweenLite AS3 Library (http://blog.greensock.com/tweenlite/)
Robert Penner - Equations (http://robertpenner.com/easing/)
Andreas Schlegel - ControlP5 (http://www.sojamo.de/libraries/);
Ekene Ijeoma - Tween Processin Library (http://www.ekeneijeoma.com/processing/tween/)

AniCore, Ani and AniSequence includes many ideas and code of the nice people above!
Thanks a lot for sharing your code with the rest of the world!

This library is free software; you can redistribute it and/or modify it under the terms 
of the GNU Lesser General Public License as published by the Free Software Foundation; 
either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this 
library; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, 
Boston, MA 02110, USA
*/

package de.looksgood.ani;

import de.looksgood.ani.easing.*;

/**
 * All Constants of the Ani library.
 */
public interface AniConstants {
		
		public static final String VERSION = "2.3";
		
		// timeMode
		public static final String SECONDS = "SECONDS";
		public static final String FRAMES = "FRAMES";

		// callback, keywords for the property list string parsing
		public static final String ON_START = "onStart";
		public static final String ON_END = "onEnd";
		public static final String ON_DELAY_END = "onDelayEnd";

		// playMode
		public static final String FORWARD = "FORWARD";
		public static final String BACKWARD = "BACKWARD";
		public static final String YOYO = "YOYO";

		// autoStartMode
		public static final String AUTOSTART = "AUTOSTART";
		public static final String NO_AUTOSTART = "NO_AUTOSTART";

		// overwriteMode
		public static final String OVERWRITE = "OVERWRITE";
		public static final String NO_OVERWRITE = "NO_OVERWRITE";

		// debug out
		public static final String ANI_DEBUG_PREFIX = "### Ani Debug -> ";
		
		// easings mode
		public static final int IN = 0;
		public static final int IN_OUT = 2;
		public static final int OUT = 1;

		// names of easings
		public static final Easing LINEAR =         new Linear();
		public static final Easing QUAD_IN =        new Quad(IN);
		public static final Easing QUAD_OUT =       new Quad(OUT);
		public static final Easing QUAD_IN_OUT =    new Quad(IN_OUT);
		public static final Easing CUBIC_IN =       new Cubic(IN);
		public static final Easing CUBIC_OUT =      new Cubic(OUT);
		public static final Easing CUBIC_IN_OUT =   new Cubic(IN_OUT);
		public static final Easing QUART_IN =       new Quart(IN);
		public static final Easing QUART_OUT =      new Quart(OUT);
		public static final Easing QUART_IN_OUT =   new Quart(IN_OUT);
		public static final Easing QUINT_IN =       new Quint(IN);
		public static final Easing QUINT_OUT =      new Quint(OUT);
		public static final Easing QUINT_IN_OUT =   new Quint(IN_OUT);
		public static final Easing SINE_IN =        new Sine(IN);
		public static final Easing SINE_OUT =       new Sine(OUT);
		public static final Easing SINE_IN_OUT =    new Sine(IN_OUT);
		public static final Easing CIRC_IN =        new Circ(IN);
		public static final Easing CIRC_OUT =       new Circ(OUT);
		public static final Easing CIRC_IN_OUT =    new Circ(IN_OUT);
		public static final Easing EXPO_IN =        new Expo(IN);
		public static final Easing EXPO_OUT =       new Expo(OUT);
		public static final Easing EXPO_IN_OUT =    new Expo(IN_OUT);
		public static final Easing BACK_IN =        new Back(IN);
		public static final Easing BACK_OUT =       new Back(OUT);
		public static final Easing BACK_IN_OUT =    new Back(IN_OUT);
		public static final Easing BOUNCE_IN =      new Bounce(IN);
		public static final Easing BOUNCE_OUT =     new Bounce(OUT);
		public static final Easing BOUNCE_IN_OUT =  new Bounce(IN_OUT);
		public static final Easing ELASTIC_IN =     new Elastic(IN);
		public static final Easing ELASTIC_OUT =    new Elastic(OUT);
		public static final Easing ELASTIC_IN_OUT = new Elastic(IN_OUT);
		
}

