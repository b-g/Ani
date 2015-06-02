/*
Ani (a processing animation library) 
Copyright (c) 2010 Benedikt Groß

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

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * The Class AniSequence creates sequences out of instances of Ani. 
 * Each step is either one single Ani or multiple Anis at the same time (in parallel).
 * Please note: AniSequence expects that you add only Anis which do have playMode/repeatMode set to BACKWARD, YOYO, COUNT and FOREVER!
 */
public class AniSequence {
	private PApplet papplet;
	private ArrayList<Step> steps = new ArrayList<Step>();
	private ArrayList<Ani> addParallelAnisCollector = new ArrayList<Ani>();
	private boolean addParallel = false;
	private boolean isPlaying = false;
	private boolean isEnded = false;
	private int currentStep;
	private float durationTotal; // sum of all durations for each step
	private float time;
	
	// -- Step (class to encapsulate multiple Ani instances) --
	private class Step{
		public ArrayList<Ani> anis = new ArrayList<Ani>();
		public int stepLength;
		public float duration;
		public float startTime;
		public float endTime;
		
		public Step(Ani _ani){
			anis.add(_ani);
			init();
		}
		
		public Step(Ani[] _anis){
			for (int i=0; i < _anis.length; i++) {
				anis.add(_anis[i]);
			}
			init();
		}

		private void init() {
			stepLength = anis.size();
			duration = 0;
			for (int i=0; i < stepLength; i++) {
				Ani tmpAni = anis.get(i);
				tmpAni.setBegin();
				tmpAni.seek(1.0f);
				// get the longest durationTotal of all anis in this step
				duration = PApplet.max(tmpAni.getDurationTotal(),duration);
			}
			//System.out.println(durationTotal+" "+stepLength);
		}
		public boolean isFinished() {
			boolean isFinished = true;
			for (int i=0; i < stepLength; i++) {
				Ani tmpAni = anis.get(i);
				isFinished &= tmpAni.isEnded();
			}
			return isFinished;
		}
		public void start() {
			for (int i=0; i < stepLength; i++) anis.get(i).start();
		}
		
		public void seekAll(Float _value) {
			for (int i=0; i < stepLength; i++) {
				Ani tmpAni = anis.get(i);
				tmpAni.seek(_value);
			}
		}
		public void seek(Float _value) {
			float seekTime = _value * duration;
			for (int i=0; i < stepLength; i++) {
				Ani tmpAni = anis.get(i);
				float aniSeekValue = PApplet.map(seekTime, 0.0f,tmpAni.getDurationTotal(), 0.0f, 1.0f);
				tmpAni.seek(aniSeekValue);
			}
		}
		public float getTime() {
			float currentTime = 0.0f;
			for (int i=0; i < stepLength; i++) {
				Ani tmpAni = anis.get(i);
				float seekValue = tmpAni.getSeek()*tmpAni.getDurationTotal();
				currentTime = PApplet.max(seekValue,currentTime);
			}
			return currentTime;
		}
		public void play() {
			for (int i=0; i < stepLength; i++) anis.get(i).resume();
		}
		public void pause() {
			for (int i=0; i < stepLength; i++) anis.get(i).pause();
		}
	}
	// -- end Step --
	
	/**
	 * Instantiates a new ani sequence.
	 * 
	 * @param thePapplet
	 */
	public AniSequence(PApplet thePapplet){
		papplet = thePapplet;
		papplet.registerMethod("pre", this);
	}
	
	/**
	 * No need to call ever this method. Only public to use the registerMethod() mechanism
	 */
	public void pre() {
		if (isPlaying) {
			update();
		}
	}
	
	private void update() {		
		if (steps.size() > 0) {
			Step tmpStep = steps.get(currentStep);
			// is current step finished? if so, start next step
			if (tmpStep.isFinished() && currentStep < steps.size()-1) {
				currentStep++;
				Step nextStep = steps.get(currentStep);
				nextStep.start();
				tmpStep = nextStep;
			} 
			else if (currentStep == steps.size()-1) {
				isEnded = tmpStep.isFinished();
			}
			
			time = tmpStep.startTime + tmpStep.getTime();
			
		}
	}
	
	
	/**
	 * Seek the sequence to any position: start = 0.0 end = 1.0
	 * 
	 * @param theValue seek value: 0.0 - 1.0
	 */
	public void seek(float theValue) {
		isEnded = false;
		// clamp between 0 and 1
		theValue = PApplet.constrain(theValue, 0.0f, 1.0f);
		time = theValue * durationTotal;		
		// find step corresponding to seek value
		for (int i=steps.size()-1; i >= 0 ; i--) {
			Step tmpStep = steps.get(i);
			if (time >= tmpStep.startTime && time < tmpStep.endTime) {
				currentStep = i;
				//System.out.println(i);
				break;
			} 
		}
		
		// bring all variables back to their starting points 
		// and seek current step
		for (int i=steps.size()-1; i >= currentStep ; i--) {
			Step tmpStep = steps.get(i);
			if (i == currentStep) {
				float stepSeekTime = time - tmpStep.startTime;
				float stepSeekValue = PApplet.map(stepSeekTime, 0.0f, tmpStep.duration, 0.0f, 1.0f);
				tmpStep.seek(stepSeekValue);
				break;
			} else {
				tmpStep.seekAll(0.0f);
			}
		}
	}
	
