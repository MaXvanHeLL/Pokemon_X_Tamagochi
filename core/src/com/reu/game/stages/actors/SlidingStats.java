package com.reu.game.stages.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reu.game.ReuGame;
import com.reu.game.monster.Stats;
import com.reu.game.utils.Utils;

public class SlidingStats extends Table{
	
	private static float	update_intervall_ = 2;
	
	private Table			stats_;
	private Table 			window_;
	private Table 			ribbon_;
	private Table 			hunger_;
	private Table 			tiredness_;
	private Table 			happiness_;
	private Table			dirtness_;
	private Skin			skin_;
	private Label			name_data_;
	private Label			weight_data_;
	private Label			age_data_;
	private LabelStyle 		label_style_;
	private Stack			hunger_stack_;
	private Stack			happy_stack_;
	private Stack			dirty_stack_;
	private Stack			tired_stack_;

	
	private Stats			monster_stats_;
	private Stats			temp_save_;
	
	private float			next_update_time_;
	
	private boolean			opened_;

	public SlidingStats(Skin skin, Stats stats)
	{
		opened_ = false;
		skin_ = skin;
		next_update_time_ = ReuGame.getSystemTime() + update_intervall_;
		
		monster_stats_ = stats;
		temp_save_ = new Stats(monster_stats_);
		
		// Init Table
		align(Align.bottom | Align.center);
		setFillParent(true);
		
		// Build parts
		buildRibbon();
		buildWindow();
						
		// Create Table
		buildTable(opened_);
	}
	
	private void buildRibbon()
	{
		// Adjust the top of the ribbon
		ribbon_ = new Table();
		ribbon_.align(Align.top | Align.center);
		ribbon_.setBackground(skin_.getDrawable("RibbonTop"));
		
		buildRibbonHungryStats();
		ribbon_.add(hunger_).padLeft(Utils.GetPixelX(4.0f)).padRight(Utils.GetPixelX(1.0f)).expandY().bottom().padBottom(Utils.GetPixelY(0.7f));
		buildRibbonHappyStats();
		ribbon_.add(happiness_).padLeft(Utils.GetPixelX(1.0f)).padRight(Utils.GetPixelX(1.0f)).expandY().bottom().padBottom(Utils.GetPixelY(0.7f));;
			
		// Add button
		ImageButtonStyle button_style = new ImageButtonStyle();
		button_style.up = skin_.getDrawable("ButtonUp");
		button_style.down = skin_.getDrawable("ButtonDown");
				
		ImageButton button = new ImageButton(button_style);
				
		button.addListener( new ClickListener() {             
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if(!opened_)
			{
				buildTable(true);
				opened_ = true;
			}
			else
			{
				buildTable(false);
				opened_ = false;
			}};});
				
