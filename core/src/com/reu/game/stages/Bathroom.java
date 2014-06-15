package com.reu.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.reu.game.monster.bathroom.BathroomMonster;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class Bathroom extends ReuGameStage{
	
	public static RoomType type_ = RoomType.BATHROOM;
	
	private Table table_;
	private Table stack_table_;
	private Stack bar_stack_;
	private BathroomMonster monster_;
	private float feeding_started_;

	public Bathroom(ReuGame parent) 
	{
		super(parent);
		this.parent_ = parent;
		// Create a table that fills the screen. Everything else will go inside.
	    table_ = new Table();
		table_.setBackground(parent.getSkin().getDrawable("Bathroom"));
		table_.setFillParent(true);
		table_.align(Align.top | Align.center);
		
		createStackTable();
		
		buildTable();
		
		addActor(table_);
		
		// Create monster
		monster_ = (BathroomMonster)MonsterFactory.CreateMonster(type_, parent.getMonsterType());
		addActor(monster_);
		
		// Add a input listener
		addListener(new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{				
				if(monster_.isClicked(x, y))
				{
					monster_.takeBath();
					feeding_started_ = ReuGame.getSystemTime();
					System.out.println("Is klicked");
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
		bar_table.add(new Image(parent_.getSkin().getDrawable("green"))).width(Utils.GetPixelX(60 * parent_.getNusselts_stats_().getDirtness() / 100.f)).height(Utils.GetPixelY(5f)).left();
		bar_table.add().width(Utils.GetPixelX(60 * (1 - parent_.getNusselts_stats_().getDirtness() / 100.f)));
		bar_stack_.add(bar_table);
		bar_stack_.add(new Image(parent_.getSkin().getDrawable("BarFrame")));
		
		
		stack_table_.add(bar_stack_).width(Utils.GetPixelX(60)).height(Utils.GetPixelY(5)); // Will be stack
		
		stack_table_.add().width(Utils.GetPixelX(15));
		
		
		//-------------------------------------------------------------------------------------------------------------------------
		// Test button which resets dirtness to 0
				
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
				parent_.getNusselts_stats_().setDirtness(0);
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
			monster_.stopBath();
			this.parent_.SetCurrentStage(RoomType.MAINROOM);
			return true;
	    }
	    return false;
	}
	
	@Override
	public void PostAct(){
		if(monster_.isBathing())
		{
			if(parent_.getNusselts_stats_().getDirtness() < 100)
			{
				System.out.println("Test");
				if((feeding_started_ + 0.1) < ReuGame.getSystemTime())
				{
					feeding_started_ = ReuGame.getSystemTime();
					parent_.getNusselts_stats_().setDirtness(parent_.getNusselts_stats_().getDirtness() + 2);
					if(!(parent_.getNusselts_stats_().getTiredness() <= 0))
			          parent_.getNusselts_stats_().setTiredness(parent_.getNusselts_stats_().getTiredness() - 1);
					if(parent_.getNusselts_stats_().getDirtness() > 100)
					{
						parent_.getNusselts_stats_().setDirtness(100);
					}
					createStackTable();
					buildTable();
				}
			}
			else
			{
				monster_.stopBath();
				createStackTable();
				buildTable();
			}
		}
	}
}
