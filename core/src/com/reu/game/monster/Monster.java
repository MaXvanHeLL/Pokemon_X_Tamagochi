package com.reu.game.monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Monster  extends Actor{
	
	/***
	 * Get the x-center of the monster
	 * @return The horizontal center
	 */
	public float GetCenterX(){
		return getX() + (getOriginX() * getScaleX());
	}
	
	/***
	 * Get the y-center of the monster
	 * @return The vertical center
	 */
	public float GetCenterY(){
		return getY() + (getOriginY() * getScaleY());
	}
	
	/***
	 * Loads the Textures of an animation.
	 * 
	 * @param frame_cols Number of columns in the Walk Sheet
	 * @param frame_rows Number of rows in the Walk Sheet
	 * @param sheet The Walk Sheet itself
	 * @return Array of texture region for the animation
	 */
	protected TextureRegion[] LoadAnimation(int frame_cols, int frame_rows, Texture sheet)
	{
		// Spit the Texture into texture regions for the single frames
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/frame_cols, sheet.getHeight()/frame_rows);
		
		// Create the one dimensional walk frames...
		TextureRegion[] frames = new TextureRegion[frame_cols * frame_rows];
	    int index = 0;
	    // ... and fill it with the frames from the two dimensional array
	    for (int i = 0; i < frame_rows; i++) 
	     {
	         for (int j = 0; j < frame_cols; j++) 
	         {
	        	 frames[index++] = tmp[i][j];
	         }
	     }
	    return frames;
	}
	
	public void Reset()	{		
	}
}
