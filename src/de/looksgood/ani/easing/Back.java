/**********************************************************************
TERMS OF USE - EASING EQUATIONS
Open source under the BSD License.
Copyright (c) 2001 Robert Penner
JavaScript version copyright (C) 2006 by Philippe Maegerman
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

   * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
   * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
   * Neither the name of the author nor the names of contributors may
be used to endorse or promote products derived from this software
without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*****************************************/

package de.looksgood.ani.easing;

public class Back extends Easing {
	public Back(int theEasingMode){
		this();
		setMode(theEasingMode);
	}

	public Back(){
		setMode(easingMode);
	}

	public float easeIn(float t, float b, float c, float d) {
		float s = 1.70158f;
		return c * (t /= d) * t * ((s + 1) * t - s) + b;
	}

	public float easeOut(float t, float b, float c, float d) {
		float s = 1.70158f;
		return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
	}

	public float easeInOut(float t, float b, float c, float d) {
		float s = 1.70158f;
		if ((t /= d / 2) < 1)
			return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
		return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
	}
}
