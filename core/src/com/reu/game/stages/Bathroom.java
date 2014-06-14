package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.reu.game.ReuGame;
import com.reu.game.types.RoomType;

public class Bathroom extends ReuGameStage{
	
	public static RoomType type_ = RoomType.BATHROOM;
	
	private Table table_;

	public Bathroom(ReuGame parent) 
	{
		super(parent);
		this.parent_ = parent;
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("Bathroom"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.center);
		
		addActor(table_);
		
		// -- important for catching the Back Button to avoid program Exit !!
		Gdx.input.setCatchBackKey(true);	
	}

	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.BACK)
		{
			this.parent_.SetCurrentStage(RoomType.MAINROOM);
			return true;
	    }
	    return false;
	}
}
