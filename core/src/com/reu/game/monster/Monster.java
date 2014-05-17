package com.reu.game.monster;

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

}
