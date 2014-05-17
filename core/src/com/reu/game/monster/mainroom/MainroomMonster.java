package com.reu.game.monster.mainroom;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.reu.game.monster.Monster;
import com.reu.game.utils.Utils;;


/***
 * The basic class for all monsters. It implements everything which is needed
 * to show and animate different monster types in the main room
 */
public abstract class MainroomMonster extends Monster{
	
	protected Animation 	  walk_animation_;		// The walk animation
	protected TextureRegion   current_frame_;	    // The current frame of the monster
	
	protected float 	state_time_;	// The passed time since the creation of the monster
	protected float 	stop_time_;		// The time when the running animation should stop
	protected boolean 	animated_;		// Is the monster currently animated?
	
	/***
	 * Creates a monster
	 */
	MainroomMonster()
	{
		// Initialize values
	    state_time_   = 0f;
	    animated_     = false;
	    
	    setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f);
		setWidth(Utils.GetPixelX(18));
		setHeight(Utils.GetPixelX(18));
		setOrigin(getWidth()/2.0f, getHeight()/2.0f);
	}
	
	/***
	 * Loads the walk animation of the monster! This MUST be called before you
	 * use the monster. Else your program will ultimately fail.
	 * 
	 * @param frame_cols Number of columns in the Walk Sheet
	 * @param frame_rows Number of rows in the Walk Sheet
	 * @param walk_sheet_path The path to the Walk Sheet Texture
	 */
	protected void LoadWalkAnimation(int frame_cols, int frame_rows, String walk_sheet_path)
	{
		// Create the single frames of the animation
		TextureRegion[] walk_frames = LoadAnimation(frame_cols, frame_rows, new Texture(Gdx.files.internal(walk_sheet_path)));
		// Create the animation itself and save it to the member
		walk_animation_ = new Animation(1.0f / walk_frames.length, walk_frames);
	}
	
	/***
	 * Loads the Textures of an animation.
	 * 
	 * @param frame_cols Number of columns in the Walk Sheet
	 * @param frame_rows Number of rows in the Walk Sheet
	 * @param sheet The Walk Sheet itself
	 * @return Array of texture region for the animation
	 */
	private TextureRegion[] LoadAnimation(int frame_cols, int frame_rows, Texture sheet)
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
	
	/***
	 * Moves the monster to an given position.
	 * 
	 * @param x The x position in absolute pixels (depending on screen resolution)
	 * @param y The y position in absolute pixels (depending on screen resolution)
	 */
	public void MoveTo(float x, float y)
	{
		animated_ = true;
		clearActions();
		// Calculate vector of movement
		Vector2 current_position = new Vector2(getX(), getY());		 
		Vector2 new_position = new Vector2(x - (getOriginX() * getScaleX()),y - (getOriginY() * getScaleY()));
		Vector2 distance = new_position.sub(current_position);
		float length = distance.len();
		// Do some top secret angle calculation
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
		// Calculate time of rotation, 360 degree would take 1 second 
		float rotation_time = 1f / 360f * Math.abs(old_angle - angle);
		// Calculate time of movement, 500 pixel would take 1 second
		float moving_time = length / 500f;
		addAction(sequence(rotateTo(angle, rotation_time), moveTo(x - (getOriginX() * getScaleX()),y - (getOriginY() * getScaleY()), moving_time)));
		stop_time_ = state_time_ + rotation_time + moving_time;
	 }
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		state_time_ += Gdx.graphics.getDeltaTime();
		if(animated_)
		{
			current_frame_ = walk_animation_.getKeyFrame(state_time_, true);
			if(state_time_ > stop_time_)
				animated_ = false;
		}
		else
		{
			current_frame_ = walk_animation_.getKeyFrame(0.5f, true);
		}

	    batch.draw(current_frame_, getX(), getY(), getOriginX(), getOriginY(),
	    	     getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

	} 
}
