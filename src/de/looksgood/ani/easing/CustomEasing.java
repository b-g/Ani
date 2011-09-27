/**********************************************************************
this class is a port of greensock's excellent customease class
http://www.greensock.com/customease/

*****************************************/

package de.looksgood.ani.easing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import processing.core.PApplet;

public class CustomEasing extends Easing {
	private static HashMap<String, CustomEasing> customEasingsLookup = new HashMap<String, CustomEasing>();
	private ArrayList<Segment> segments = new ArrayList<CustomEasing.Segment>();
	private String name;
	
	private class Segment{
		public float s;
		public float cp;
		public float e;
		
		public Segment(float s, float cp, float e) {
			this.s = s;
			this.cp = cp;
			this.e = e;
		}
	}

	public CustomEasing(String theName, String theSegments){
		// no use here for easingMode ... just inherited from superclass
		setMode(easingMode);

		this.name = theName;
		this.segments = new ArrayList<CustomEasing.Segment>();

		// parse theSegments string in property list style 
		// e.g. [{s:0,cp:0.54,e:0.748},{s:0.748,cp:0.956,e:0.832},{s:0.832,cp:0.708,e:1}]
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(theSegments.toLowerCase());
		
		while (regexMatcher.find()) {
			String segmentString = regexMatcher.group();
			segmentString = segmentString.replace( '{', ' ' );
			segmentString = segmentString.replace( '}', ' ' );
			segmentString = PApplet.trim(segmentString);

			String[] segmentPropertiesString = PApplet.split(segmentString,',');
			HashMap<String, Float> segmentProperties = new HashMap<String, Float>();
			for (int i = 0; i < segmentPropertiesString.length; i++) {
				String[] p = PApplet.split(PApplet.trim(segmentPropertiesString[i]),':');
				segmentProperties.put( PApplet.trim(p[0]), Float.parseFloat(p[1]) );
			}
			segments.add(new Segment(segmentProperties.get("s"), 
									 segmentProperties.get("cp"), 
									 segmentProperties.get("e") ));
		}

		customEasingsLookup.put(name, this);
		//System.out.println(ANI_DEBUG_PREFIX+" Info @ CustomEasing -> add CustomEasing: "+theName);
	}
	
	public static CustomEasing create(String theName, String theSegments) {
		CustomEasing tmp = new CustomEasing(theName, theSegments);
		return tmp;
	}
	
	public static CustomEasing byName(String theName) {
		return customEasingsLookup.get(theName);
	}
	
	public float easeIn(float t, float b, float c, float d) {
		return easeBezier(t, b, c, d);
	}

	public float easeOut(float t, float b, float c, float d) {
		return easeBezier(t, b, c, d);
	}

	public float easeInOut(float t, float b, float c, float d) {
		return easeBezier(t, b, c, d);
	}
	private float easeBezier(float t, float b , float c, float d) {
		float factor = t / d; 
		int qty = segments.size(); 
		int i = (int)(qty * factor);
		i = PApplet.min(i, segments.size()-1);
		float time = (factor - (i * (1 / (float)qty))) * qty;
		Segment s = segments.get(i);
		//System.out.println("tmp: "+time);
		return b + c * (s.s + time * (2 * (1 - time) * (s.cp - s.s) + time * (s.e - s.s)));
	}

}
