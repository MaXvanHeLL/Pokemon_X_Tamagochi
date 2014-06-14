package com.reu.game;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.reu.game.monster.Stats;
import com.reu.game.stages.Kitchen;
import com.reu.game.stages.MainRoom;
import com.reu.game.stages.ReuGameStage;
import com.reu.game.types.MonsterType;
import com.reu.game.types.RoomType;
import com.reu.game.types.GameMode;

public class ReuGame extends ApplicationAdapter 
{
	
	// Use the monster type static for now, could be read from config later!
	private static MonsterType MONSTER_TYPE = MonsterType.NUSSELTS;
	
	private Stats nusselts_stats_;
	private Preferences prefs;
	
	private static boolean sound_enabled_ = true;
	private static float system_time_ = 0;
	
	@SuppressWarnings("unused")
	private GameMode current_mode_;
	private RoomType current_room_;				// The room which is currently active
	private Map<RoomType, ReuGameStage> stages_;// Includes all stages
	private Skin skin_;							// The skin for our game
	
	

	@Override
	public void create () 
	{
		// Create the skins for our game!
		CreateSkins();
		system_time_ = 0;
		// -- loading Nusselts Stats from Phone_Memory
		// ----------------------------------------------
		setPrefs(Gdx.app.getPreferences("Nusselts Preferences"));
		//	this.setNusselts_stats_(new Stats(getPrefs().getFloat("hunger", 100), 100, 100, 100, 100, 100, 100));
		nusselts_stats_ = new Stats();
		nusselts_stats_.setHunger(getPrefs().getFloat("hunger", 100));
		nusselts_stats_.setHappiness(getPrefs().getFloat("happiness", 100));	
		nusselts_stats_.setDirtness(getPrefs().getFloat("dirtness", 100));	
		nusselts_stats_.setTiredness(getPrefs().getFloat("tiredness", 100));
		nusselts_stats_.setName(getPrefs().getString("name", "Nusselts"));
		nusselts_stats_.setWeight(getPrefs().getFloat("weight", 10));
		// Use the date as a long value
		Calendar cal = Calendar.getInstance();
		long prefs_day = getPrefs().getLong("creation", cal.getTimeInMillis());
		cal.setTimeInMillis(prefs_day);
		nusselts_stats_.setCreationDate(cal.getTimeInMillis());
		
		// Add all the stages to the stage map!
		stages_ = new HashMap<RoomType, ReuGameStage>();
		stages_.put(RoomType.MAINROOM, new MainRoom(this));
		stages_.put(RoomType.KITCHEN, new Kitchen(this));
		
		
		// Set the stage to the Mainroom on start up
		SetCurrentStage(RoomType.MAINROOM);
		// Set the Game Mode to Care Mode on start up
		SetCurrentGameMode(GameMode.CARE_MODE);
	}
	
	/***
	 * Returns the skin of the current game
	 * @return The skin
	 */
	public Skin getSkin()
	{
		return skin_;
	}
	
	/***
	 * Returns the monster type which is currently used
	 * @return The MonsterType
	 */
	public MonsterType getMonsterType(){
		return MONSTER_TYPE;
	}
	
	/***
	 * Changes the current game Scene
	 * @param The room which should be used in the next frame
	 */
	public void SetCurrentStage(RoomType type)
	{
		Gdx.input.setInputProcessor(stages_.get(type));
		this.setCurrent_room_(type);
	}
	
	/***
	 * Changes the current game Mode
	 * @param The Game Mode which is currently active
	 */
	public void SetCurrentGameMode(GameMode game_mode)
	{
		this.current_mode_ = game_mode;
	}
	
	/***
	 * Creates the game skin. Changes for the design should go here
	 */
	private void CreateSkins()
	{
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin_ = new Skin();
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin_.add("white", new Texture(pixmap));
		
		pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fill();
		skin_.add("red", new Texture(pixmap));
		
		pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		skin_.add("green", new Texture(pixmap));
		
		// Store the default libgdx font under the name "default".
		skin_.add("default", new BitmapFont());
		
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin_.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin_.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin_.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin_.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin_.getFont("default");
		skin_.add("default", textButtonStyle);
		
		//---------------------------------------------------------------------
		// Productive code starts here, code above is just for example
		
		// This is the first line of code which we actually use! It defines our
		// house image as Texture which we can use as background later!
		
		// MainRoom Stage
		skin_.add("MainRoom", new Texture(Gdx.files.internal("house.png")));
		
		// Kitchen Stage
		skin_.add("Kitchen", new Texture(Gdx.files.internal("kitchen.png")));
		skin_.add("kitchen_button", new Texture(Gdx.files.internal("badlogic.jpg")));
		skin_.add("kitchen_button_down", new Texture(Gdx.files.internal("door.png")));

		// Test Skins
		skin_.add("CircleMask", new Texture(Gdx.files.internal("mask_circle.png")));
		
		// Ribon Skins
		skin_.add("RibbonTop", new Texture(Gdx.files.internal("ribbon_top.png")));
		skin_.add("RibbonRepeat", new Texture(Gdx.files.internal("dashboard_bg.png")));
		skin_.add("IconHappy", new Texture(Gdx.files.internal("overlay_happiness.png")));
		skin_.add("IconHungry", new Texture(Gdx.files.internal("overlay_food.png")));
		skin_.add("IconTired", new Texture(Gdx.files.internal("overlay_tiredness.png")));
		skin_.add("IconDirty", new Texture(Gdx.files.internal("overlay_cleaness.png")));
		skin_.add("ButtonUp", new Texture(Gdx.files.internal("button_db_norm.png")));
		skin_.add("ButtonDown", new Texture(Gdx.files.internal("button_db_active.png")));
		skin_.add("Portrait", new Texture(Gdx.files.internal("portrait.png")));
		skin_.add("BarFrame", new Texture(Gdx.files.internal("bar_frame.png")));
	}

	@Override
	public void resize(int width, int height) 
	{
	    // See below for what true means.
	    stages_.get(getCurrent_room_()).getViewport().update(width, height, true);
	}
	
	@Override
	public void render () 
	{
		system_time_ += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stages_.get(getCurrent_room_()).act(Gdx.graphics.getDeltaTime());
		stages_.get(getCurrent_room_()).PostAct();
		stages_.get(getCurrent_room_()).draw();
	}
	
	public RoomType getCurrent_room_() 
	{
		return current_room_;
	}

	public void setCurrent_room_(RoomType current_room_) 
	{
		this.current_room_ = current_room_;
	}

	public Stats getNusselts_stats_() 
	{
		return nusselts_stats_;
	}

	public void setNusselts_stats_(Stats nusselts_stats_) 
	{
		this.nusselts_stats_ = nusselts_stats_;
	}

	public Preferences getPrefs() 
	{
		return prefs;
	}

	public void setPrefs(Preferences prefs) 
	{
		this.prefs = prefs;
	}
	
	/***
	 * Get the running time of the system
	 * @return	The time passed since start
	 */
	public static float getSystemTime()
	{
		return system_time_;
	}
	
	/***
	 * Check if the sound is enabled
	 * @return bool
	 */
	public static boolean isSoundEnabled()
	{
		return sound_enabled_;
	}
	
	/***
	 * Enable or disable the sound
	 * @param enable
	 */
	public static void setSound(boolean enable)
	{
		sound_enabled_ = enable;
	}
}
