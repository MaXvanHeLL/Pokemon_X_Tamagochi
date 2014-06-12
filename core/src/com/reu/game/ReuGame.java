package com.reu.game;

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

public class ReuGame extends ApplicationAdapter {
	
	// Use the monster type static for now, could be read from config later!
	private static MonsterType MONSTER_TYPE = MonsterType.NUSSELTS;
	
	private Stats nusselts_stats_;
	private Preferences prefs;
	
	@SuppressWarnings("unused")
	private GameMode current_mode_;
	private RoomType current_room_;				// The room which is currently active
	private Map<RoomType, ReuGameStage> stages_;// Includes all stages
	private Skin skin_;							// The skin for our game

	@Override
	public void create () {
		// Create the skins for our game!
		CreateSkins();
		
		// Add all the stages to the stage map!
		stages_ = new HashMap<RoomType, ReuGameStage>();
		stages_.put(RoomType.MAINROOM, new MainRoom(this));
		stages_.put(RoomType.KITCHEN, new Kitchen(this));
		
		
		// Set the stage to the Mainroom on start up
		SetCurrentStage(RoomType.MAINROOM);
		// Set the Game Mode to Care Mode on start up
		SetCurrentGameMode(GameMode.CARE_MODE);
		
		// -- loading Nusselts Stats from Phone_Memory
		// ----------------------------------------------
		setPrefs(Gdx.app.getPreferences("Nusselts Preferences"));
	//	this.setNusselts_stats_(new Stats(getPrefs().getFloat("hunger", 100), 100, 100, 100, 100, 100, 100));
		nusselts_stats_ = new Stats();
		nusselts_stats_.setHunger(getPrefs().getFloat("hunger", 100));
		nusselts_stats_.setHappiness(getPrefs().getFloat("happiness", 100));	
		nusselts_stats_.setTiredness(getPrefs().getFloat("tiredness", 100));
		nusselts_stats_.setDirtness(getPrefs().getFloat("dirtness", 100));

		
		
	}
	
	/***
	 * Returns the skin of the current game
	 * @return The skin
	 */
	public Skin getSkin(){
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
	private void CreateSkins(){
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin_ = new Skin();
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin_.add("white", new Texture(pixmap));
		
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

		
	}

	@Override
	public void resize (int width, int height) {
	    // See below for what true means.
	    stages_.get(getCurrent_room_()).getViewport().update(width, height, true);
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stages_.get(getCurrent_room_()).act(Gdx.graphics.getDeltaTime());
		stages_.get(getCurrent_room_()).PostAct();
		stages_.get(getCurrent_room_()).draw();
	}

	public RoomType getCurrent_room_() {
		return current_room_;
	}

	public void setCurrent_room_(RoomType current_room_) {
		this.current_room_ = current_room_;
	}

	public Stats getNusselts_stats_() {
		return nusselts_stats_;
	}

	public void setNusselts_stats_(Stats nusselts_stats_) {
		this.nusselts_stats_ = nusselts_stats_;
	}

	public Preferences getPrefs() {
		return prefs;
	}

	public void setPrefs(Preferences prefs) {
		this.prefs = prefs;
	}
	
}





// OUTAKES - This comments should be deleted! They are just here to help us do tricky things in libgdx


/*// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
final TextButton button = new TextButton("Click me!", skin);
table.add(button).width(200);

// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
// revert the checked state.
button.addListener(new ChangeListener() {
public void changed (ChangeEvent event, Actor actor) {
System.out.println("Clicked! Is checked: " + button.isChecked());
button.setText("Good job!");
}
});







	@Override
	public void render () {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
// ###############################               ##########################################
// Following Code is just a simple dirty implementation to check if NUSSELTS has entered
// a specific region in his cuddly MAINROOM. Program exits now when NUDDELTS enters his kitchen
// Implementation of pointInRectangle() Method - see furhter blow.
// ############################## # # # # # # # # #########################################
		

		if(pointInRectangle(kitchen_area, test.getX(), test.getY()))
			this.currentRoom = RoomType.KITCHEN;
						
// shall be the switch case where Stage-Change will take place in future.
// for example: Change MAINROOM Stage with KITCHEN stage in future
		switch(this.currentRoom) {
		
		case MAINROOM : stage.act(Gdx.graphics.getDeltaTime());
						stage.draw();
						break;
						
		case KITCHEN : System.exit(0);
					   break;
		}

	}
	
//	implemented a Method for further use of REGION tests
//	@param_1 : Rectangle as REGION OF INTEREST
//  @param_2 : X coordination of Testsubject (mostly NUSSELTS ^.^)
//  @param_3 : Y Coordination of Testsubject (mostly NUSSELTS ^.^)
// --------------------------------------------------------------------
	public static boolean pointInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
*
*/


