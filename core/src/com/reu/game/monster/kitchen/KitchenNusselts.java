package com.reu.game.monster.kitchen;

import com.reu.game.ReuGame;


public class KitchenNusselts extends KitchenMonster
{
	public  KitchenNusselts()
	{
		super();
		LoadMonsterTexture("nusselts_still.png");
		AddEatAnimation(ReuGame.getAnimation("EatAnimation"));
		AddIdleAnimations(ReuGame.getAnimation("Idle0"));
		AddIdleAnimations(ReuGame.getAnimation("Idle1"));
		AddNoooAnimation(ReuGame.getAnimation("Nonono"));
	}
}
