package com.reu.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Utils {
	/***
	 * Implemented a method for further use of REGION tests
	 * 
	 * @param r Rectangle as REGION OF INTEREST
	 * @param x X coordination of Testsubject (mostly NUSSELTS ^.^)
	 * @param y Y Coordination of Testsubject (mostly NUSSELTS ^.^)
	 * @return True if in or false if not!
	 */
	public static boolean MonsterInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
	
	public static boolean PointInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
	
	public static float GetPixelX(float x_raster){
		return Gdx.graphics.getWidth() / 90.0f * x_raster;
	}
	
	public static float GetPixelY(float y_raster){
		return Gdx.graphics.getHeight() / 160.0f * y_raster;
	}
	
	/***
	 * Creates a color as skin for the new Image function
	 * @param value	The value of the stat which the color should represent
	 * @return		The skin which contains the color as attribute "color"
	 */
	public static Skin getAttentionColor(float value)
	{
		Skin to_ret = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.rgba8888(1 - (value / 100.0f), (value / 100.0f), 0, 1));
		pixmap.fill();
		to_ret.add("color", new Texture(pixmap));
		return to_ret;
	}
}
