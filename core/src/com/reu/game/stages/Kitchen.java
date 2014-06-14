package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	ReuGame parent_;
	public static RoomType type_ = RoomType.KITCHEN;
	private Table table_;

	private ImageButton apple_button_;
	private ImageButton carrot_button_;
	private ImageButton pizza_button_;
	
	private Monster monster_;
	
	
	public Kitchen(ReuGame parent){
		super(parent);
		this.parent_ = parent;
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("Kitchen"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.center);
		
		createButtons();
		
		Table stackTable = new Table();
		stackTable.align(Align.top | Align.center);
		
		
		stackTable.add().minHeight(Utils.GetPixelY(10)).width(Utils.GetPixelX(90)).colspan(3);
		stackTable.row();
		stackTable.add().width(Utils.GetPixelX(15));
		
		
		Stack bar_stack = new Stack();
		bar_stack.setWidth(Utils.GetPixelX(60f));
		bar_stack.setHeight(Utils.GetPixelY(5f));
		Table dirty_table = new Table();
		dirty_table.setFillParent(true);
		dirty_table.add(new Image(Utils.getAttentionColor(parent_.getNusselts_stats_().getHunger()).getDrawable("color"))).width(Utils.GetPixelX(60 * parent_.getNusselts_stats_().getHunger() / 100.f)).height(Utils.GetPixelY(5f)).left();
		dirty_table.add().width(Utils.GetPixelX(60 * (1 - parent_.getNusselts_stats_().getHunger() / 100.f)));
		bar_stack.add(dirty_table);
		bar_stack.add(new Image(parent_.getSkin().getDrawable("BarFrame")));
		
		
		stackTable.add(bar_stack).width(Utils.GetPixelX(60)).height(Utils.GetPixelY(5)); // Will be stack
		
		stackTable.add().width(Utils.GetPixelX(15));
		
		table_.add(stackTable).height(Utils.GetPixelY(135)).width(Utils.GetPixelX(90)).colspan(3);
		table_.row();
		/*//-------------------------------------------------------------------------------------------------------------------------
		// Test button which resets hunger to 0
				
				
		ImageButtonStyle test_style = new ImageButtonStyle();
		test_style.up = parent_.getSkin().getDrawable("kitchen_button_up");
		test_style.down = parent_.getSkin().getDrawable("kitchen_button_down");
		ImageButton test_button = new ImageButton(test_style);
				
		test_button.addListener( new ClickListener() 
		{             
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{
				parent_.getNusselts_stats_().setHunger(0);
			};
		});
				
		table_.add(test_button).bottom().left().width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padLeft(Utils.GetPixelX(5)).padTop(Utils.GetPixelY(5));
				
		table_.row().height(Utils.GetPixelY(100));
		//-------------------------------------------------------------------------------------------------------------------------*/
		
		
		table_.add(apple_button_).width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padLeft(Utils.GetPixelX(10)).padRight(Utils.GetPixelX(5)).padBottom(Utils.GetPixelY(5));
		table_.add(carrot_button_).width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padLeft(Utils.GetPixelX(5)).padRight(Utils.GetPixelX(5)).padBottom(Utils.GetPixelY(5));
		table_.add(pizza_button_).width(Utils.GetPixelX(20)).height(Utils.GetPixelY(20)).padLeft(Utils.GetPixelX(5)).padRight(Utils.GetPixelX(10)).padBottom(Utils.GetPixelY(5));
		
		addActor(table_);
		
		// Create monster
		monster_ = MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// -- important for catching the Back Button to avoid program Exit !!
		Gdx.input.setCatchBackKey(true);			
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
		        	System.out.println(parent_.getNusselts_stats_().getHunger());
		        	parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() + 10);
		        	parent_.getNusselts_stats_().setWeight(parent_.getNusselts_stats_().getWeight() + 0.01f);
		        	temp.eatSomething();
		        }
		        else
		        {
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
		        	System.out.println(parent_.getNusselts_stats_().getHunger());
		        	parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() + 5);
		        	temp.eatSomething();
		        }
		        else
		        {
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
		        	System.out.println(parent_.getNusselts_stats_().getHunger());
		        	parent_.getNusselts_stats_().setHunger(parent_.getNusselts_stats_().getHunger() + 20);
		        	parent_.getNusselts_stats_().setWeight(parent_.getNusselts_stats_().getWeight() + 0.1f);
		        	temp.eatSomething();
		        }
		        else
		        {
		        	temp.pleaseNoMoreFood();
		        }
		    };
		});
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