		ribbon_.add(button.padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f))).expandY().top().padTop(Utils.GetPixelY(1.2f));;
		
		buildRibbonTiredStats();
		ribbon_.add(tiredness_).padLeft(Utils.GetPixelX(1.0f)).padRight(Utils.GetPixelX(1.0f)).expandY().bottom().padBottom(Utils.GetPixelY(0.7f));;
		buildRibbonDirtyStats();
		ribbon_.add(dirtness_).padLeft(Utils.GetPixelX(1.0f)).padRight(Utils.GetPixelX(4.0f)).expandY().bottom().padBottom(Utils.GetPixelY(0.7f));;
		

	}
	
	private void buildRibbonHungryStats()
	{
		// Add hungry stat view
		hunger_ = new Table();
		hunger_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		hunger_.setBackground(getAttentionColor(monster_stats_.getHunger()).getDrawable("color"));
		hunger_.add(new Image(skin_.getDrawable("IconHungry"))).width(Utils.GetPixelX(10.0f)).height(Utils.GetPixelY(10.0f));
	}
	
	private void buildRibbonHappyStats()
	{
		// Add happiness stat view
		happiness_ = new Table();
		happiness_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		happiness_.setBackground(getAttentionColor(monster_stats_.getHappiness()).getDrawable("color"));
		happiness_.add(new Image(skin_.getDrawable("IconHappy"))).width(Utils.GetPixelX(10.0f)).height(Utils.GetPixelY(10.0f));
	}
	
	private void buildRibbonDirtyStats()
	{
		// Add dirtyness stat view
		dirtness_ = new Table();
		dirtness_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		dirtness_.setBackground(getAttentionColor(monster_stats_.getDirtness()).getDrawable("color"));
		dirtness_.add(new Image(skin_.getDrawable("IconDirty"))).width(Utils.GetPixelX(10.0f)).height(Utils.GetPixelY(10.0f));
	}
	
	private void buildRibbonTiredStats()
	{
		// Add tiredness stat view
		tiredness_ = new Table();
		tiredness_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		tiredness_.setBackground(getAttentionColor(monster_stats_.getTiredness()).getDrawable("color"));
		tiredness_.add(new Image(skin_.getDrawable("IconTired"))).width(Utils.GetPixelX(10.0f)).height(Utils.GetPixelY(10.0f));

	}

	
	private void buildWindow()
	{
		window_ = new Table();
		window_.align(Align.bottom | Align.center);
		window_.background(skin_.getDrawable("RibbonRepeat"));
		window_.setHeight(0);

		window_.add(new Image(skin_.getDrawable("Portrait"))).width(Utils.GetPixelX(26.9375f)).height(Utils.GetPixelY(26.9375f)).padLeft(Utils.GetPixelX(6.0f)).padRight(Utils.GetPixelX(0.0f)).padTop(Utils.GetPixelY(1.0f)).colspan(2);
		stats_ = new Table();
		stats_.align(Align.bottom | Align.center);
		window_.add(stats_).width(Utils.GetPixelX(33.9375f));
		
		BitmapFont font = new BitmapFont();
		font.scale(0.8f);
		label_style_ = new LabelStyle(font, Color.BLACK);
		
		updateDataLabels();
		
		Label name = new Label("Name:", label_style_);
		stats_.add(name).left();
		stats_.add(name_data_).left();
		stats_.row();
		Label weight = new Label("Weight:", label_style_);
		stats_.add(weight).left();
		stats_.add(weight_data_).left();
		stats_.row();
		Label age = new Label("Age:", label_style_);
		stats_.add(age).left();
		stats_.add(age_data_).left();
		stats_.row();
		
		window_.row().padTop(Utils.GetPixelY(4f));
		
		// Add the labels and bars to the window
		Label hunger = new Label("Hunger:", label_style_);
		window_.add(hunger);
		buildHungerStack();
		window_.add(hunger_stack_).colspan(2).width(Utils.GetPixelX(37f)).left().height(Utils.GetPixelY(4f));
		window_.row().padTop(Utils.GetPixelY(2f));
		Label happy = new Label("Happy:", label_style_);
		window_.add(happy);
		buildHappyStack();
		window_.add(happy_stack_).colspan(2).width(Utils.GetPixelX(37f)).left().height(Utils.GetPixelY(4f));
		window_.row().padTop(Utils.GetPixelY(2f));
		Label tired = new Label("Tired:", label_style_);
		window_.add(tired);
		buildTiredStack();
		window_.add(tired_stack_).colspan(2).width(Utils.GetPixelX(37f)).left().height(Utils.GetPixelY(4f));
		window_.row().padTop(Utils.GetPixelY(2f)).padBottom(Utils.GetPixelY(4f));
		Label dirty = new Label("Dirty:", label_style_);
		window_.add(dirty);
		buildDirtyStack();
		window_.add(dirty_stack_).colspan(2).width(Utils.GetPixelX(37f)).left().height(Utils.GetPixelY(4f));
		
	}
	
	private void updateDataLabels()
	{
		// TODO: Get values from "stats"-class
		name_data_ = new Label("NUSSELTS", label_style_);
		weight_data_ = new Label("14 kg", label_style_);
		age_data_ = new Label("6 days", label_style_);
	}
	
	private void buildHungerStack()
	{
		hunger_stack_ = new Stack();
		hunger_stack_.setWidth(Utils.GetPixelX(37f));
		hunger_stack_.setHeight(Utils.GetPixelY(4f));
		Table hunger_table = new Table();
		hunger_table.setFillParent(true);
		hunger_table.add(new Image(getAttentionColor(monster_stats_.getHunger()).getDrawable("color"))).width(Utils.GetPixelX(37 * monster_stats_.getHunger() / 100.f)).height(Utils.GetPixelY(4f)).left();
		hunger_table.add().width(Utils.GetPixelX(37 * (1 - monster_stats_.getHunger() / 100.f)));
		hunger_stack_.add(hunger_table);
		hunger_stack_.add(new Image(skin_.getDrawable("BarFrame")));
	}
	
	private void buildHappyStack()
	{
		happy_stack_ = new Stack();
		happy_stack_.setWidth(Utils.GetPixelX(37f));
		happy_stack_.setHeight(Utils.GetPixelY(4f));
		Table happy_table = new Table();
		happy_table.setFillParent(true);
		happy_table.add(new Image(getAttentionColor(monster_stats_.getHappiness()).getDrawable("color"))).width(Utils.GetPixelX(37 * monster_stats_.getHappiness() / 100.f)).height(Utils.GetPixelY(4f)).left();
		happy_table.add().width(Utils.GetPixelX(37 * (1 - monster_stats_.getHappiness() / 100.f)));
		happy_stack_.add(happy_table);
		happy_stack_.add(new Image(skin_.getDrawable("BarFrame")));
	}
	
	private void buildTiredStack()
	{
		tired_stack_ = new Stack();
		tired_stack_.setWidth(Utils.GetPixelX(37f));
		tired_stack_.setHeight(Utils.GetPixelY(4f));
		Table tired_table = new Table();
		tired_table.setFillParent(true);
		tired_table.add(new Image(getAttentionColor(monster_stats_.getTiredness()).getDrawable("color"))).width(Utils.GetPixelX(37 * monster_stats_.getTiredness() / 100.f)).height(Utils.GetPixelY(4f)).left();
		tired_table.add().width(Utils.GetPixelX(37 * (1 - monster_stats_.getTiredness() / 100.f)));
		tired_stack_.add(tired_table);
		tired_stack_.add(new Image(skin_.getDrawable("BarFrame")));
	}
	
	private void buildDirtyStack()
	{
		dirty_stack_ = new Stack();
		dirty_stack_.setWidth(Utils.GetPixelX(37f));
		dirty_stack_.setHeight(Utils.GetPixelY(4f));
		Table dirty_table = new Table();
		dirty_table.setFillParent(true);
		dirty_table.add(new Image(getAttentionColor(monster_stats_.getDirtness()).getDrawable("color"))).width(Utils.GetPixelX(37 * monster_stats_.getDirtness() / 100.f)).height(Utils.GetPixelY(4f)).left();
		dirty_table.add().width(Utils.GetPixelX(37 * (1 - monster_stats_.getDirtness() / 100.f)));
		dirty_stack_.add(dirty_table);
		dirty_stack_.add(new Image(skin_.getDrawable("BarFrame")));
	}
	
	private void buildTable(boolean with_window)
	{
		clearChildren();
		add().width(Utils.GetPixelX(11.0625f)).pad(0);
		add(ribbon_);
		add().width(Utils.GetPixelX(11.0625f)).pad(0);		
		row();
		if(with_window)
		{
			add().width(Utils.GetPixelX(11.0625f)).pad(0);
			add(window_);
			add().width(Utils.GetPixelX(11.0625f)).pad(0);
		}
	}
	
	private Skin getAttentionColor(float value)
	{
		Skin to_ret = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.rgba8888(1 - (value / 100.0f), (value / 100.0f), 0, 1));
		pixmap.fill();
		to_ret.add("color", new Texture(pixmap));
		return to_ret;
	}
	
	public void updateStats(Stats stats)
	{
		// This function needs a lot of cpu power, so we only use it
		// everey X seconds
		if(ReuGame.getSystemTime() > next_update_time_)
		{
			// And we only perform the new UI if the stats changed
			if(!temp_save_.equals(monster_stats_))
			{
				monster_stats_ = stats;
				buildRibbon();
				buildWindow();
				buildTable(opened_);
				temp_save_ = new Stats(monster_stats_);
				next_update_time_ += update_intervall_;
			}
		}
	}
}
