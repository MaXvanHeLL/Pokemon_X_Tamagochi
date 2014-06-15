package com.reu.game.stages;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.reu.game.ReuGame;
import com.reu.game.monster.Monster;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.mainroom.MainroomMonster;
import com.reu.game.stages.actors.MainRoomPortal;
import com.reu.game.stages.actors.SlidingStats;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class MainRoom extends ReuGameStage{
	
	
	public static RoomType type_ = RoomType.MAINROOM;
	private SlidingStats	slider_table_;
	private Monster 		monster_;
	private MainRoomPortal 	portal_;
	
	private float feeding_started_;

	
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
		Image background = new Image(parent.getSkin().getDrawable("MainRoom"));
		background.setWidth(Utils.GetPixelX(90));
		background.setHeight(Utils.GetPixelY(160));
		addActor(background);
				
		// Get the right monster for this room, the monster factory will make
		// decisions for us!
		monster_ = MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// Create the portal! Nusselts loves portals!
		portal_ = new MainRoomPortal(Utils.GetPixelX(32.5f), Utils.GetPixelY(83.5f));
		addActor(portal_);
		
		// The ribbon + sliding window
		slider_table_ = new SlidingStats(parent_.getSkin(), parent_.getNusselts_stats_());
		addActor(slider_table_);
		
		// This code is only here for testing purpose. It will be removed later,
		// but it shows how clicking and moving could work.
		addListener(new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{				
				MainroomMonster temp = (MainroomMonster) monster_;
				if(Utils.MonsterInRectangle(kitchen_area_, x, y))
				{
					temp.GoToRoom(RoomType.KITCHEN);
				}
				if(Utils.MonsterInRectangle(bathroom_area_, x, y))
				{
					temp.GoToRoom(RoomType.BATHROOM);
				}
				if(Utils.MonsterInRectangle(playroom_area_, x, y))
				{
					temp.GoToRoom(RoomType.PLAYROOM);
				}
				if(Utils.MonsterInRectangle(bedroom_area_, x, y))
				{
					temp.GoToRoom(RoomType.BEDROOM);
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
	
	/***
	 * Resets the room before leaving (or maybe entering?)
	 */
	public void ResetRoom()
	{
		monster_.Reset();
	}
	
	@Override
	public void PostAct()
	{
		slider_table_.updateStats();
		// This is test functionality... Whenever nusselts enters the kitchen region the game
		// should close (later it should switch to the kitchen stage!)
		if(Utils.MonsterInRectangle(kitchen_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			parent_.SetCurrentStage(RoomType.KITCHEN);
			ResetRoom();
		}
		if(Utils.MonsterInRectangle(bathroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			parent_.SetCurrentStage(RoomType.BATHROOM);
			ResetRoom();
		}
		if(Utils.MonsterInRectangle(playroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			parent_.SetCurrentStage(RoomType.PLAYROOM);
			ResetRoom();
		}
		if(Utils.MonsterInRectangle(bedroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			System.out.println(((MainroomMonster) monster_).isSleeping());

			
				if(parent_.getNusselts_stats_().getTiredness() < 100)
				{
					((MainroomMonster) monster_).sleepTime();

					System.out.println(parent_.getNusselts_stats_().getTiredness());
					if((feeding_started_ + 0.1) < ReuGame.getSystemTime())
					{
						feeding_started_ = ReuGame.getSystemTime();
						parent_.getNusselts_stats_().setTiredness(parent_.getNusselts_stats_().getTiredness() + 1);
						if(parent_.getNusselts_stats_().getTiredness() > 100)
						{
							parent_.getNusselts_stats_().setTiredness(100);
						}
						//createStackTable();
						//buildTable();
					}
				}
				else if (((MainroomMonster) monster_).isSleeping())
				{
					((MainroomMonster)monster_).stopSleeping();
					//((MainroomMonster)monster_).MoveTo(45, 20, 100)
					//((MainroomMonster)monster_).getWaypoints_().clear();
				//	Vector2 bla = new Vector2 ();
				//	bla.x = 23;
				//	bla.y = 300;
				//	ArrayList<Vector2> test = new ArrayList();
				//	test.add(bla);
					//List<Vector2> new_waypoints = new List (bla);
					//((MainroomMonster)monster_).getWaypoints_().clear();
					//((MainroomMonster)monster_).setWaypoints_((test));
					
					//((MainroomMonster)monster_).getWaypoints_().add(new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(140)));
				  // ((MainroomMonster)monster_). setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/3.0f);
					if(!((MainroomMonster)monster_).getWaypoints_().isEmpty())
					{
						System.out.println("I AM IN BEDROOM");
						((MainroomMonster)monster_).MoveTo(((MainroomMonster)monster_).getWaypoints_().get(0).x, ((MainroomMonster)monster_).getWaypoints_().get(0).y, 0.5f);
						//((MainroomMonster)monster_).getWaypoints_().remove(0);
					}
					else
					{
						// DO NOTHING HAHAHAHAHAHA XD
					}
					//monster_.Reset();
					//createStackTable();
					//buildTable();
				}
			
		}
	}
	
	@Override
	public boolean keyDown(int keycode) 
	{
		parent_.getPrefs().putFloat("hunger", this.parent_.getNusselts_stats_().getHunger());
		parent_.getPrefs().putFloat("happiness", this.parent_.getNusselts_stats_().getHappiness());
		parent_.getPrefs().putFloat("tiredness", this.parent_.getNusselts_stats_().getTiredness());
		parent_.getPrefs().putFloat("dirtness", this.parent_.getNusselts_stats_().getDirtness());
		parent_.getPrefs().putString("name", this.parent_.getNusselts_stats_().getName());
		parent_.getPrefs().putFloat("weight", this.parent_.getNusselts_stats_().getWeight());
		parent_.getPrefs().putLong("creation", this.parent_.getNusselts_stats_().getCreationDate());

		// -- always flush after changing the Preferences to make effect on Memory
		parent_.getPrefs().flush();
				  
		// -- just temporary dirty work, no cleanup :D we should override or enhance dispose() method for 
		// all the fancy Memory flushing stuff which is done here :)
		System.exit(0);
	    return false;
	}
}
