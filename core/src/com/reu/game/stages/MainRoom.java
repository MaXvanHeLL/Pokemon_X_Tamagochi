package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.reu.game.ReuGame;
import com.reu.game.monster.Monster;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.mainroom.MainroomMonster;
import com.reu.game.stages.actors.MainRoomPortal;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class MainRoom extends ReuGameStage{
	
	public static RoomType type_ = RoomType.MAINROOM;
	private Table table_;
	private Monster monster_;
	private MainRoomPortal portal_;
	
	private Rectangle kitchen_area_;
	private Rectangle bathroom_area_;
	private Rectangle playroom_area_;
	private Rectangle bedroom_area_;
	@SuppressWarnings("unused")
	private Rectangle garden_area_;
	
	public MainRoom(final ReuGame parent){
		super(parent);
		
		
		// -- important for catching the Back Button !!
				Gdx.input.setCatchBackKey(true);
		
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("MainRoom"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.left);
		addActor(table_);
		
		// Add dummy object to table... else screen would be empty, table needs
		// at least one object to be shown.
		//table_.add(new Image(parent.getSkin().getDrawable("white")));
		
		
		// Uncomment this code to see were the kitchen would be... Use it to find the right
		// Measures for the kitchen
		/*Cell kitchen = table_.add(new Image(parent.getSkin().getDrawable("white")));
		kitchen.width(Utils.GetPixelX(41.0f));
		kitchen.height(Utils.GetPixelY(40));
		kitchen.align(Align.left | Align.top);
		
		Cell bath = table_.add(new Image(parent.getSkin().getDrawable("white")));
		bath.width(Utils.GetPixelX(41.0f));
		bath.height(Utils.GetPixelY(40));
		bath.align(Align.left | Align.top);
		bath.padLeft(Utils.GetPixelX(8.3f));
		
		table_.row();
		
		Cell playroom = table_.add(new Image(parent.getSkin().getDrawable("white")));
		playroom.width(Utils.GetPixelX(32.5f));
		playroom.height(Utils.GetPixelY(35));
		playroom.align(Align.left | Align.top);
		
		Cell bed = table_.add(new Image(parent.getSkin().getDrawable("white")));
		bed.width(Utils.GetPixelX(32.5f));
		bed.height(Utils.GetPixelY(35));
		bed.align(Align.left | Align.top);
		bed.padLeft(Utils.GetPixelX(16.5f));*/
		
		// Width of "upper rooms" 41 raster left, 8.3 raster padding 40.7 raster right
		// Height of "upper rooms" 40 raster
		// Width of "lower rooms" 32.5 raster left, 25 raster padding, 32.5 raster right
		// Height of "lower rooms" 35 raster
		
		
		
		// Get the right monster for this room, the monster factory will make
		// decisions for us!
		monster_ = MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// Create the portal! Nusselts loves portals!
		portal_ = new MainRoomPortal(Utils.GetPixelX(32.5f), Utils.GetPixelY(83.5f));
		addActor(portal_);
		
		// This code is only here for testing purpose. It will be removed later,
		// but it shows how clicking and moving could work.
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{				
				MainroomMonster temp = (MainroomMonster) monster_;
				if(Utils.MonsterInRectangle(kitchen_area_, x, y))
				{
					// you really wana have that in PostAct()? gets called everytime in render()?!
					System.out.println("Would enter kitchen!");
					parent.SetCurrentStage(RoomType.KITCHEN);
				}
				if(Utils.MonsterInRectangle(bathroom_area_, x, y))
				{
					System.out.println("Would enter bathroom!");
				}
				if(Utils.MonsterInRectangle(playroom_area_, x, y))
				{
					System.out.println("Would enter playroom!");
				}
				if(Utils.MonsterInRectangle(bedroom_area_, x, y))
				{
					System.out.println("Would enter bedroom!");
				}
				//temp.MoveTo(x, y);
				return true;
			}});
				
				
		// Compute rectangles for the rooms! Rectangles fit the background!
	    kitchen_area_ = new Rectangle(0, Gdx.graphics.getHeight()-Utils.GetPixelY(40),Utils.GetPixelX(41.0f),Utils.GetPixelY(40));
	    bathroom_area_ = new Rectangle(Gdx.graphics.getWidth()-Utils.GetPixelX(40.7f), Gdx.graphics.getHeight()-Utils.GetPixelY(40),Utils.GetPixelX(40.7f),Utils.GetPixelY(40));
	    playroom_area_ = new Rectangle(0, Gdx.graphics.getHeight()-Utils.GetPixelY(75),Utils.GetPixelX(32.5f),Utils.GetPixelY(35));
	    bedroom_area_ = new Rectangle(Gdx.graphics.getWidth()-Utils.GetPixelX(32.5f), Gdx.graphics.getHeight()-Utils.GetPixelY(75),Utils.GetPixelX(32.5f),Utils.GetPixelY(35));
	}
	
	@Override
	public void PostAct()
	{
		// This is test functionality... Whenever nusselts enters the kitchen region the game
		// should close (later it should switch to the kitchen stage!)
		/*if(Utils.PointInRectangle(bedroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			System.exit(0);
		}*/
	}
	
	  @Override
	   public boolean keyDown(int keycode) {
		  
	 
		  parent_.getPrefs().putFloat("hunger", this.parent_.getNusselts_stats_().getHunger());
		  parent_.getPrefs().flush();
				  
		 System.exit(0);
	        return false;
	   }
}
