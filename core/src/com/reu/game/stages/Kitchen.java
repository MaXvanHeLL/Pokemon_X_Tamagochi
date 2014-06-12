package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.reu.game.ReuGame;
import com.reu.game.monster.Monster;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.types.RoomType;

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
		
		// important for catching the Back Button to avoid program Exit !!
		Gdx.input.setCatchBackKey(true);

/*		// I wanted to add the InputProcessor Listener directly to the class "Kitchen",
 * 		   but it didnt worked that way - final implementation see below ;)
 * 
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
		  
	        if(keycode == Keys.BACK){
	        	  this.parent_.SetCurrentStage(RoomType.MAINROOM);
				  return true;
	        }
	        return false;
	   }



}
