package com.reu.game.monster.kitchen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.reu.game.monster.Monster;
import com.reu.game.utils.Utils;

public abstract class KitchenMonster extends Monster{
	protected List<Animation> idle_animations_;		// The idle animation
	protected Animation		  eat_animation_;		// The eat animation
	protected Animation		  nooo_animation_;		// The animation if the monster wants no more
	protected TextureRegion   current_frame_;	    // The current frame of the monster
	protected TextureRegion	  standartd_monster_;	// The standard texture;
	
	protected float 		state_time_;	// The passed time since the creation of the monster
	protected float 		stop_time_;		// The time when the running animation should stop
	protected float			busy_time_;		// The time when the monster isn't bussy anymore
	protected boolean 		animated_;		// Is the monster currently animated?
	protected boolean		busy_;			// Is the monster bussy or should it do something?
	protected Random		r_generator_;	// Random number generator
	
	KitchenMonster()
	{
		// Initialize values
	    state_time_   = 0f;
	    animated_     = false;
	    r_generator_  = new Random();
	    
	    idle_animations_ = new ArrayList<Animation>();
	    
	    setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f);
		setWidth(Utils.GetPixelX(20));
		setHeight(Utils.GetPixelX(20));
		setOrigin(getWidth()/2.0f, getHeight()/2.0f);
	}
	
	/***
	 * Loads an idle animation of the monster! This MUST be called before you
	 * use the monster. Else your program will ultimately fail.
	 * 
	 * @param frame_cols Number of columns in the Walk Sheet
	 * @param frame_rows Number of rows in the Walk Sheet
	 * @param idle_sheet_path The path to the Idle Sheet Texture
	 * @param duraction The duration of your idle animation
	 */
	protected void LoadIdleAnimation(int frame_cols, int frame_rows, String walk_sheet_path, float duration)
	{
		// Create the single frames of the animation
		TextureRegion[] walk_frames = LoadAnimation(frame_cols, frame_rows, new Texture(Gdx.files.internal(walk_sheet_path)));
		// Create the animation itself and save it to the member
		idle_animations_.add(new Animation(duration / walk_frames.length, walk_frames));
	}
	
	/***
	 * Loads an nooo animation of the monster! This MUST be called before you
	 * use the monster. Else your program will ultimately fail.
	 * 
	 * @param frame_cols Number of columns in the Walk Sheet
	 * @param frame_rows Number of rows in the Walk Sheet
	 * @param nooo_sheet_path The path to the Nooo Sheet Texture
	 * @param duraction The duration of your idle animation
	 */
	protected void LoadNoooAnimation(int frame_cols, int frame_rows, String nooo_sheet_path, float duration)
	{
		// Create the single frames of the animation
		TextureRegion[] walk_frames = LoadAnimation(frame_cols, frame_rows, new Texture(Gdx.files.internal(nooo_sheet_path)));
		// Create the animation itself and save it to the member
		nooo_animation_ = new Animation(duration / walk_frames.length, walk_frames);
	}
	
	/***
	 * Loads an eat animation of the monster! This MUST be called before you
	 * use the monster. Else your program will ultimately fail.
	 * 
	 * @param frame_cols Number of columns in the Walk Sheet
	 * @param frame_rows Number of rows in the Walk Sheet
	 * @param eat_sheet_path The path to the Eat Sheet Texture
	 * @param duraction The duration of your idle animation
	 */
	protected void LoadEatAnimation(int frame_cols, int frame_rows, String eat_sheet_path, float duration)
	{
		// Create the single frames of the animation
		TextureRegion[] walk_frames = LoadAnimation(frame_cols, frame_rows, new Texture(Gdx.files.internal(eat_sheet_path)));
		// Create the animation itself and save it to the member
		eat_animation_ = new Animation(duration / walk_frames.length, walk_frames);
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
}
