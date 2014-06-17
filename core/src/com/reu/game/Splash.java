package com.reu.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class Splash implements Screen{
	
	private Texture texture = new Texture(Gdx.files.internal("loading_screen.png"));
    private Image splashImage = new Image(texture);
    private Stage stage = new Stage();
    
	@Override
    public void render(float delta) {   
		Gdx.gl.glClearColor(0,0,0,1); //sets clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        stage.act(); //update all actors
        stage.draw(); //draw all actors on the Stage.getBatch()
    }

    @Override
    public void resize(int width, int height) {             
    }

    @Override
    public void show() { 
    	stage.addActor(splashImage);
    }

    @Override
    public void hide() {  
    	dispose();
    }

    @Override
    public void pause() {           
    }

    @Override
    public void resume() {          
    }

    @Override
    public void dispose() {  
    	texture.dispose();
        stage.dispose();
    }

}
