package de.looksgood.ani;

import processing.core.PConstants;

public class AniUtil {
	/**
	 * Calculates the minimal difference of two angles given in radians.
	 * by Hartmut Bohnacker.
	 * 
	 * @param theAngle1
	 *            Angle to subtract from
	 * @param theAngle2
	 *            Angle to subtract
	 * @return Angle between -PI and PI (-180Á and 180Á)
	 */
	public static float shortRotation(float theAngle1, float theAngle2) {
		float a1 = (theAngle1 % PConstants.TWO_PI + PConstants.TWO_PI)
				% PConstants.TWO_PI;
		float a2 = (theAngle2 % PConstants.TWO_PI + PConstants.TWO_PI)
				% PConstants.TWO_PI;

		if (a2 > a1) {
			float d1 = a2 - a1;
			float d2 = a1 + PConstants.TWO_PI - a2;
			if (d1 <= d2) {
				return -d1;
			} else {
				return d2;
			}
		} else {
			float d1 = a1 - a2;
			float d2 = a2 + PConstants.TWO_PI - a1;
			if (d1 <= d2) {
				return d1;
			} else {
				return -d2;
			}
		}
	}
}
