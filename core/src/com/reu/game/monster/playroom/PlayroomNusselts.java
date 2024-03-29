package com.reu.game.monster.playroom;

import com.reu.game.ReuGame;
import com.reu.game.utils.Utils;

public class PlayroomNusselts extends PlayroomMonster{
	public  PlayroomNusselts()
	{
		super();

		if(!Utils.test_mode_)
			LoadMonsterTexture("nusselts_still.png");

	    AddPlayAnimations(ReuGame.getAnimation("PlayAnimation1"));
		AddPlayAnimations(ReuGame.getAnimation("PlayAnimation2"));
		AddPlayAnimations(ReuGame.getAnimation("PlayAnimation3"));
		AddIdleAnimations(ReuGame.getAnimation("Idle0"));
		AddIdleAnimations(ReuGame.getAnimation("Idle1"));
		AddNoooAnimation(ReuGame.getAnimation("Nonono"));
	}

}
