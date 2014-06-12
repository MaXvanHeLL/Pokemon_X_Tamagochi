package com.reu.game.stages;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.reu.game.ReuGame;
import com.reu.game.monster.Monster;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.mainroom.MainroomMonster;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class Kitchen extends ReuGameStage{
	ReuGame parent_;
	public static RoomType type_ = RoomType.KITCHEN;
	private Table table_;
	private Monster monster_;
	
	public Kitchen(ReuGame parent){
		super(parent);
		this.parent_ = parent;
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("Kitchen"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.left);
		addActor(table_);
		
		monster_ = MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		
		Gdx.input.setCatchBackKey(true);
		
		System.out.println(parent.getCurrent_room_());



/*		
		Gdx.input.setInputProcessor(new InputAdapter () {
			   public boolean keyDown (int keycode) {
				   if(keycode == Keys.BACK){
						  System.out.println("BAAAAAAAACK");

					 }	
				   else
						  System.out.println("NOOOOOOOOOOOOOOO");
				   
				   return true;

			   }

			});

*/				
			
		
		
		
	}
	
	  @Override
	   public boolean keyDown(int keycode) {
		  
		  
			System.out.println(parent_.getCurrent_room_());

		  
	        if(keycode == Keys.BACK){
	        	  this.parent_.SetCurrentStage(RoomType.MAINROOM);
				  System.out.println("BAAAAAAAACK");
				  return true;
	        }
	        return false;
	   }



}
