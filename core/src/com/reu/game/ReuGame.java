package com.reu.game;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.reu.game.monster.Stats;
import com.reu.game.stages.Bathroom;
import com.reu.game.stages.Kitchen;
import com.reu.game.stages.MainRoom;
import com.reu.game.stages.Playroom;
import com.reu.game.stages.ReuGameStage;
import com.reu.game.types.MonsterType;
import com.reu.game.types.RoomType;
import com.reu.game.types.GameMode;
import com.reu.game.utils.Utils;

public class ReuGame implements Screen 
{
	
	Music music;
	
	// Use the monster type static for now, could be read from config later!
	private static MonsterType MONSTER_TYPE = MonsterType.NUSSELTS;
	private static Map<String, Animation> animations_ = new HashMap<String, Animation>();// Includes all stages
	private static FreeTypeFontGenerator font_generator_;
	
	private Stats nusselts_stats_;
	private Preferences prefs;
	
	private int last_day_;
	private int last_hour_;
	private int stats_timefactor_;
	
	private static boolean sound_enabled_ = true;
	private static float system_time_ = 0;
	
	@SuppressWarnings("unused")
	private GameMode current_mode_;
	private RoomType current_room_;				// The room which is currently active
	private Map<RoomType, ReuGameStage> stages_;// Includes all stages
	private Skin skin_;							// The skin for our game
	
	

	@Override
	public void show() 
	{


		setSound(true);
		
		// Create the skins for our game!
		CreateSkins();
		LoadAnimations();
		system_time_ = 0;
		this.music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack.mp3"));
		this.music.play();
	    this.music.setVolume(0.8f);                 // sets the volume to half the maximum volume
	    this.music.setLooping(true); 
		
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
		
		// read the Programs last time on Startup
		this.last_day_ = getPrefs().getInteger("Day");
		this.last_hour_ = getPrefs().getInteger("Hour");
		
		checkStatsonTime();
	
		// Use the date as a long value
		Calendar cal = Calendar.getInstance();
		long prefs_day = getPrefs().getLong("creation", cal.getTimeInMillis());
		cal.setTimeInMillis(prefs_day);
		nusselts_stats_.setCreationDate(cal.getTimeInMillis());
		
		// Add all the stages to the stage map!
		stages_ = new HashMap<RoomType, ReuGameStage>();
		stages_.put(RoomType.MAINROOM, new MainRoom(this));
		stages_.put(RoomType.KITCHEN, new Kitchen(this));
		stages_.put(RoomType.BATHROOM, new Bathroom(this));
		stages_.put(RoomType.PLAYROOM, new Playroom(this));
		
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
		stages_.get(getCurrent_room_()).ResetRoom();
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
	 * Loads all animations at the beginning. Be careful to much stuff crashes the program
	 */
	private void LoadAnimations()
	{
		// Mainroom only
		animations_.put("WalkAnimation", LoadSingleAnimation(6,6, "walking.png"));
		animations_.put("SleepAnimation", LoadSingleAnimation(6,6, "sleep.png"));
		
		// Kitchen only
		animations_.put("EatAnimation", LoadSingleAnimation(6,6, "eating.png"));

		//Bathroom only
		animations_.put("Bath_Idle0", LoadSingleAnimation(6,6, "bath_idle_1.png"));
		animations_.put("Bath_Idle1", LoadSingleAnimation(6,6, "bath_idle_2.png"));
		animations_.put("Bathing", LoadSingleAnimation(6,6, "bathing.png"));
		
		//Playroom only
		animations_.put("PlayAnimation1", LoadSingleAnimation(6,6, "play_1.png"));
		animations_.put("PlayAnimation2", LoadSingleAnimation(6,6, "play_1_inverted.png"));
		animations_.put("PlayAnimation3", LoadSingleAnimation(6,6, "play_2.png"));
		
		//Shared Animation
		animations_.put("Idle0", LoadSingleAnimation(6,6, "idle_1.png"));
		animations_.put("Idle1", LoadSingleAnimation(6,6, "idle_2.png"));
		animations_.put("Nonono", LoadSingleAnimation(6,6, "no.png"));
	}
	
	protected Animation LoadSingleAnimation(int frame_cols, int frame_rows, String sheet_path)
	{
		// Create the single frames of the animation
		TextureRegion[] walk_frames = LoadTextureRegion(frame_cols, frame_rows, new Texture(Gdx.files.internal(sheet_path)));
		// Create the animation itself and save it to the member
		return new Animation(1.0f / walk_frames.length, walk_frames);
	}
	
	public static Animation getAnimation(String name)
	{
		return animations_.get(name);
	}
	
	/***
	 * Loads the Textures of an animation.
	 * 
	 * @param frame_cols Number of columns in the Walk Sheet
	 * @param frame_rows Number of rows in the Walk Sheet
	 * @param sheet The Walk Sheet itself
	 * @return Array of texture region for the animation
	 */
	protected TextureRegion[] LoadTextureRegion(int frame_cols, int frame_rows, Texture sheet)
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
	 * Creates the game skin. Changes for the design should go here
	 */
	private void CreateSkins()
	{
	    
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin_ = new Skin();		
	
		//---------------------------------------------------------------------
		// Productive code starts here, code above is just for example
		
		// This is the first line of code which we actually use! It defines our
		// house image as Texture which we can use as background later!
		
		// General purpose skins
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
		
		
		// Debugbutton skin
		//--------------------------------------------------------------------------
		skin_.add("DebugButtonUp", new Texture(Gdx.files.internal("badlogic.jpg")));
		skin_.add("DebugButtonDown", new Texture(Gdx.files.internal("door.png")));
		//--------------------------------------------------------------------------
		
		// MainRoom Stage
		skin_.add("MainRoom", new Texture(Gdx.files.internal("house.png")));
		
		// Kitchen Stage
		skin_.add("Kitchen", new Texture(Gdx.files.internal("kitchen.png")));
		skin_.add("FoodAppleUp", new Texture(Gdx.files.internal("food_apple_normal.png")));
		skin_.add("FoodAppleDown", new Texture(Gdx.files.internal("food_apple_active.png")));
		skin_.add("FoodCarrotUp", new Texture(Gdx.files.internal("food_carrot_normal.png")));
		skin_.add("FoodCarrotDown", new Texture(Gdx.files.internal("food_carrot_active.png")));
		skin_.add("FoodPizzaUp", new Texture(Gdx.files.internal("food_pizza_normal.png")));
		skin_.add("FoodPizzaDown", new Texture(Gdx.files.internal("food_pizza_active.png")));
		
		// Bathroom Stage
		skin_.add("Bathroom", new Texture(Gdx.files.internal("bath.png")));
		
		// Playroom Stage
		skin_.add("Playroom", new Texture(Gdx.files.internal("playroom.png")));
		
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
		
		
		// Other Stuff
		skin_.add("sound_off", new Texture(Gdx.files.internal("btn_sound_off.png")));
		skin_.add("sound_on", new Texture(Gdx.files.internal("btn_sound_on.png")));
		
		skin_.add("btn_dialog_yes", new Texture(Gdx.files.internal("btn_yes.png")));
		skin_.add("btn_dialog_no", new Texture(Gdx.files.internal("btn_no.png")));
		
		skin_.add("dialog_bgnd", new Texture(Gdx.files.internal("menue.png")));
		
		// Dialog
		font_generator_ = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));;
		@SuppressWarnings("deprecation")
		BitmapFont font = font_generator_.generateFont((int) Utils.GetPixelX(4));
		LabelStyle label_style_ = new LabelStyle(font, Color.BLACK);

		
		ImageButtonStyle ibstyle = new ImageButtonStyle();
		ibstyle.down = skin_.getDrawable("btn_dialog_yes");
		ibstyle.up = skin_.getDrawable("btn_dialog_yes");
		