	/**
	 * Adds a single Ani to the sequence
	 * 
	 * @param theAni
	 */
	public void add(Ani theAni) {
		if (addParallel) {
			addParallelAnisCollector.add(theAni);
		}
		else {
			Step tmpStep = new Step(theAni);
			steps.add(tmpStep);
		}
	}
	
	/**
	 * Adds multiple Anis to the sequence
	 * 
	 * @param theAnis
	 */
	public void add(Ani[] theAnis) {
		// beginParallel() was not called before
		if (!addParallel) {
			beginStep();
			for (int i=0; i < theAnis.length; i++) {
				add(theAnis[i]);
			}
			endStep();
		}
		// beginParallel() was called before, so add insertParallelAnisCollector
		else {
			for (int i=0; i < theAnis.length; i++) {
				add(theAnis[i]);
			}
		}
	}
	
	/**
	 * Begin a new step, everything after until endStep() is called is treated as a single step
	 */
	public void beginStep() { 
		addParallelAnisCollector = new ArrayList<Ani>();
		addParallel = true;
	}
	
	/**
	 * End the step
	 */
	public void endStep() {
		Ani[] tmpAnis = new Ani[addParallelAnisCollector.size()];
		for (int i=0; i < tmpAnis.length; i++) {
			tmpAnis[i] = addParallelAnisCollector.get(i);
		}
		Step tmpStep = new Step(tmpAnis);
		steps.add(tmpStep);
		addParallel = false;
	}
	
	/**
	 * Start the first step of the sequence
	 */
	public void start() {
		isPlaying = true;
		isEnded = false;		
		reconstruct();
		// start the first step
		Step tmpStep = (Step) steps.get(currentStep);
		tmpStep.start();
	}
	
	/**
	 * Resume the sequence from the current position in time (adjustable with seek)
	 */
	public void resume() {
		Step tmpStep = steps.get(currentStep);
		tmpStep.play();
		isPlaying = true;
		isEnded = false;
	}
	
	/**
	 * Pause the sequence at the current position in time
	 */
	public void pause() {
		Step tmpStep = steps.get(currentStep);
		tmpStep.pause();
		isPlaying = false;
	}
	
	/**
	 * Begin sequence
	 */
	public void beginSequence() {
		// disable autostart feature of Ani
		Ani.noAutostart();
		Ani.noOverwrite();
	}
	
	/**
	 * End sequence
	 */
	public void endSequence() {
		// enable autostart feature of Ani
		Ani.autostart();
		Ani.overwrite();
		reconstruct();
	}
	
	// reconstruct all variables back to their origin values (before the sequence was created)
	private void reconstruct() {
		currentStep = 0;

		// calc global durationTotal of all steps
		// set start- and end times to the steps
		durationTotal = 0;
		for (int i=0; i < steps.size(); i++) {
			Step tmpStep = steps.get(i);
			tmpStep.pause(); // just in case this is a re-start of the whole sequence
			tmpStep.startTime = durationTotal;
			tmpStep.endTime = durationTotal + tmpStep.duration;
			durationTotal += tmpStep.duration;
			//System.out.println(tmpStep.startTime+" "+tmpStep.endTime+"  "+tmpStep.duration);
		}
		// bring all variables back to their starting points 
		for (int i=steps.size()-1; i >= 0 ; i--) {
			Step tmpStep = steps.get(i);	
			tmpStep.seekAll(0.0f);
		}
	}
	
	/**
	 * Checks if sequence is playing
	 * 
	 * @return true, if sequence is playing
	 */
	public boolean isPlaying() {
		return isPlaying;
	}
	
	/**
	 * Checks if the sequence is ended.
	 * 
	 * @return true, if the sequence is ended
	 */
	public boolean isEnded() {
			return isEnded;
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
	 * Gets the duration of the sequence
	 * 
	 * @return the duration
	 */
	public float getDuration() {
		return durationTotal;
	}
	
	/**
	 * Gets the current step
	 * 
	 * @return the step number
	 */
	public int getStepNumber() {
		return currentStep + 1;
	}
	
	/**
	 * Gets the count step
	 * 
	 * @return the step count
	 */
	public int getStepCount() {
		return steps.size();
	}
	
	/**
	 * Gets the current position in time of the sequence
	 * 
	 * @return the position
	 */
	public float getTime() {
		return time;
	}
}
