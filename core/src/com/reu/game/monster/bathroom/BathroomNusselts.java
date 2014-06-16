package com.reu.game.monster.bathroom;

import com.reu.game.ReuGame;
import com.reu.game.utils.Utils;


public class BathroomNusselts extends BathroomMonster{
	public BathroomNusselts()
	{
		super();
		if(!Utils.test_mode_)
			LoadMonsterTexture("bath_stand.png");
		
		//TODO: Load propper animations!
		AddBathAnimation(ReuGame.getAnimation("Bathing"));
		AddIdleAnimations(ReuGame.getAnimation("Bath_Idle0"));
		AddIdleAnimations(ReuGame.getAnimation("Bath_Idle1"));

	}
}
