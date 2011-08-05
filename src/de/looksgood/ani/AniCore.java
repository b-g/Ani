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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.looksgood.ani.easing.Easing;

import processing.core.PApplet;

/**
 * The Class AniCore encapsulates the core features for Ani and AniSequence, it's not recommended to use this class standalone!.
 */
public class AniCore implements AniConstants {
	private PApplet papplet;

	private String id;
	private String targetName;
	private String fieldName;

	private boolean isRegistered = false;
	private Object targetObject;
	private Field targetField;
	private Class<?> targetObjectFieldType;

	private float position;
	private float begin;
	private float change;
	private float end;

	private float beginTime;
	private float time;
	private float durationEasing;
	private float durationDelay;
	// durationEasing + durationDelay = durationTotal
	private float durationTotal;
	private String timeMode;

	// stopwatch timer for pause and resume
	private float pauseTime = 0;

	private String easingName;
	private Easing easing;
	private Class<?> easingClass;
	
	private Method callbackStartMethod;
	private Class<?> callbackStartParameterClass;
	private Method callbackFinishMethod;
	private Class<?> callbackFinishParameterClass;

	private String playMode = FORWARD;
	private String playDirection = FORWARD;
	private int repeatNumber; // inited in start()
	private int repeatCount = 1;

	private boolean isRepeating = false;
	private boolean isDelaying = false;
	private boolean isPlaying = false;
	private boolean isEnded = false;


	/**
	 * Instantiates a new ani core.
	 * 
	 * @param thePapplet the papplet
	 * @param theAutostart the autostart
	 * @param theTargetObject the target object
	 * @param theDurationEasing the duration easing
	 * @param theDurationDelay the duration delay
	 * @param theTargetObjectFieldName the target object field name
	 * @param theEnd the end
	 * @param theEasing the easing
	 * @param theTimeMode the time mode
	 * @param theCallback the callback
	 */
	public AniCore(PApplet thePapplet, String theAutostart, Object theTargetObject,
			float theDurationEasing, float theDurationDelay,
			String theTargetObjectFieldName, float theEnd, String theEasing,
			String theTimeMode, String theCallback) {

		papplet = thePapplet;
		targetObject = theTargetObject;
		// generate unique id
		targetName = targetObject.toString();
		fieldName = theTargetObjectFieldName;
		id = targetName + "_" + fieldName;

		end = theEnd;
		durationEasing = theDurationEasing;
		durationDelay = theDurationDelay;
		durationTotal = durationEasing + durationDelay;

		timeMode = theTimeMode;
		setEasing(theEasing);
		setCallback(theCallback);
		
		// set begin value to field value
		boolean setBeginSuccess = setBegin();
		if (setBeginSuccess && theAutostart == AUTOSTART) {
			start();
		}
	}
	
