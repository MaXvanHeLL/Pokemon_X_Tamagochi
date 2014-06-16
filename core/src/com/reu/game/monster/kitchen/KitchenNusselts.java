package com.reu.game.monster.kitchen;

import com.reu.game.ReuGame;
import com.reu.game.utils.Utils;


public class KitchenNusselts extends KitchenMonster
{
	public  KitchenNusselts()
	{
		super();
		if(!Utils.test_mode_)
			LoadMonsterTexture("nusselts_still.png");

		AddEatAnimation(ReuGame.getAnimation("EatAnimation"));
		AddIdleAnimations(ReuGame.getAnimation("Idle0"));
		AddIdleAnimations(ReuGame.getAnimation("Idle1"));
		AddNoooAnimation(ReuGame.getAnimation("Nonono"));
	}
}
