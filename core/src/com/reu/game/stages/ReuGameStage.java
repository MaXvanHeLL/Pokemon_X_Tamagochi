package com.reu.game.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.reu.game.ReuGame;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

/***
 * We can add code here if we need some shared members over all stages. (f.e.
 * global Monster information)
 */
public abstract class ReuGameStage extends Stage{
	public static RoomType type_;
	
	protected ImageButton	sound_button_;
	
	protected ReuGame parent_;		// The used skin for this stage
	/***
	 * Creates a new game stage!
	 * @param skin The skin to use.
	 */
	public ReuGameStage(ReuGame parent){
		super();
		parent_ = parent;
		
		sound_button_ = new ImageButton(parent_.getSkin().get("sound_on", ImageButtonStyle.class));
		sound_button_.align(Align.top | Align.left);
		sound_button_.setPosition(Utils.GetPixelX(77), Utils.GetPixelY(70));
		sound_button_.setWidth(Utils.GetPixelX(10));
		sound_button_.setHeight(Utils.GetPixelX(10));
		
		sound_button_.setStyle(parent_.getSkin().get("sound_off", ImageButtonStyle.class));
		
		addActor(sound_button_);
	}
	
	/***
	 * Override this function to check stuff on each render cycle
	 */
	public void PostAct(){
	}
	
	/***
	 * Resets the room before leaving (or maybe entering?)
	 */
	public void ResetRoom(){
	}
}
