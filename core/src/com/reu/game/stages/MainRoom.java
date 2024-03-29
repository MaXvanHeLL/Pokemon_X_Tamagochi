package com.reu.game.stages;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.reu.game.ReuGame;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.mainroom.MainroomMonster;
import com.reu.game.stages.actors.MainRoomPortal;
import com.reu.game.stages.actors.SlidingStats;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class MainRoom extends ReuGameStage{
	
	Music snore_sound_;
	
	
	
	public static RoomType type_ = RoomType.MAINROOM;
	private SlidingStats	slider_table_;
	private MainroomMonster	monster_;
	private Dialog			dialog_exit_;
	private MainRoomPortal 	portal_;
	
	private float sleeping_started_;

	
	private Rectangle kitchen_area_;
	private Rectangle bathroom_area_;
	private Rectangle playroom_area_;
	private Rectangle bedroom_area_;
	@SuppressWarnings("unused")
	private Rectangle garden_area_;
	
	public MainRoom(final ReuGame parent){
		super(parent);
		
		
    	snore_sound_ = Gdx.audio.newMusic(Gdx.files.internal("snore.mp3"));
    	snore_sound_.setVolume(0.5f);
		snore_sound_.setLooping(false);
		
		// -- important for catching the Back Button !!
		Gdx.input.setCatchBackKey(true);
				
		// Create a table that fills the screen. Everything else will go inside.
		Image background = new Image(parent.getSkin().getDrawable("MainRoom"));
		background.setWidth(Utils.GetPixelX(90));
		background.setHeight(Utils.GetPixelY(160));
		addActor(background);
		
		getActors().get(0).setZIndex(10);
				
		// Get the right monster for this room, the monster factory will make
		// decisions for us!
		monster_ = (MainroomMonster)MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		dialog_exit_ = new Dialog("", parent_.getSkin(), "dialog") 
		{
		    protected void result (Object object)
		    {
		        if(object.equals(true))
		        	System.exit(0);
		    }
		};
		
		
		// Create the "close"-dialog
		dialog_exit_.text("Are you sure that you want\n to leave your friend alone?");
		dialog_exit_.getContentTable().pad(Utils.GetPixelX(5));
		dialog_exit_.button(new ImageButton(parent_.getSkin().get("yes", ImageButtonStyle.class)), true);
		dialog_exit_.getButtonTable().padTop(Utils.GetPixelY(5)).padBottom(Utils.GetPixelY(5));
		dialog_exit_.getButtonTable().add().width(Utils.GetPixelX(10));
		dialog_exit_.button(new ImageButton(parent_.getSkin().get("no", ImageButtonStyle.class)), false);

		
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
				if(Utils.MonsterInRectangle(kitchen_area_, x, y))
				{
					if (monster_.isSleeping())
					{
						moveSleepingMonster();
					}
					monster_.GoToRoom(RoomType.KITCHEN);
				}
				if(Utils.MonsterInRectangle(bathroom_area_, x, y))
				{
					if (monster_.isSleeping())
					{
						moveSleepingMonster();
					}
					monster_.GoToRoom(RoomType.BATHROOM);
				}
				if(Utils.MonsterInRectangle(playroom_area_, x, y))
				{
					if (monster_.isSleeping())
					{
						moveSleepingMonster();
					}
					monster_.GoToRoom(RoomType.PLAYROOM);
				}
				if(Utils.MonsterInRectangle(bedroom_area_, x, y) && !monster_.isSleeping())
				{
					monster_.GoToRoom(RoomType.BEDROOM);
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
	
	@Override
	public void ResetRoom()
	{
		monster_.Reset();
		slider_table_.updateStats();
		CorrectSoundButton();
	}
	
	private void moveSleepingMonster()
	{
		monster_.stopSleeping();
		snore_sound_.stop();
		snore_sound_.setLooping(false);
		CorrectSoundButton();
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
		}
		if(Utils.MonsterInRectangle(bathroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			parent_.SetCurrentStage(RoomType.BATHROOM);;
		}
		if(Utils.MonsterInRectangle(playroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			parent_.SetCurrentStage(RoomType.PLAYROOM);
		}
		if(Utils.MonsterInRectangle(bedroom_area_, monster_.GetCenterX(), monster_.GetCenterY()))
		{
			if(parent_.getNusselts_stats_().getTiredness() < 100 && monster_.getWaypoints_().isEmpty())
			{				
				monster_.sleepTime();
				if(ReuGame.isSoundEnabled())
				{
				  snore_sound_.play();
		    	  snore_sound_.setLooping(true);
				}
				else
					snore_sound_.pause();

				if((sleeping_started_ + 0.1) < ReuGame.getSystemTime())
				{
					sleeping_started_ = ReuGame.getSystemTime();                                           
					parent_.getNusselts_stats_().setTiredness(parent_.getNusselts_stats_().getTiredness() + 0.2f);
					
					parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() - 0.06f);
		        	if((parent_.getNusselts_stats_().getHunger() < 0))
		        		  parent_.getNusselts_stats_().setHunger(0);
		        		
		        	parent_.getNusselts_stats_().setHappiness(parent_.getNusselts_stats_().getHappiness() - 0.04f);
		        	if((parent_.getNusselts_stats_().getHappiness() < 0))
		        		  parent_.getNusselts_stats_().setHappiness(0);
		        	
					if(parent_.getNusselts_stats_().getTiredness() > 100)
					{
						parent_.getNusselts_stats_().setTiredness(100);
					}
				}
			}			
			else if (monster_.isSleeping())
			{					 
				monster_.stopSleeping();
				snore_sound_.stop();
				snore_sound_.setLooping(false);
				if(!monster_.getWaypoints_().isEmpty())
				{
					monster_.MoveTo(monster_.getWaypoints_().get(0).x, monster_.getWaypoints_().get(0).y, 0.5f);
				}
			}
			else if(monster_.getWaypoints_().isEmpty())
			{
				monster_.stopSleeping();
			}   	 
				
		 }
	}
	
	@Override
	public boolean keyDown(int keycode) 
	{

		if(keycode == Keys.BACK)
		{
			parent_.getPrefs().putFloat("hunger", this.parent_.getNusselts_stats_().getHunger());
			parent_.getPrefs().putFloat("happiness", this.parent_.getNusselts_stats_().getHappiness());
			parent_.getPrefs().putFloat("tiredness", this.parent_.getNusselts_stats_().getTiredness());
			parent_.getPrefs().putFloat("dirtness", this.parent_.getNusselts_stats_().getDirtness());
			parent_.getPrefs().putString("name", this.parent_.getNusselts_stats_().getName());
			parent_.getPrefs().putFloat("weight", this.parent_.getNusselts_stats_().getWeight());
			parent_.getPrefs().putLong("creation", this.parent_.getNusselts_stats_().getCreationDate());
			parent_.getPrefs().putInteger("Day", Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
			parent_.getPrefs().putInteger("Hour", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		
				
			// -- always flush after changing the Preferences to make effect on Memory
			parent_.getPrefs().flush();
				
			dialog_exit_.show(this);
		}
		

				  
		// -- just temporary dirty work, no cleanup :D we should override or enhance dispose() method for 
		// all the fancy Memory flushing stuff which is done here :)

	    return false;
	}
}
