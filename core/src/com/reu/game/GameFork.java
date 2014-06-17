package com.reu.game;

import com.badlogic.gdx.Game;

public class GameFork  extends Game{
	private int counter = 0;
	private Splash mySplash;
	private ReuGame myGame;
	
	@Override
	public void create () {
		mySplash = new Splash();
		setScreen(mySplash);
	}
	
	@Override
	public void render(){
		super.render();
		
		if(counter == 2)
		{
			myGame = new ReuGame();
			setScreen(myGame);
			mySplash.pause();
		}

		counter++;
	}
	
	@Override
	public void dispose(){
		super.dispose();
		myGame.dispose();
		mySplash.dispose();
	}

}
