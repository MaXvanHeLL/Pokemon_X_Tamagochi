package com.reu.game;





import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.reu.game.monster.mainroom.MainroomMonster;
import com.reu.game.monster.mainroom.MainroomNuddelts;
import com.reu.game.types.RoomType;




public class ReuGame extends ApplicationAdapter {
	
	RoomType currentRoom;
	
	Stage stage;	
	Table table;
	Skin skin;
	Texture tex;
	MainroomMonster test;
	
	Rectangle kitchen_area;
	

	
	@Override
	public void create () {
		this.currentRoom = RoomType.MAINROOM;
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		Skin skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		skin.add("room", new Texture(Gdx.files.internal("house.png")));

		
		// Create a table that fills the screen. Everything else will go inside this table.
	    table = new Table();
		table.setBackground(skin.getDrawable("room"));
		table.setFillParent(true);
		table.align(Align.top | Align.left);
		stage.addActor(table);
		
		// Add dummy object to table... else screen would be empty
		table.add(new Image(skin.getDrawable("white")));

		
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
		});*/
					
		

		// Hier wird "Knuddel" implementiert!
		test = new MainroomNuddelts();
		stage.addActor(test);
		// Mache die ganze stage "klickbar" - wir sollten über Wege nachdenken
		stage.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        test.MoveTo(x, y);
		        return true;
		}});
		
		
		// compute a rectangle for REGION of KITCHEN 
		kitchen_area = new Rectangle(0,	Gdx.graphics.getWidth(),100,50);

	}

	@Override
	public void resize (int width, int height) {
	    // See below for what true means.
	    stage.getViewport().update(width, height, true);
	}
	
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
}
