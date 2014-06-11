package com.reu.game.monster.mainroom;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.Random;

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
	protected float		bussy_time_;	// The time when the monster isn't bussy anymore
	protected boolean 	animated_;		// Is the monster currently animated?
	protected boolean	busy_;			// Is the monster bussy or should it do something?
	protected Random	r_generator_;	// Random number generator
	
	/***
	 * Creates a monster
	 */
	MainroomMonster()
	{
		// Initialize values
	    state_time_   = 0f;
	    animated_     = false;
	    r_generator_  = new Random();
	    
	    setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/3.0f);
		setWidth(Utils.GetPixelX(8));
		setHeight(Utils.GetPixelX(8));
		setOrigin(getWidth()/2.0f, getHeight()/2.0f);
		
		// CARE_MODE Attributes
		setHappiness(0);
		setTiredness(0);
		setDirtness(0);
		setHunger(0);
		
		//BATTLE_MODE Attributes
		setStrength(100);
		setAgility(50);
		setHealth(200);
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
		busy_ = true;
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
		// Calculate time of movement, 1/8 of the room width per second
		float moving_time = length / Utils.GetPixelY(20);
		addAction(sequence(rotateTo(angle, rotation_time), moveTo(x - (getOriginX() * getScaleX()),y - (getOriginY() * getScaleY()), moving_time)));
		stop_time_ = state_time_ + rotation_time + moving_time;
	 }
	
	private void doSomething()
	{
		busy_ = true;
		int chance = Math.abs(r_generator_.nextInt(100));
		// 50 % chance to wait a random amount of time
		if(chance < 70)
		{
			// Wait between 500 and 1500 ms
			int wait_ms = (Math.abs(r_generator_.nextInt(800))) + 400;
			bussy_time_ = state_time_ + (wait_ms / 1000.0f);
		}
		else
		{
			// Move to random position on the stone ground
			int random_x = Math.abs(r_generator_.nextInt(35)) + 27;	// Random x from 27 to 62
			int random_y = Math.abs(r_generator_.nextInt((int)((Gdx.graphics.getHeight()/2.0f) - 2 * getHeight())));
			MoveTo(Utils.GetPixelX((float)random_x), (float)random_y + getHeight());
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		state_time_ += Gdx.graphics.getDeltaTime();
		// Animation stuff
		if(animated_)
		{
			current_frame_ = walk_animation_.getKeyFrame(state_time_, true);
			if(state_time_ > stop_time_)
			{
				animated_ = false;
				busy_ = false;
			}
		}
		else
		{
			current_frame_ = walk_animation_.getKeyFrame(0.5f, true);
		}
		
		// Do random things while nothing happens
		if(busy_)
		{
			if(state_time_ > bussy_time_)
			{
				busy_ = false;
			}
		}
		else
		{
			doSomething();
		}

	    batch.draw(current_frame_, getX(), getY(), getOriginX(), getOriginY(),
	    	     getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

	} 
	
	// BATTLE_MODE Attributes
	public int getStrength ()
	{
		return this.strength_;
	}
	
	public void setStrength(int strength)
	{
		this.strength_ = strength;
	}
	
	public int getHealth ()
	{
		return this.health_;
	}
	
	public void setHealth(int health)
	{
		this.health_ = health;
	}
	
	public int getAgility ()
	{
		return this.agility_;
	}
	public void setAgility(int agility)
	{
		this.agility_ = agility;
	}
	
	// CARE_MODE Attributes
	public float getHunger ()
	{
		return this.hunger_;
	}
	
	public void setHunger(int hunger)
	{
		this.hunger_ = hunger;
	}
	
	public float getHappiness ()
	{
		return this.happiness_;
	}
	public void setHappiness(int happiness)
	{
		this.happiness_ = happiness;
	}
	
	public float getTiredness ()
	{
		return this.tiredness_;
	}
	
	public void setTiredness(int tiredness)
	{
		this.tiredness_ = tiredness;
	}
	
	public float getDirtness ()
	{
		return this.dirtness_;
	}
	
	// dirty little Nusselts! :D
	public void setDirtness(int dirtness)
	{
		this.dirtness_ = dirtness;
	}
}
