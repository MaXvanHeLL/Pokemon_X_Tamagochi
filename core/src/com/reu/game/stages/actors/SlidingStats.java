package com.reu.game.stages.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.tablelayout.Cell;
import com.reu.game.utils.Utils;

public class SlidingStats extends Table{
	@SuppressWarnings("rawtypes")
	private Cell			menue_;
	private Table 			window_;
	private Table 			ribbon_;
	private Table 			hunger_;
	private Table 			tiredness_;
	private Table 			happiness_;
	private Table			dirtness_;
	private Skin			skin_;
	
	private boolean			opened_;

	public SlidingStats(Skin skin)
	{
		opened_ = false;
		skin_ = skin;
		
		// Init Table
		align(Align.bottom | Align.center);
		setFillParent(true);
		add().width(Utils.GetPixelX(11.0625f)).pad(0);
		
		
		// Adjust the top of the ribbon
		ribbon_ = new Table();
		ribbon_.align(Align.bottom | Align.center);
		ribbon_.setBackground(skin.getDrawable("RibbonTop"));
		ribbon_.add().width(Utils.GetPixelX(67.875f)).height(Utils.GetPixelY(10.0f)).colspan(5).pad(0);
		ribbon_.row().pad(0);

		// Add hungry stat view
		hunger_ = new Table();
		hunger_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		hunger_.setBackground(skin_.getDrawable("red"));
		hunger_.add(new Image(skin_.getDrawable("IconHungry"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		ribbon_.add(hunger_).padLeft(Utils.GetPixelX(4.0f)).padRight(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelY(1.0f));;
		
		happiness_ = new Table();
		happiness_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		happiness_.setBackground(skin_.getDrawable("green"));
		happiness_.add(new Image(skin_.getDrawable("IconHappy"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		ribbon_.add(happiness_).padLeft(Utils.GetPixelX(1.0f)).padRight(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelY(1.0f));;
		
		TextButton button = new TextButton("Click me!", skin_);
		
		button.addListener( new ClickListener() {             
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	if(!opened_)
		    	{
		        	menue_.height(Utils.GetPixelY(80.0f));
		        	window_.setHeight(Utils.GetPixelY(80.0f));
		        	setHeight(getHeight()+Utils.GetPixelY(80.0f));
		        	opened_ = true;
		    	}
		    	else
		    	{
		    		menue_.height(Utils.GetPixelY(0.0f));
		    		window_.setHeight(Utils.GetPixelY(0.0f));
		        	setHeight(Utils.GetPixelY(8.0f));
		        	opened_ = false;
		    	}
		    };
		});
		
		ribbon_.add(button.padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f)).padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f)));
		
		tiredness_ = new Table();
		tiredness_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		tiredness_.setBackground(skin_.getDrawable("red"));
		tiredness_.add(new Image(skin_.getDrawable("IconTired"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		ribbon_.add(tiredness_).padLeft(Utils.GetPixelX(1.0f)).padRight(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelY(1.0f));;
		
		dirtness_ = new Table();
		dirtness_.padTop(Utils.GetPixelX(0.0f)).padBottom(Utils.GetPixelX(0.0f));
		dirtness_.setBackground(skin_.getDrawable("green"));
		dirtness_.add(new Image(skin_.getDrawable("IconDirty"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		ribbon_.add(dirtness_).padLeft(Utils.GetPixelX(1.0f)).padRight(Utils.GetPixelX(4.0f)).padBottom(Utils.GetPixelY(1.0f));
		
		add(ribbon_);
		
		add().width(Utils.GetPixelX(11.0625f)).pad(0);
		
		row();
		
		add().width(Utils.GetPixelX(11.0625f)).pad(0);
		
		window_ = new Table();
		window_.align(Align.bottom | Align.center);
		window_.background(skin_.getDrawable("white"));
		window_.setHeight(0);
		
		menue_ = window_.add().width(Utils.GetPixelX(67.875f)).height(Utils.GetPixelY(0.0f));
		
		add(window_);
		
		add().width(Utils.GetPixelX(11.0625f)).pad(0);
	}
}
