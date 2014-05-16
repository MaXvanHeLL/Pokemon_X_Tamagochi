package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.reu.game.ReuGame;
import com.reu.game.monster.Monster;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.mainroom.MainroomMonster;
import com.reu.game.types.RoomType;

public class MainRoom extends ReuGameStage{
	
	public static RoomType type_ = RoomType.MAINROOM;
	private Table table_;
	private Monster monster_;
	
	@SuppressWarnings("unused")
	private Rectangle kitchen_area_;
	
	public MainRoom(ReuGame parent){
		super(parent);
		
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("MainRoom"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.left);
		addActor(table_);
		
		// Add dummy object to table... else screen would be empty, table needs
		// at least one object to be shown.
		table_.add(new Image(parent.getSkin().getDrawable("white")));
		
		// Get the right monster for this room, the monster factory will make
		// decisions for us!
		monster_ = MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// This code is only here for testing purpose. It will be removed later,
		// but it shows how clicking and moving could work.
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				MainroomMonster temp = (MainroomMonster) monster_;
				temp.MoveTo(x, y);
				return true;
			}});
				
				
		// Compute a rectangle for REGION of KITCHEN (also only test code)
	    kitchen_area_ = new Rectangle(0, Gdx.graphics.getWidth(),100,50);
	}
}
