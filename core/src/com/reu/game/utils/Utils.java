package com.reu.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Utils {
	/***
	 * Implemented a method for further use of REGION tests
	 * 
	 * @param r Rectangle as REGION OF INTEREST
	 * @param x X coordination of Testsubject (mostly NUSSELTS ^.^)
	 * @param y Y Coordination of Testsubject (mostly NUSSELTS ^.^)
	 * @return True if in or false if not!
	 */
	public static boolean PointInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
	
	public static float GetPixelX(float x_raster){
		return Gdx.graphics.getWidth() / 90.0f * x_raster;
	}
	
	public static float GetPixelY(float y_raster){
		return Gdx.graphics.getHeight() / 160.0f * y_raster;
	}
}
