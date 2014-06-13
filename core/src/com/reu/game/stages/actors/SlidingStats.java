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
	private Cell			menue_;
	private Table 			table_;
	private Skin			skin_;
	
	private boolean			opened_;

	public SlidingStats(Skin skin)
	{
		opened_ = false;
		skin_ = skin;
		
		// Init Table
		align(Align.bottom | Align.center);
		setFillParent(true);
		
		Table hunger = new Table();
		hunger.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		hunger.setBackground(skin_.getDrawable("red"));
		hunger.add(new Image(skin_.getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		add(hunger).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		Table happyness = new Table();
		happyness.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		happyness.setBackground(skin_.getDrawable("green"));
		happyness.add(new Image(skin_.getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		add(happyness).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		TextButton button = new TextButton("Click me!", skin_);
		
		button.addListener( new ClickListener() {             
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	if(!opened_)
		    	{
		        	menue_.height(Utils.GetPixelY(80.0f));
		        	table_.setHeight(Utils.GetPixelY(80.0f));
		        	setHeight(getHeight()+Utils.GetPixelY(80.0f));
		        	opened_ = true;
		    	}
		    	else
		    	{
		    		menue_.height(Utils.GetPixelY(0.0f));
		        	table_.setHeight(Utils.GetPixelY(0.0f));
		        	setHeight(Utils.GetPixelY(10.0f));
		        	opened_ = false;
		    	}
		    };
		});
		
		add(button.padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f)).padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f)));
		
		Table tiredness = new Table();
		tiredness.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		tiredness.setBackground(skin_.getDrawable("red"));
		tiredness.add(new Image(skin_.getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		add(tiredness).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		Table dirtyness = new Table();
		dirtyness.padTop(Utils.GetPixelX(1.0f)).padBottom(Utils.GetPixelX(1.0f));
		dirtyness.setBackground(skin_.getDrawable("green"));
		dirtyness.add(new Image(skin_.getDrawable("CircleMask"))).width(Utils.GetPixelX(8.0f)).height(Utils.GetPixelY(8.0f));
		add(dirtyness).padLeft(Utils.GetPixelX(2.0f)).padRight(Utils.GetPixelX(2.0f));
		
		row();
		
		table_ = new Table();
		table_.align(Align.bottom | Align.center);
		table_.background(skin_.getDrawable("white"));
		
		menue_ = table_.add().width(Utils.GetPixelX(90.0f)).height(Utils.GetPixelY(0.0f));
		
		add(table_).colspan(5);
	}
}
