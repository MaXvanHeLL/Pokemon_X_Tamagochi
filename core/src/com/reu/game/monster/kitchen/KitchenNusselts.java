package com.reu.game.monster.kitchen;


public class KitchenNusselts extends KitchenMonster
{
	public  KitchenNusselts()
	{
		super();
		LoadMonsterTexture("nusselts_still.png");
		LoadEatAnimation(6, 6, "eating.png", 1);
		LoadIdleAnimation(6, 6, "idle_1.png", 1);
		LoadIdleAnimation(6, 6, "idle_2.png", 1);
		LoadNoooAnimation(6, 6, "no.png", 1);
	}
}
