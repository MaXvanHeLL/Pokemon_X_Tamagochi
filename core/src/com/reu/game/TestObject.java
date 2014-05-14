package com.reu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class TestObject extends Actor{
	 private static final int    FRAME_COLS = 1;
	 private static final int    FRAME_ROWS = 16; 
	 
	 Animation 			walkAnimation;
	 Texture            walkSheet;
	 TextureRegion[]    walkFrames;
	 SpriteBatch        spriteBatch;
	 TextureRegion      currentFrame;
	 
	 float stateTime;
	 float stopTime;
	 boolean animate;
	 
	 TestObject()
	 {
		 setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f);
		 setWidth(128);
		 setHeight(128);
		 setOrigin(64, 64);
		 setScale(0.8f);
		 walkSheet = new Texture(Gdx.files.internal("sprites.png"));
	     TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
	     walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	     int index = 0;
	     for (int i = 0; i < FRAME_ROWS; i++) 
	     {
	         for (int j = 0; j < FRAME_COLS; j++) 
	         {
	        	 walkFrames[index++] = tmp[i][j];
	         }
	     }
	     walkAnimation = new Animation(0.025f, walkFrames);
	     spriteBatch = new SpriteBatch();
	     stateTime = 0f;
	     animate = true;
	 }
	 
	 public void MoveTo(float x, float y)
	 {
		 animate = true;
		 clearActions();
		 Vector2 current_position = new Vector2(getX(), getY());
		 Vector2 new_position = new Vector2(x - (getOriginX() * getScaleX()),y - (getOriginY() * getScaleY()));
		 Vector2 distance = new_position.sub(current_position);
		 float length = distance.len();
		 
		 float angle = distance.angle() - 90;
		 if(angle < 0)
		 {
			 angle += 360;
		 }
		 if(getRotation() > 360)
		 {
			 setRotation(getRotation()-360);
		 }
		 else if(getRotation() < 0)
		 {
			 setRotation(getRotation()+360);
		 }
		 float old_angle = getRotation();
		 
		 
		 if(old_angle > 180 && angle < 180 && (old_angle - angle) > 180)
		 {
			 angle += 360; 
		 }
		 else if(old_angle < 180 && angle > 180 && (angle - old_angle) > 180)
		 {
			 angle -= 360;
		 }

		 
		 float rotation_time = 1f / 360f * Math.abs(old_angle - angle);
		 float moving_time = length / 500f;
		 addAction(sequence(rotateTo(angle, rotation_time), moveTo(x - (getOriginX() * getScaleX()),y - (getOriginY() * getScaleY()), moving_time)));
		 stopTime = stateTime + rotation_time + moving_time;
	 }
	 
	 @Override
	 public void draw(Batch batch, float parentAlpha)
	 {
		 stateTime += Gdx.graphics.getDeltaTime();
		 if(animate)
		 {
			 currentFrame = walkAnimation.getKeyFrame(stateTime, true);
			 if(stateTime > stopTime)
				 animate = false;
		 }
		 else
		 {
			 currentFrame = walkAnimation.getKeyFrame(0.5f, true);
		 }
	     spriteBatch.begin();
	     spriteBatch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(),
	    	     getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	     spriteBatch.end();
	 }
	 

}
