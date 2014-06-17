package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reu.game.ReuGame;
import com.reu.game.monster.Monster;
import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.kitchen.KitchenMonster;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class Kitchen extends ReuGameStage{

	Music eat_sound_;
	Music nono_sound_;
	
	public static RoomType type_ = RoomType.KITCHEN;
	private Table table_;
	private Table stack_table_;
	private Stack bar_stack_;

	private ImageButton apple_button_;
	private ImageButton carrot_button_;
	private ImageButton pizza_button_;
	
	private Monster monster_;
	
	
	public Kitchen(ReuGame parent){
		
		super(parent);
		this.parent_ = parent;
		
    	eat_sound_ = Gdx.audio.newMusic(Gdx.files.internal("eat.mp3"));
    	eat_sound_.setVolume(0.5f);                 // sets the volume to half the maximum volume
 	    eat_sound_.setLooping(false); 
 	    
    	nono_sound_ = Gdx.audio.newMusic(Gdx.files.internal("nono.mp3"));
    	eat_sound_.setVolume(0.5f);                 // sets the volume to half the maximum volume
 	    eat_sound_.setLooping(false);


		
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("Kitchen"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.center);
		
		createButtons();
		createStackTable();
		buildTable();
		
		
		
		addActor(table_);
		
		getActors().get(0).setZIndex(10);
		
		// Create monster
		monster_ = MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// -- important for catching the Back Button to avoid program Exit !!
		Gdx.input.setCatchBackKey(true);			
	}
	
	private void buildTable()
	{
		table_.clearChildren();
		table_.add(stack_table_).height(Utils.GetPixelY(135)).width(Utils.GetPixelX(90)).colspan(3);
		table_.row();
				
		table_.add(apple_button_).width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padLeft(Utils.GetPixelX(10)).padRight(Utils.GetPixelX(5)).padBottom(Utils.GetPixelY(5));
		table_.add(carrot_button_).width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padLeft(Utils.GetPixelX(5)).padRight(Utils.GetPixelX(5)).padBottom(Utils.GetPixelY(5));
		table_.add(pizza_button_).width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padLeft(Utils.GetPixelX(5)).padRight(Utils.GetPixelX(10)).padBottom(Utils.GetPixelY(5));
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
		bar_table.add(new Image(parent_.getSkin().getDrawable("green"))).width(Utils.GetPixelX(60 * parent_.getNusselts_stats_().getHunger() / 100.f)).height(Utils.GetPixelY(5f)).left();
		bar_table.add().width(Utils.GetPixelX(60 * (1 - parent_.getNusselts_stats_().getHunger() / 100.f)));
		bar_stack_.add(bar_table);
		bar_stack_.add(new Image(parent_.getSkin().getDrawable("BarFrame")));
		
		stack_table_.add(bar_stack_).width(Utils.GetPixelX(60)).height(Utils.GetPixelY(5)); // Will be stack
		
		stack_table_.add().width(Utils.GetPixelX(15));
		
		
		//-------------------------------------------------------------------------------------------------------------------------
		// Test button which resets hunger to 0
				
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
				parent_.getNusselts_stats_().setHunger(0);
				createStackTable();
	        	buildTable();
			};
		});
		stack_table_.add();
		stack_table_.add(test_button).center().width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padTop(Utils.GetPixelY(10));
				

		//-------------------------------------------------------------------------------------------------------------------------
		
	}
	
	private void createButtons()
	{
		// Apple button
		ImageButtonStyle apple_style_ = new ImageButtonStyle();
		apple_style_.up = parent_.getSkin().getDrawable("FoodAppleUp");
		apple_style_.down = parent_.getSkin().getDrawable("FoodAppleDown");
		apple_button_ = new ImageButton(apple_style_);
		
		// Carrot button
		ImageButtonStyle carrot_style_ = new ImageButtonStyle();
		carrot_style_.up = parent_.getSkin().getDrawable("FoodCarrotUp");
		carrot_style_.down = parent_.getSkin().getDrawable("FoodCarrotDown");
		carrot_button_ = new ImageButton(carrot_style_);
		
		// Pizza button
		ImageButtonStyle pizza_style_ = new ImageButtonStyle();
		pizza_style_.up = parent_.getSkin().getDrawable("FoodPizzaUp");
		pizza_style_.down = parent_.getSkin().getDrawable("FoodPizzaDown");
		pizza_button_ = new ImageButton(pizza_style_);
		
		// Event Handler
		apple_button_.addListener( new ClickListener() 
		{             
		    @Override
		    public void clicked(InputEvent event, float x, float y) 
		    {
		    	KitchenMonster temp = (KitchenMonster)monster_;
		        if(parent_.getNusselts_stats_().getHunger() <= 90)
		        {
		        	if(ReuGame.isSoundEnabled())
					{
		        		if(eat_sound_.isPlaying())
		        			  eat_sound_.stop();
		        		
		        		eat_sound_.play();
					}
		        	else
		        	 eat_sound_.pause();

		        	parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() + 10);
		        	parent_.getNusselts_stats_().setWeight(parent_.getNusselts_stats_().getWeight() + 0.01f);
		        	
		        	parent_.getNusselts_stats_().setDirtness(parent_.getNusselts_stats_().getDirtness() - 8);
		        	if((parent_.getNusselts_stats_().getDirtness() < 0))
		        		  parent_.getNusselts_stats_().setDirtness(0);
		        	  
		        	parent_.getNusselts_stats_().setTiredness(parent_.getNusselts_stats_().getTiredness() - 5);
		        	if((parent_.getNusselts_stats_().getTiredness() < 0))
		        		parent_.getNusselts_stats_().setTiredness(0);

		        	temp.eatSomething();
		        	createStackTable();
		        	buildTable();
		        }
		        else
		        {
		        	if(ReuGame.isSoundEnabled())
					{
		        		if(nono_sound_.isPlaying())
				        	  nono_sound_.stop();
			    
			    		nono_sound_.play();
					}
		        	else
		        	 nono_sound_.pause();
		        
		        	temp.pleaseNoMoreFood();
		        }
		    };
		});
		
		carrot_button_.addListener( new ClickListener() 
		{             
			@Override
		    public void clicked(InputEvent event, float x, float y) 
		    {
		    	KitchenMonster temp = (KitchenMonster)monster_;
		        if(parent_.getNusselts_stats_().getHunger() <= 95)
		        {
		        	
		        	if(ReuGame.isSoundEnabled())
					{
		        		if(eat_sound_.isPlaying())
				        	  eat_sound_.stop();
			    
			    		eat_sound_.play();
					}
		        	else
		        	 eat_sound_.pause();
		        
		        	parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() + 5);
		        	parent_.getNusselts_stats_().setWeight(parent_.getNusselts_stats_().getWeight() + 0.01f);
		        	
		        	parent_.getNusselts_stats_().setDirtness(parent_.getNusselts_stats_().getDirtness() - 6);
		        	if((parent_.getNusselts_stats_().getDirtness() < 0))
		        		  parent_.getNusselts_stats_().setDirtness(0);
		        	  
		        	parent_.getNusselts_stats_().setTiredness(parent_.getNusselts_stats_().getTiredness() - 1);
		        	if((parent_.getNusselts_stats_().getTiredness() < 0))
		        		parent_.getNusselts_stats_().setTiredness(0);
		        	
		        	temp.eatSomething();
		        	createStackTable();
		        	buildTable();
		        }
		        else
		        {
		        	if(ReuGame.isSoundEnabled())
		        	{
		        		if(nono_sound_.isPlaying())
				        	  nono_sound_.stop();
			    
			    		nono_sound_.play();
        				        	}
		        	else
		        	 nono_sound_.pause();
 
		        	temp.pleaseNoMoreFood();
		        }
		    };
		});
		
		pizza_button_.addListener( new ClickListener() 
		{             
			@Override
		    public void clicked(InputEvent event, float x, float y) 
		    {
		    	KitchenMonster temp = (KitchenMonster)monster_;
		        if(parent_.getNusselts_stats_().getHunger() <= 80)
		        {
		        	
		        	if(ReuGame.isSoundEnabled())
					{
		        		if(eat_sound_.isPlaying())
				        	  eat_sound_.stop();
			    
			    		eat_sound_.play();
					}
		        	else
		        	 eat_sound_.pause();
		        	
		        	parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() + 20);
		        	parent_.getNusselts_stats_().setWeight(parent_.getNusselts_stats_().getWeight() + 0.2f);
		        	
		        	parent_.getNusselts_stats_().setDirtness(parent_.getNusselts_stats_().getDirtness() - 10);
		        	if((parent_.getNusselts_stats_().getDirtness() < 0))
		        		  parent_.getNusselts_stats_().setDirtness(0);
		        	  
		        	parent_.getNusselts_stats_().setTiredness(parent_.getNusselts_stats_().getTiredness() - 1);
		        	if((parent_.getNusselts_stats_().getTiredness() < 0))
		        		parent_.getNusselts_stats_().setTiredness(0);
		        	
		        	temp.eatSomething();
		        	createStackTable();
		        	buildTable();
		        }
		        else
		        {
		        	
		          	if(ReuGame.isSoundEnabled())
		        	{
		        		if(nono_sound_.isPlaying())
				        	  nono_sound_.stop();
			    
			    		nono_sound_.play();
		        	}
		        	else
		        	 nono_sound_.pause();
		        	temp.pleaseNoMoreFood();
		        }
		    };
		});
	}
	
	@Override
	public void ResetRoom()
	{
		monster_.Reset();
		createStackTable();
		buildTable();
		CorrectSoundButton();
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
