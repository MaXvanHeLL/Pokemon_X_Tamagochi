package com.reu.game.monster.mainroom;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.reu.game.monster.Monster;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;


/***
 * The basic class for all monsters. It implements everything which is needed
 * to show and animate different monster types in the main room
 */
public abstract class MainroomMonster extends Monster{
	
	protected Animation 	  	walk_animation_;		// The walk animation
	protected Animation 	  	sleep_animation_;
	protected TextureRegion   	current_frame_;	    // The current frame of the monster
	protected Animation		  	current_animation_;
	protected float				animation_time_;// Where in the animation is the frame?
	protected Boolean 			sleeping_;
	
	private Boolean walking_back_from_bedroom_;


	protected float 		state_time_;	// The passed time since the creation of the monster
	protected float 		stop_time_;		// The time when the running animation should stop
	protected float			busy_time_;		// The time when the monster isn't bussy anymore
	protected boolean 		animated_;		// Is the monster currently animated?
	protected boolean		busy_;			// Is the monster bussy or should it do something?
	protected Random		r_generator_;	// Random number generator
	private List<Vector2>	waypoints_;		// The points to go next
	
	/***
	 * Creates a monster
	 */
	MainroomMonster()
	{
		// Initialize values
	    state_time_   = 0f;
	    sleeping_ = false;
	    animated_     = false;
	    
	    walking_back_from_bedroom_ = false;
	    
	   // current_animation_ = walk_animation_;
	    r_generator_  = new Random();
	    setWaypoints_(new ArrayList<Vector2>());
	    
	    setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/3.0f);
		setWidth(Utils.GetPixelX(8));
		setHeight(Utils.GetPixelX(8));
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
	protected void AddWalkAnimation(Animation walk_animation)
	{
		walk_animation_ = walk_animation;
		current_animation_ = walk_animation;
	}
	
	protected void AddSleepAnimation(Animation sleep_animation)
	{
		sleep_animation_ = sleep_animation;
	}
	
	/***
	 * Moves the monster to an given position.
	 * 
	 * @param x The x position in absolute pixels (depending on screen resolution)
	 * @param y The y position in absolute pixels (depending on screen resolution)
	 */
	public void MoveTo(float x, float y, float speed_factor)
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
		float moving_time = length / (Utils.GetPixelY(20) * speed_factor) ;
		addAction(sequence(rotateTo(angle, rotation_time), moveTo(x - (getOriginX() * getScaleX()),y - (getOriginY() * getScaleY()), moving_time)));
		stop_time_ = state_time_ + rotation_time + moving_time;
		busy_time_ = stop_time_;
	}
	
	/***
	 * Sends the monster to a room
	 * @param room	The room where the monster should go to
	 */
	public void GoToRoom(RoomType room)
	{
		busy_ = false;
		clearActions();
		if(!getWaypoints_().isEmpty())
		{
			if(getWaypoints_().get(0).x == Utils.GetPixelX(83) && getWaypoints_().get(0).y == Utils.GetPixelY(106))
			{
				// Sleeping waypoint
				System.out.println("HIT");
			}
			else
			{
				getWaypoints_().clear();
				getWaypoints_().add(new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
			}
		}
		else
		{
			getWaypoints_().add(new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
		}
		
		if(room == RoomType.KITCHEN || room == RoomType.BATHROOM)
		{
			getWaypoints_().add(new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(140)));
			if(room == RoomType.KITCHEN )
			{
				getWaypoints_().add(new Vector2(Utils.GetPixelX(0), Utils.GetPixelY(140)));
			}
			else
			{
				getWaypoints_().add(new Vector2(Utils.GetPixelX(90), Utils.GetPixelY(140)));
			}
		}
		else if(room == RoomType.PLAYROOM || room == RoomType.BEDROOM)
		{
			getWaypoints_().add(new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(106)));
			if(room == RoomType.PLAYROOM)
			{
				getWaypoints_().add(new Vector2(Utils.GetPixelX(0), Utils.GetPixelY(106)));
			}
			else
			{
				getWaypoints_().add(new Vector2(Utils.GetPixelX(83), Utils.GetPixelY(106)));
				getWaypoints_().add(new Vector2(Utils.GetPixelX(83), Utils.GetPixelY(114)));
			}
		}
	}
	
	
	/**
	 * Sets the monster back to it's starting position
	 */
	@Override
	public void Reset(){
		getWaypoints_().clear();
		clearActions();
		busy_ = false;
		animated_ = false;
		sleeping_ = false;
		setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/3.0f);
	}
	
	public void sleepTime()
	{
		sleeping_ = true;
		playAnimation(sleep_animation_, 500);
	}
	
	public void stopSleeping()
	{
		walking_back_from_bedroom_ = true;
		sleeping_ = false;
		stop_time_ = state_time_;
		busy_time_ = state_time_;
		current_animation_ = walk_animation_;
		getWaypoints_().clear();
		getWaypoints_().add(new Vector2(Utils.GetPixelX(83), Utils.GetPixelY(106)));
		getWaypoints_().add(new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(106)));
		getWaypoints_().add(new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
	}
	public boolean isSleeping()
	{
		return sleeping_;
	}
	
	private void playAnimation(Animation to_play, float length)
	{
		busy_ = true;
		animated_ = true;
		current_animation_ = to_play;
		animation_time_ = 0;
		stop_time_ = state_time_ + length;
		busy_time_ = stop_time_;
	}
	
	/***
	 * Performs a random activity
	 */
	public void doSomething()
	{
		int chance = Math.abs(r_generator_.nextInt(100));
		// 50 % chance to wait a random amount of time
		if(chance < 70)
		{
			// Wait between 500 and 1500 ms
			int wait_ms = (Math.abs(r_generator_.nextInt(800))) + 400;
			busy_time_ = state_time_ + (wait_ms / 1000.0f);
		}
		else
		{
			// Move to random position on the stone ground
			int random_x = Math.abs(r_generator_.nextInt(34)) + 27;	// Random x from 27 to 62
			int random_y = Math.abs(r_generator_.nextInt((int)((Gdx.graphics.getHeight()/2.0f) - 2 * getHeight())));
			MoveTo(Utils.GetPixelX((float)random_x), (float)random_y + getHeight(), 1);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		state_time_ += Gdx.graphics.getDeltaTime();
		// Animation stuff
		if(animated_)
		{
			current_frame_ = current_animation_.getKeyFrame(state_time_, true);
			if(state_time_ > stop_time_)
			{
				animated_ = false;
				busy_ = false;
			}
		}
		else
		{
			current_frame_ = current_animation_.getKeyFrame(0.5f, true);
		}
		
		// Do random things while nothing happens
		if(busy_)
		{
			if(state_time_ > busy_time_)
			{
				busy_ = false;
			}
		}
		else
		{
			// First check for further waypoints:
			if(!getWaypoints_().isEmpty())
			{
				MoveTo(getWaypoints_().get(0).x, getWaypoints_().get(0).y, 2.5f);
				getWaypoints_().remove(0);
			}
			else
			{
				doSomething();
			}
		}

	    batch.draw(current_frame_, getX(), getY(), getOriginX(), getOriginY(),
	    	     getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

	}

	public List<Vector2> getWaypoints_() {
		return waypoints_;
	}

	public void setWaypoints_(List<Vector2> waypoints_) {
		this.waypoints_ = waypoints_;
	}

	public Boolean getWalking_back_from_bedroom_() {
		return walking_back_from_bedroom_;
	}

	public void setWalking_back_from_bedroom_(Boolean walking_back_from_bedroom_) {
		this.walking_back_from_bedroom_ = walking_back_from_bedroom_;
	}

}
