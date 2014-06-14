package com.reu.game.monster.bathroom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.reu.game.monster.Monster;
import com.reu.game.utils.Utils;

public class BathroomMonster extends Monster{
	protected List<Animation> idle_animations_;		// The idle animation
	protected Animation		  bath_animation_;		// The eat animation
	protected Animation		  current_animation_;
	protected TextureRegion   current_frame_;	    // The current frame of the monster
	protected TextureRegion	  standartd_monster_;	// The standard texture;
	
	protected float 		state_time_;	// The passed time since the creation of the monster
	protected float 		stop_time_;		// The time when the running animation should stop
	protected float			busy_time_;		// The time when the monster isn't bussy anymore
	protected float			animation_time_;// Where in the animation is the frame?
	protected boolean 		animated_;		// Is the monster currently animated?
	protected boolean		busy_;			// Is the monster bussy or should it do something?
	protected Random		r_generator_;	// Random number generator
	protected boolean		bathing_;
	
	BathroomMonster()
	{
		// Initialize values
	    state_time_   		= 0f;
	    animation_time_ 	= 0f;
	    animated_     		= false;
	    r_generator_  		= new Random();
	    
	    idle_animations_ = new ArrayList<Animation>();
	   
		setWidth(Utils.GetPixelX(60));
		setHeight(Utils.GetPixelY(60));
		setOrigin(getWidth()/2.0f, getHeight()/2.0f);
		setPosition(Utils.GetPixelX(45 - 30), Utils.GetPixelY(60 - 30));
		
		bathing_ = false;
	}
	
	protected void AddIdleAnimations(Animation to_add)
	{
		idle_animations_.add(to_add);
	}
	
	protected void AddBathAnimation(Animation to_add)
	{
		bath_animation_ = to_add;
	}

	
	/***
	 * Loads the texture of your monster! THIS FUNCTION MUST BE CALLED!
	 * @param textre_path	path of your monster texture
	 */
	protected void LoadMonsterTexture(String textre_path)
	{
		standartd_monster_ = new TextureRegion(new Texture(Gdx.files.internal(textre_path)));
	}
	
	@Override
	public void Reset()
	{
		clearActions();
		busy_ = false;
		animated_ = false;
	}
	
	/***
	 * Performs a random activity
	 */
	private void doSomething()
	{
		
		int percent = Math.abs(r_generator_.nextInt(100));
		if(percent < 20 && idle_animations_.size() > 0)
		{
			// Code for choosing random animation
			int index = Math.abs(r_generator_.nextInt(idle_animations_.size()));
			playAnimation(idle_animations_.get(index), 1);
		}
		else
		{
			busy_ = true;
			busy_time_ += 1;
		}
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
	
	public void takeBath()
	{
		bathing_ = true;
		playAnimation(bath_animation_, 500);
	}
	
	public void stopBath()
	{
		bathing_ = false;
		stop_time_ = state_time_;
		busy_time_ = state_time_;
	}
	
	public boolean isBathing()
	{
		return bathing_;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		state_time_ += Gdx.graphics.getDeltaTime();
		// Animation stuff
		if(animated_)
		{
			animation_time_ += Gdx.graphics.getDeltaTime();
			if(state_time_ > stop_time_)
			{
				animation_time_ = (float)Math.ceil(animation_time_);
			}
			current_frame_ = current_animation_.getKeyFrame(animation_time_, true);
			if(state_time_ > stop_time_)
			{
				animated_ = false;
				busy_ = false;
				animation_time_ = 0;
			}
		}
		else
		{
			current_frame_ = standartd_monster_;;
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
			doSomething();
		}

	    batch.draw(current_frame_, getX(), getY(), getOriginX(), getOriginY(),
	    	     getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

	} 
}
