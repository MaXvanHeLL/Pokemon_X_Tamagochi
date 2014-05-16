package com.reu.game.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.reu.game.ReuGame;
import com.reu.game.types.RoomType;

/***
 * We can add code here if we need some shared members over all stages. (f.e.
 * global Monster information)
 */
public abstract class ReuGameStage extends Stage{
	public static RoomType type_;
	protected ReuGame parent_;		// The used skin for this stage
	/***
	 * Creates a new game stage!
	 * @param skin The skin to use.
	 */
	public ReuGameStage(ReuGame parent){
		super();
		parent_ = parent;
	}
}
