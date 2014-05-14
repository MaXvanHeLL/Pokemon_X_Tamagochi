package com.reu.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;


public class ReuGame extends ApplicationAdapter {
	Stage stage;
	Skin skin;
	Texture tex;
	TestObject test;
	
	@Override
	public void create () {
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
		Table table = new Table();
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
		test = new TestObject();
		stage.addActor(test);
		// Mache die ganze stage "klickbar" - wir sollten �ber Wege nachdenken
		stage.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        test.MoveTo(x, y);
		        return true;
		}});
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
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		Table.drawDebug(stage);
	}
}
