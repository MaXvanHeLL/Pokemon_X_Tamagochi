package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reu.game.ReuGame;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.playroom.PlayroomMonster;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class Playroom extends ReuGameStage{
	
	Music laughing_sound_;
	Music nono_sound_;
	
	public static RoomType type_ = RoomType.PLAYROOM;
	
	private Table table_;
	private Table stack_table_;
	private Stack bar_stack_;
	private PlayroomMonster monster_;

	public Playroom(ReuGame parent) 
	{
		super(parent);
		this.parent_ = parent;
		laughing_sound_ = Gdx.audio.newMusic(Gdx.files.internal("laughing.mp3"));
		laughing_sound_.setVolume(0.5f);                 // sets the volume to half the maximum volume
 	    laughing_sound_.setLooping(false);
 		nono_sound_ = Gdx.audio.newMusic(Gdx.files.internal("nono.mp3"));
		nono_sound_.setVolume(0.5f);                 
 	    nono_sound_.setLooping(false);
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("Playroom"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.center);
		

		createStackTable();
		
		buildTable();
		
		addActor(table_);
		
		// Create monster
		monster_ = (PlayroomMonster)MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// Add a input listener
		addListener(new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{				
				if(monster_.isClicked(x, y))
				{
					 if(parent_.getNusselts_stats_().getHappiness() <= 90)
				        {
						 
						 if(laughing_sound_.isPlaying())
				        	  laughing_sound_.stop();
			    
			    		laughing_sound_.play();
			    		
				        	System.out.println(parent_.getNusselts_stats_().getHappiness());
				        	parent_.getNusselts_stats_().setHappiness(parent_.getNusselts_stats_().getHappiness() + 10);
				        	if(!(parent_.getNusselts_stats_().getDirtness() <= 0))
					          parent_.getNusselts_stats_().setDirtness(parent_.getNusselts_stats_().getDirtness() - 1);
					        if(!(parent_.getNusselts_stats_().getHunger() <= 0))
					          parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() - 2);
					        if(!(parent_.getNusselts_stats_().getTiredness() <= 0))
					          parent_.getNusselts_stats_().setTiredness(parent_.getNusselts_stats_().getTiredness() - 5);
				
				        	monster_.playSomething();
				        	createStackTable();
				        	buildTable();
				        }
				        else
				        {
				        	laughing_sound_.stop();
				        	
				        	if(nono_sound_.isPlaying())
								nono_sound_.stop();
							  
					  		nono_sound_.play();
				        	monster_.pleaseNoMoreFun();
				        }
				}
				return true;
			}
		});
		
		// -- important for catching the Back Button to avoid program Exit !!
		Gdx.input.setCatchBackKey(true);	
	}
	
	private void buildTable()
	{
		table_.clearChildren();
		table_.add(stack_table_).height(Utils.GetPixelY(135)).width(Utils.GetPixelX(90));
	}
	
	private void createStackTable()
	{
		stack_table_ = new Table();
		stack_table_.align(Align.top | Align.center);
		
		
		stack_table_.add().minHeight(Utils.GetPixelY(10)).width(Utils.GetPixelX(90)).colspan(3);
		stack_table_.row();
		stack_table_.add().width(Utils.GetPixelX(15));
		
		
		bar_stack_ = new Stack();
		bar_stack_.setWidth(Utils.GetPixelX(60f));
		bar_stack_.setHeight(Utils.GetPixelY(5f));
		bar_stack_.add(new Image(parent_.getSkin().getDrawable("red")));
		Table bar_table = new Table();
		bar_table.setFillParent(true);
		bar_table.add(new Image(parent_.getSkin().getDrawable("green"))).width(Utils.GetPixelX(60 * parent_.getNusselts_stats_().getHappiness() / 100.f)).height(Utils.GetPixelY(5f)).left();
		bar_table.add().width(Utils.GetPixelX(60 * (1 - parent_.getNusselts_stats_().getHappiness() / 100.f)));
		bar_stack_.add(bar_table);
		bar_stack_.add(new Image(parent_.getSkin().getDrawable("BarFrame")));
		
		
		stack_table_.add(bar_stack_).width(Utils.GetPixelX(60)).height(Utils.GetPixelY(5)); // Will be stack
		
		stack_table_.add().width(Utils.GetPixelX(15));
		
		
		//-------------------------------------------------------------------------------------------------------------------------
		// Test button which resets happiness to 0
				
		stack_table_.row();
		ImageButtonStyle test_style = new ImageButtonStyle();
		test_style.up = parent_.getSkin().getDrawable("DebugButtonUp");
		test_style.down = parent_.getSkin().getDrawable("DebugButtonDown");
		ImageButton test_button = new ImageButton(test_style);
				
		test_button.addListener( new ClickListener() 
		{             
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{
				parent_.getNusselts_stats_().setHappiness(0);
				createStackTable();
	        	buildTable();
			};
		});
		stack_table_.add();
		stack_table_.add(test_button).center().width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padTop(Utils.GetPixelY(10));
				

		//-------------------------------------------------------------------------------------------------------------------------
		
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
	
	@Override
	public void ResetRoom()
	{
		monster_.Reset();
		createStackTable();
		buildTable();
	}
	
	@Override
	public void PostAct(){
	}
}