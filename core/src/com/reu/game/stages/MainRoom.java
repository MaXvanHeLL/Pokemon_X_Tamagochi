package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.esotericsoftware.tablelayout.Cell;
import com.reu.game.ReuGame;
import com.reu.game.monster.Monster;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.mainroom.MainroomMonster;
import com.reu.game.stages.actors.MainRoomPortal;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class MainRoom extends ReuGameStage{
	
	public static RoomType type_ = RoomType.MAINROOM;
	private Table 			table_;
	private Monster 		monster_;
	private MainRoomPortal 	portal_;
	private Container 		slider_;
	
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
		

		
		
		// Uncomment this code to see were the kitchen would be... Use it to find the right
		// Measures for the kitchen
		Table slider_table_ = new Table();
		slider_table_.align(Align.top | Align.left);

		
		
		Table hunger = new Table();
		hunger.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		hunger.setBackground(parent.getSkin().getDrawable("red"));
		hunger.add(new Image(parent.getSkin().getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		slider_table_.add(hunger).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		Table happyness = new Table();
		happyness.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		happyness.setBackground(parent.getSkin().getDrawable("green"));
		happyness.add(new Image(parent.getSkin().getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		slider_table_.add(happyness).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		slider_table_.add(new Image(parent.getSkin().getDrawable("green"))).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f)).padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f)).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		
		Table tiredness = new Table();
		tiredness.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		tiredness.setBackground(parent.getSkin().getDrawable("red"));
		tiredness.add(new Image(parent.getSkin().getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		slider_table_.add(tiredness).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		Table dirtyness = new Table();
		dirtyness.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		dirtyness.setBackground(parent.getSkin().getDrawable("green"));
		dirtyness.add(new Image(parent.getSkin().getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		slider_table_.add(dirtyness).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		table_.add(slider_table_).expand().bottom();
				
		/*Cell bath = table_.add(new Image(parent.getSkin().getDrawable("white")));
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
			
		
		
		
		// Get the right monster for this room, the monster factory will make
		// decisions for us!
		monster_ = MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// Create the portal! Nusselts loves portals!
		portal_ = new MainRoomPortal(Utils.GetPixelX(32.5f), Utils.GetPixelY(83.5f));
		addActor(portal_);
		
		// This code is only here for testing purpose. It will be removed later,
		// but it shows how clicking and moving could work.
		addListener(new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{				
				MainroomMonster temp = (MainroomMonster) monster_;
				if(Utils.MonsterInRectangle(kitchen_area_, x, y))
				{
					// you really wana have that in PostAct()? gets called everytime in render()?!
					System.out.println("Would enter kitchen!");
					temp.GoToRoom(RoomType.KITCHEN);
				}
				if(Utils.MonsterInRectangle(bathroom_area_, x, y))
				{
					temp.GoToRoom(RoomType.BATHROOM);
					System.out.println("Would enter bathroom!");
				}
				if(Utils.MonsterInRectangle(playroom_area_, x, y))
				{
					temp.GoToRoom(RoomType.PLAYROOM);
					System.out.println("Would enter playroom!");
				}
				if(Utils.MonsterInRectangle(bedroom_area_, x, y))
				{
					temp.GoToRoom(RoomType.BEDROOM);
					System.out.println("Would enter bedroom!");
				}
				//temp.MoveTo(x, y);
				return true;
			}
		});
				
				
		// Compute rectangles for the rooms! Rectangles fit the background!
	    kitchen_area_ = new Rectangle(0, Gdx.graphics.getHeight()-Utils.GetPixelY(40),Utils.GetPixelX(41.0f),Utils.GetPixelY(40));
	    bathroom_area_ = new Rectangle(Gdx.graphics.getWidth()-Utils.GetPixelX(40.7f), Gdx.graphics.getHeight()-Utils.GetPixelY(40),Utils.GetPixelX(40.7f),Utils.GetPixelY(40));
	    playroom_area_ = new Rectangle(0, Gdx.graphics.getHeight()-Utils.GetPixelY(75),Utils.GetPixelX(32.5f),Utils.GetPixelY(35));
	    bedroom_area_ = new Rectangle(Gdx.graphics.getWidth()-Utils.GetPixelX(32.5f), Gdx.graphics.getHeight()-Utils.GetPixelY(75),Utils.GetPixelX(32.5f),Utils.GetPixelY(35));
	}
	
	public void ResetRoom()
	{
		monster_.Reset();
	}
	
	@Override
	public void PostAct()
	{
		// This is test functionality... Whenever nusselts enters the kitchen region the game
		// should close (later it should switch to the kitchen stage!)
		if(Utils.MonsterInRectangle(kitchen_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			parent_.SetCurrentStage(RoomType.KITCHEN);
			ResetRoom();
		}
		/*if(Utils.PointInRectangle(bedroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			System.exit(0);
		}*/
	}
	
	  @Override
	   public boolean keyDown(int keycode) {
		  
	 
		  parent_.getPrefs().putFloat("hunger", this.parent_.getNusselts_stats_().getHunger());
		  parent_.getPrefs().putFloat("happiness", this.parent_.getNusselts_stats_().getHappiness());
		  parent_.getPrefs().putFloat("tiredness", this.parent_.getNusselts_stats_().getTiredness());
		  parent_.getPrefs().putFloat("dirtness", this.parent_.getNusselts_stats_().getDirtness());

		  // -- always flush after changing the Preferences to make effect on Memory
		  parent_.getPrefs().flush();
				  
		 // -- just temporary dirty work, no cleanup :D we should override or enhance dispose() method for 
		 // all the fancy Memory flushing stuff which is done here :)
		 System.exit(0);
	        return false;
	   }
	 
}
