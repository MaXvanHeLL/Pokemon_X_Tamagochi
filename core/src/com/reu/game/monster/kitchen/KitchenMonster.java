package com.reu.game.monster.kitchen;

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

public abstract class KitchenMonster extends Monster {
	protected List<Animation> idle_animations_;		// The idle animation
	protected Animation		  eat_animation_;		// The eat animation
	protected Animation		  nooo_animation_;		// The animation if the monster wants no more
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
	
	KitchenMonster()
	{
		// Initialize values
		stop_time_ = 0f;
		busy_time_ = 0f;
		busy_ = false;
		
	    state_time_   		= 0f;
	    animation_time_ 	= 0f;
	    animated_     		= false;
	    r_generator_  		= new Random();
	    
	    idle_animations_ = new ArrayList<Animation>();
	   
		setWidth(Utils.GetPixelX(50));
		setHeight(Utils.GetPixelY(50));
		setOrigin(getWidth()/2.0f, getHeight()/2.0f);
		setPosition(Utils.GetPixelX(45 - 25), Utils.GetPixelY(60 - 25));
	}
	
	protected void AddIdleAnimations(Animation to_add)
	{
		idle_animations_.add(to_add);
	}
	
	protected void AddNoooAnimation(Animation to_add)
	{
		nooo_animation_ = to_add;
	}
	
	protected void AddEatAnimation(Animation to_add)
	{
		eat_animation_ = to_add;
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
	
	public void eatSomething()
	{
		playAnimation(eat_animation_, 1);
	}
	
	public void pleaseNoMoreFood()
	{
		playAnimation(nooo_animation_, 1);
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
			if(current_animation_ != null)
			{
				current_frame_ = current_animation_.getKeyFrame(animation_time_, true);
			}
			else
			{
				current_frame_ = standartd_monster_;
			}
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

	public List<Animation> getIdle_animations_() {
		return idle_animations_;
	}

	public Animation getEat_animation_() {
		return eat_animation_;
	}

	public Animation getNooo_animation_() {
		return nooo_animation_;
	}

	public Animation getCurrent_animation_() {
		return current_animation_;
	}

	public TextureRegion getCurrent_frame_() {
		return current_frame_;
	}

	public TextureRegion getStandartd_monster_() {
		return standartd_monster_;
	}

	public float getState_time_() {
		return state_time_;
	}

	public float getStop_time_() {
		return stop_time_;
	}

	public float getBusy_time_() {
		return busy_time_;
	}

	public float getAnimation_time_() {
		return animation_time_;
	}

	public boolean isAnimated_() {
		return animated_;
	}

	public boolean isBusy_() {
		return busy_;
	}

	public Random getR_generator_() {
		return r_generator_;
	} 

}