		ImageButtonStyle ibstyle_2 = new ImageButtonStyle();
		ibstyle_2.down = skin_.getDrawable("btn_dialog_no");
		ibstyle_2.up = skin_.getDrawable("btn_dialog_no");
		
		TextButtonStyle tb_style = new TextButtonStyle();
		
		WindowStyle dialog = new WindowStyle(font, Color.BLACK, skin_.getDrawable("dialog_bgnd"));
		

		
		skin_.add("default", font);
		skin_.add("default", tb_style);
		skin_.add("yes", ibstyle);
		skin_.add("no", ibstyle_2);
		skin_.add("default", label_style_);
		skin_.add("dialog", dialog);
		
		ImageButtonStyle sound_on = new ImageButtonStyle();
		ImageButtonStyle sound_off = new ImageButtonStyle();
		
		sound_on.up = skin_.getDrawable("sound_on");
		sound_on.down = skin_.getDrawable("sound_on");
		
		sound_off.up = skin_.getDrawable("sound_off");
		sound_off.down = skin_.getDrawable("sound_off");
		
		skin_.add("sound_on", sound_on);
		skin_.add("sound_off", sound_off);
	}
	

	@Override
	public void resize(int width, int height) 
	{
	    // See below for what true means.
	    stages_.get(getCurrent_room_()).getViewport().update(width, height, true);
	}
	
	@Override
	public void render(float delta) 
	{
		if(!(isSoundEnabled()))
			music.pause();
		else
			music.play();

		system_time_ += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stages_.get(getCurrent_room_()).act(Gdx.graphics.getDeltaTime());
		stages_.get(getCurrent_room_()).PostAct();
		stages_.get(getCurrent_room_()).draw();
	}
	
	public static FreeTypeFontGenerator getStandardFontGenerator()
	{
		return font_generator_;
	}
	
	public void checkStatsonTime()
	{
		if(this.last_day_ == Calendar.getInstance().get(Calendar.DAY_OF_YEAR))
		{
			if(this.last_hour_ < Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
			{
				this.stats_timefactor_ = this.last_hour_ - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				
				nusselts_stats_.setHunger(nusselts_stats_.getHunger() - this.stats_timefactor_ * 2);
				if(nusselts_stats_.getHunger() < 0)
					nusselts_stats_.setHunger(0);
				nusselts_stats_.setHappiness(nusselts_stats_.getHappiness() - this.stats_timefactor_ * 2);
				if(nusselts_stats_.getHappiness() < 0)
					nusselts_stats_.setHappiness(0);
				nusselts_stats_.setDirtness(nusselts_stats_.getDirtness() - this.stats_timefactor_ * 2);
				if(nusselts_stats_.getDirtness() < 0)
					nusselts_stats_.setDirtness(0);
				nusselts_stats_.setTiredness(nusselts_stats_.getTiredness() - this.stats_timefactor_ * 2);
				if(nusselts_stats_.getTiredness() < 0)
					nusselts_stats_.setTiredness(0);
			}
		}
		else if(this.last_day_ == (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1) && 
				(this.last_hour_ >  Calendar.getInstance().get(Calendar.HOUR_OF_DAY)))
		{
			this.stats_timefactor_ = - Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - this.last_hour_;
			
			nusselts_stats_.setHunger(nusselts_stats_.getHunger() -  this.stats_timefactor_ * 2);
			if(nusselts_stats_.getHunger() < 0)
				nusselts_stats_.setHunger(0);
			nusselts_stats_.setHappiness(nusselts_stats_.getHappiness() -  this.stats_timefactor_ * 2);	
			if(nusselts_stats_.getHappiness() < 0)
				nusselts_stats_.setHappiness(0);
			nusselts_stats_.setDirtness(nusselts_stats_.getDirtness() -  this.stats_timefactor_ * 2);
			if(nusselts_stats_.getDirtness() < 0)
				nusselts_stats_.setDirtness(0);
			nusselts_stats_.setTiredness(nusselts_stats_.getTiredness() -  this.stats_timefactor_ * 2);
			if(nusselts_stats_.getTiredness() < 0)
				nusselts_stats_.setTiredness(0);
			
		}
		else if(this.last_day_ == (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1) && 
				(this.last_hour_ <=  Calendar.getInstance().get(Calendar.HOUR_OF_DAY)))
		{
			nusselts_stats_.setHunger(nusselts_stats_.getHunger() - 50);
			if(nusselts_stats_.getHunger() < 0)
				nusselts_stats_.setHunger(0);
			nusselts_stats_.setHappiness(nusselts_stats_.getHappiness() - 50);	
			if(nusselts_stats_.getHappiness() < 0)
				nusselts_stats_.setHappiness(0);
			nusselts_stats_.setDirtness(nusselts_stats_.getDirtness() - 50);	
			if(nusselts_stats_.getDirtness() < 0)
				nusselts_stats_.setDirtness(0);
			nusselts_stats_.setTiredness(nusselts_stats_.getTiredness() - 50);
			if(nusselts_stats_.getTiredness() < 0)
				nusselts_stats_.setTiredness(0);
			nusselts_stats_.setWeight(nusselts_stats_.getWeight() - 0.5f);
		}
		else if(this.last_day_ <= (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 2) && 
				( this.last_hour_ <= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)))
		{
			nusselts_stats_.setHunger(nusselts_stats_.getHunger() - 100);
			if(nusselts_stats_.getHunger() < 0)
				nusselts_stats_.setHunger(0);
			nusselts_stats_.setHappiness(nusselts_stats_.getHappiness() - 100);	
			if(nusselts_stats_.getHappiness() < 0)
				nusselts_stats_.setHappiness(0);
			nusselts_stats_.setDirtness(nusselts_stats_.getDirtness() - 100);
			if(nusselts_stats_.getDirtness() < 0)
				nusselts_stats_.setDirtness(0);
			nusselts_stats_.setTiredness(nusselts_stats_.getTiredness() - 100);
			if(nusselts_stats_.getTiredness() < 0)
				nusselts_stats_.setTiredness(0);
			nusselts_stats_.setWeight(nusselts_stats_.getWeight() - 1f);
		}
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


	@Override
	public void hide() {
		// Nothing to do
		
	}

	@Override
	public void pause() {
		// Nothing to do
		
	}

	@Override
	public void resume() {
		// Nothing to do
		
	}

	@Override
	public void dispose() {
		skin_.dispose();
	}
}