	/**
	 * Sets the begin of the animation to the current value of the target.
	 * 
	 * @return true, if successful
	 */
	public boolean setBegin() {
		// -- check and find fields --
		boolean foundType = false;
		boolean foundField = false;
		Class<?> targetClass = targetObject.getClass();
		
		// check field inheritance
		while (targetClass != null) {
			for (int i = 0; i < targetClass.getDeclaredFields().length; i++) {
				if (targetClass.getDeclaredFields()[i].getName().equals(fieldName)) {
					// get field type
					targetObjectFieldType = targetClass.getDeclaredFields()[i].getType();
					foundField = true;
					break;
				}
			}
			if (foundField) break;
			else targetClass = targetClass.getSuperclass();
		}
		
		// when running in applet mode. setAccessible(true) is not working for fields!
		// AccessControlException is thrown. therefore, make fields in your code public.
		if (foundField) {
			try {
				// get field
				targetField = targetClass.getDeclaredField(fieldName);
				// make accessible
				try {
					targetField.setAccessible(true);
				} catch (java.security.AccessControlException e) {
					printSecurityWarning(e);
				}
				// get field value + check type
				try {
					if (targetObjectFieldType == Float.TYPE) {
						begin = targetField.getFloat(targetObject);
						foundType = true;
					} else if (targetObjectFieldType == Integer.TYPE) {
						begin = new Float(targetField.getInt(targetObject));
						foundType = true;
					} else {
						String text = ANI_DEBUG_PREFIX + "Wrong Type @ AniCore "
						+ targetName + " ("
						+ targetField.getType().getName() + ")"
						+ " is not float or int";
						throw new ClassCastException(text);
					}
				} catch (Exception ex) {
					printSecurityWarning(ex);
				}
			} catch (NoSuchFieldException e) {
				System.out.println(ANI_DEBUG_PREFIX + " Error @ AniCore. " + e);
			}
		}

		if (foundType && foundField) {
			change = end - begin;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the begin of the animation to a new value.
	 * 
	 * @param theBegin the new begin
	 */
	public void setBegin(float theBegin) {
		begin = theBegin;
		change = end - begin;
	}

	/**
	 * setup the callback methods: onStart and onEnd.
	 * 
	 * @param theCallback the names of the callbacks for onStart and onEnd
	 */
	public void setCallback(String theCallback) {
		// ignore empty strings
		if (theCallback.length() > 0) {
			// parse the string
			String[] propertyList = PApplet.split(theCallback,',');
			for (int i = 0; i < propertyList.length; i++) {
				String[] p = PApplet.split(PApplet.trim(propertyList[i]),':');

				if (p.length == 2) {
					if (p[0].equals(ON_START) || p[0].equals(ON_END)) {

						// -- check and find methods --
						String targetMethodName = p[1];
						boolean foundMethod = false;
						Class<?> targetClass = targetObject.getClass();
						Method targetMethod = null;
						Class<?> tagetMethodParameterClass = null;

						// check method inheritance
						while (targetClass != null) {
							for (int ii = 0; ii < targetClass.getDeclaredMethods().length; ii++) {
								//System.out.println(targetClass.getDeclaredMethods()[i].getName());
								if (targetClass.getDeclaredMethods()[ii].getName().equals(targetMethodName)) {
									if (targetClass.getDeclaredMethods()[ii].getParameterTypes().length == 1) {
										if (targetClass.getDeclaredMethods()[ii].getParameterTypes()[0] == Ani.class) {
											tagetMethodParameterClass = Ani.class;
											foundMethod = true;
											break;
										}
									} else if (targetClass.getDeclaredMethods()[ii].getParameterTypes().length == 0) {
										tagetMethodParameterClass = null;
										foundMethod = true;
										break;
									}
								}
							}
							if (foundMethod) break;
							else targetClass = targetClass.getSuperclass();
						}

						// setup callback method
						if (foundMethod) {
							try {
								Class<?>[] args = (tagetMethodParameterClass == null) ? new Class[] {}
								: new Class[] {Ani.class};
								targetMethod = targetClass.getDeclaredMethod(targetMethodName, args);
								targetMethod.setAccessible(true);

								// start or end callback
								if (p[0].equals(ON_START)) {
									callbackStartMethod = targetMethod;
									callbackStartParameterClass = tagetMethodParameterClass;
								} else if (p[0].equals(ON_END)) {
									callbackFinishMethod = targetMethod;
									callbackFinishParameterClass = tagetMethodParameterClass;
								}
							} catch (SecurityException e) {
								printSecurityWarning(e);
							} catch (NoSuchMethodException e) {
								System.out.println(ANI_DEBUG_PREFIX+" Error @ AniCore -> setCallbackMethods(). "+e);
							}

						} else {
							System.out.println(ANI_DEBUG_PREFIX+" Error @ AniCore -> setCallbackMethods(). Can't find a method of name: "+targetMethodName);
						}
					}
				}
			} 
		}
	}
	
	private void dispatchOnStart() {
		if (callbackStartMethod != null) {
			try {
				Object[] args = (callbackStartParameterClass == null) ? new Object[] {}
				: new Object[] { this };
				callbackStartMethod.invoke(targetObject, args);
			} catch (Exception e) {
				System.out.println(ANI_DEBUG_PREFIX+" Error @ AniCore -> dispatchOnStart(). "+e);
			}
		}
	}
	
	private void dispatchOnEnd() {
		if (callbackFinishMethod != null) {
			try {
				Object[] args = (callbackFinishParameterClass == null) ? new Object[] {}
				: new Object[] { this };
				callbackFinishMethod.invoke(targetObject, args);
			} catch (Exception e) {
				System.out.println(ANI_DEBUG_PREFIX+" Error @ AniCore -> dispatchOnFinish(). "+e);
			}
		}
	}
	

	private void printSecurityWarning(Exception theE) {
		// AccessControlException required for applets.
		System.out.print(ANI_DEBUG_PREFIX + " Error @ AniCore. ");
		if (papplet.online) {
			System.out.println("AccessControlException.\n"
					+ "you are probably running in applet mode.\n"
					+ "make sure fields and methods which you want to \n"
					+ "access in your processing code are public.\n");
		}
		System.out.println(theE);
	}

	/**
	 * No need to call ever this method. Only public to use the registerPre() mechanism
	 */
	public void pre() {
		if (isPlaying) {
			update();
		}
	}

	/**
	 * Start the animation.
	 */
	public void start() {
		// register pre()
		if (!isRegistered) {
			papplet.registerPre(this);
			isRegistered = true;
			repeatNumber = 1;
			dispatchOnStart();
		}
		seek(0.0f);
		isPlaying = true;
		isEnded = false;
	}

	/**
	 * End the animation.
	 */
	public void end() {
		isDelaying = false;
		seek(1.0f);
		isPlaying = false;
		isEnded = true;
		// unregister pre()
		if (isRegistered) {
			papplet.unregisterPre(this);
			isRegistered = false;
		}
		dispatchOnEnd();
	}

	private void update() {	
		setTime(getTime());
		
		// delay or easing?
		if (time < durationDelay) {
			isDelaying = true;			
			position = begin;
			
		} else {
			isDelaying = false;	
			if (time >= durationTotal) {
				if (isRepeating) {
					if (repeatCount == 1 || repeatNumber <= repeatCount-1 || repeatCount == -1) {
						if (playMode == YOYO) reverse();
						start();
						repeatNumber++;
					} else {
						isRepeating = false;
					}
				} else {
					end();
				}
				
			} else {
				updatePosition();
			}
		}
		updateTargetObjectField();
		//System.out.println("isEasing: "+isEasing+" isDelaying: "+isDelaying+" time: "+time+" position: "+position);
	}

	private void updatePosition() {
		try {
			position = easing.calcEasing(time - durationDelay, begin, change, durationEasing);
		} catch (Exception e) {
			System.out.println(ANI_DEBUG_PREFIX+" Error @ AniCore -> updatePosition(). "+e);
		}
	}

	private void updateTargetObjectField() {
		try {
			if (targetObjectFieldType == Float.TYPE) {
				targetField.setFloat(targetObject, position);
			} else if (targetObjectFieldType == Integer.TYPE) {
				targetField.setInt(targetObject, (int) position);
			}
		} catch (Exception e) {
			System.out.println(ANI_DEBUG_PREFIX+" Error @ AniCore -> updateTargetObjectField(). "+e);
		}
	}

	private float getTime() {
		return (timeMode == SECONDS) ? ((papplet.millis() - beginTime) / 1000)
				: (papplet.frameCount - beginTime);
	}

	private void setTime(float theTime) {
		time = theTime;
		beginTime = (timeMode == SECONDS) ? (papplet.millis() - time * 1000)
				: (papplet.frameCount - time);
	}

	/**
	 * Pause the animation at the current position in time.
	 */
	public void pause() {
		isPlaying = false;
		pauseTime = getTime();
	}

	/**
	 * Resume the animation from the current position in time (adjustable with seek).
	 */
	public void resume() {
		if (!isRegistered) {
			papplet.registerPre(this);
			isRegistered = true;
		}
		if (!isPlaying && !isEnded) {
			isPlaying = true;
			// remember the pause time, seek to last time
			seek(pauseTime / durationTotal);
		}
	}

	/**
	 * Seek the Animation to any position: start = 0.0 end = 1.0
	 * 
	 * @param theValue seek value
	 */
	public void seek(float theValue) {
		// clamp between 0 and 1
		theValue = PApplet.constrain(theValue, 0.0f, 1.0f);
		setTime(theValue * durationTotal);
		pauseTime = time; // overwrite old pause time
		isEnded = false;
		// only use easing function to calc position if time > durationDelay
		if (time < durationDelay) {			
			position = begin;
		} else {
			updatePosition();
		}
		updateTargetObjectField();
	}
	
	/**
	 * Gets the current seek value (start = 0.0 end = 1.0)
	 * 
	 * @return theValue seek value
	 */
	public float getSeek() {
		return PApplet.constrain(time/durationTotal, 0.0f, 1.0f);
	}

	/**
	 * Swap begin end of the animation.
	 */
	public void reverse() {
		float beginTemp = begin;
		float endTemp = end;
	
		begin = endTemp;
		end = beginTemp;
		change = end - begin;
		
		if (playDirection == FORWARD) {
			playDirection = BACKWARD;
		}
		else if (playDirection == BACKWARD) {
			playDirection = FORWARD;
		}
	}

	/**
	 * Gets the time mode.
	 * 
	 * @return the time mode
	 */
	public String getTimeMode() {
		return timeMode;
	}

	/**
	 * Sets the time mode: SECONDS or FRAMES.
	 * 
	 * @param theTimeMode the new time mode
	 */
	public void setTimeMode(String theTimeMode) {
		timeMode = theTimeMode;
	}

	/**
	 * Gets the name of the easing.
	 * 
	 * @return the easing name
	 */
	public String getEasing() {
		return easingName;
	}

	/**
	 * Sets the easing: LINEAR, QUAD_IN, QUAD_OUT, QUAD_IN_OUT, CUBIC_IN, CUBIC_IN_OUT, CUBIC_OUT, QUART_IN, QUART_OUT, QUART_IN_OUT, QUINT_IN, QUINT_OUT, QUINT_IN_OUT, SINE_IN, SINE_OUT, SINE_IN_OUT, CIRC_IN, CIRC_OUT, CIRC_IN_OUT, EXPO_IN, EXPO_OUT, EXPO_IN_OUT, BACK_IN, BACK_OUT, BACK_IN_OUT, BOUNCE_IN, BOUNCE_OUT, BOUNCE_IN_OUT, ELASTIC_IN, ELASTIC_OUT, ELASTIC_IN_OUT.
	 * 
	 * @param theEasingName new name of the easing
	 */
	public void setEasing(String theEasingName) {
		easingName = theEasingName;
		int index = easingName.indexOf("Easing");
		String easingClassName = "de.looksgood.ani.easing."
				+ firstCharToUpperCase(easingName.substring(0, index));
		try {
			easingClass = Class.forName(easingClassName);
			easing = (Easing) easingClass.newInstance();
			if (easingName.endsWith("In")) easing.setMode(IN);
			else if (easingName.endsWith("InOut")) easing.setMode(IN_OUT);
			else easing.setMode(OUT);	
		} catch (Exception e) {
			System.out.println(ANI_DEBUG_PREFIX+" Error @ AniCore. "+e);
		}
	}

	/**
	 * Gets the play mode.
	 * 
	 * @return the play mode
	 */
	public String getPlayMode() {
		return playMode;
	}

	/**
	 * Sets the play mode: FORWARD, BACKWARD, YOYO.
	 * 
	 * @param thePlayMode the new play mode
	 */
	public void setPlayMode(String thePlayMode) {
		String oldPlayDirection = playDirection;
		
		if (thePlayMode == FORWARD) {
			if (oldPlayDirection == BACKWARD) reverse();
			playMode = playDirection = FORWARD;
		}
		else if (thePlayMode == BACKWARD) {
			if (oldPlayDirection == FORWARD) reverse();
			playMode = playDirection = BACKWARD;
		}
		else if (thePlayMode == YOYO) {
			playMode = YOYO;
		}
	}


	/**
	 * Repeat the animation forever.
	 */
	public void repeat() {
		isRepeating = true;
		repeatCount = -1;
	}
	
	/**
	 * Stop any repeating.
	 */
	public void noRepeat() {
		isRepeating = false;
		repeatCount = 1;
	}

	/**
	 * Sets the repeat count.
	 * 
	 * @param theRepeatCount the new repeat count
	 */
	public void repeat(int theRepeatCount) {
		if (theRepeatCount > 1) {
			isRepeating = true;
			repeatCount = theRepeatCount;
		} else {
			isRepeating = false;
			repeatCount = 1;
		}
	}
	
	/**
	 * Gets the repeat count.
	 * 
	 * @return the repeat count
	 */
	public int getRepeatCount() {
		return repeatCount;
	}
	
	/**
	 * Gets the current repeat number.
	 * 
	 * @return the repeat count
	 */
	public int getRepeatNumber() {
		return repeatNumber;
	}
	
	/**
	 * Gets the direction.
	 * 
	 * @return the direction
	 */
	public String getDirection() {
		return playDirection;
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public float getPosition() {
		return position;
	}

	/**
	 * Gets the begin.
	 * 
	 * @return the begin
	 */
	public float getBegin() {
		return begin;
	}

	/**
	 * Sets the end.
	 * 
	 * @param theEnd the new end
	 */
	public void setEnd(float theEnd) {
		end = theEnd;
		change = end - begin;
	}

	/**
	 * Gets the end.
	 * 
	 * @return the end
	 */
	public float getEnd() {
		return end;
	}

	/**
	 * Gets the duration: duration easing + duration delay.
	 * 
	 * @return the duration total
	 */
	public float getDurationTotal() {
		return durationTotal;
	}

	/**
	 * Gets the duration of delay.
	 * 
	 * @return the delay
	 */
	public float getDelay() {
		return durationDelay;
	}

	/**
	 * Sets the delay duration.
	 * 
	 * @param theDurationDelay the new delay duration
	 */
	public void setDelay(float theDurationDelay) {
		durationDelay = theDurationDelay;
		durationTotal = durationEasing + durationDelay;
	}

	/**
	 * Gets the duration.
	 * 
	 * @return the duration
	 */
	public float getDuration() {
		return durationEasing;
	}

	/**
	 * Sets the duration.
	 * 
	 * @param theDurationEasing the new duration
	 */
	public void setDuration(float theDurationEasing) {
		durationEasing = theDurationEasing;
		durationTotal = durationEasing + durationDelay;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Checks if the animation is ended.
	 * 
	 * @return true, if the animation is ended
	 */
	public boolean isEnded() {
			return isEnded;
	}

	/**
	 * Checks if the animation is repeating.
	 * 
	 * @return true, if the animation is repeating
	 */
	public boolean isRepeating() {
		return isRepeating;
	}

	/**
	 * Checks if the animation is delaying.
	 * 
	 * @return true, if the animation is delaying
	 */
	public boolean isDelaying() {
		return isDelaying;
	}

	/**
	 * Checks if the animation is playing.
	 * 
	 * @return true, if the animation is playing
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	private String firstCharToUpperCase(String theC) {
		return Character.toUpperCase(theC.charAt(0)) + theC.substring(1);
	}
}
