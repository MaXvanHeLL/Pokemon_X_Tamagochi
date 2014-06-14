package com.reu.game.monster.kitchen;


public class KitchenNusselts extends KitchenMonster
{
	public  KitchenNusselts()
	{
		super();
		LoadMonsterTexture("nusselts_still.png");
		LoadEatAnimation(4, 9, "eating.png", 1);
		LoadIdleAnimation(1, 30, "sprites.png", 1);
		LoadIdleAnimation(1, 30, "eating.png", 1);
		LoadNoooAnimation(1, 30, "sprites.png", 1);
	}
}
