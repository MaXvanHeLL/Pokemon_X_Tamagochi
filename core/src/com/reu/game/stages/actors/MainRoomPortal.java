package com.reu.game.stages.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.reu.game.utils.Utils;

/***
 * A Portal under which Nusselts can sit and hide from Max Hell!
 */
public class MainRoomPortal extends Actor{
	
	private TextureRegion texture_;
	
	public MainRoomPortal(float position_x, float position_y){
		texture_ = new TextureRegion(new Texture(Gdx.files.internal("door.png")));
		setWidth(Utils.GetPixelX(12.0f));
		setHeight(Utils.GetPixelY(11.5f));
		setPosition(position_x, position_y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
	    batch.draw(texture_, getX(), getY(), getOriginX(), getOriginY(),
	    	     getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	} 

}
