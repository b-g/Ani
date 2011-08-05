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

/**
 * All Constants of the Ani library.
 */
public interface AniConstants {
		
		public static final String VERSION = "1.3";
		
		// timeMode
		public static final String SECONDS = "SECONDS";
		public static final String FRAMES = "FRAMES";

		// callback, keywords for the property list string parsing
		public static final String ON_START = "onStart";
		public static final String ON_END = "onEnd";

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
		public static final String LINEAR = "linearEasingNone";
		public static final String QUAD_IN = "quadEasingIn";
		public static final String QUAD_OUT = "quadEasingOut";
		public static final String QUAD_IN_OUT = "quadEasingInOut";
		public static final String CUBIC_IN = "cubicEasingIn";
		public static final String CUBIC_IN_OUT = "cubicEasingInOut";
		public static final String CUBIC_OUT = "cubicEasingOut";
		public static final String QUART_IN = "quartEasingIn";
		public static final String QUART_OUT = "quartEasingOut";
		public static final String QUART_IN_OUT = "quartEasingInOut";
		public static final String QUINT_IN = "quintEasingIn";
		public static final String QUINT_OUT = "quintEasingOut";
		public static final String QUINT_IN_OUT = "quintEasingInOut";
		public static final String SINE_IN = "sineEasingIn";
		public static final String SINE_OUT = "sineEasingOut";
		public static final String SINE_IN_OUT = "sineEasingInOut";
		public static final String CIRC_IN = "circEasingIn";
		public static final String CIRC_OUT = "circEasingOut";
		public static final String CIRC_IN_OUT = "circEasingInOut";
		public static final String EXPO_IN = "expoEasingIn";
		public static final String EXPO_OUT = "expoEasingOut";
		public static final String EXPO_IN_OUT = "expoEasingInOut";
		public static final String BACK_IN = "backEasingIn";
		public static final String BACK_OUT = "backEasingOut";
		public static final String BACK_IN_OUT = "backEasingInOut";
		public static final String BOUNCE_IN = "bounceEasingIn";
		public static final String BOUNCE_OUT = "bounceEasingOut";
		public static final String BOUNCE_IN_OUT = "bounceEasingInOut";
		public static final String ELASTIC_IN = "elasticEasingIn";
		public static final String ELASTIC_OUT = "elasticEasingOut";
		public static final String ELASTIC_IN_OUT = "elasticEasingInOut";
}

