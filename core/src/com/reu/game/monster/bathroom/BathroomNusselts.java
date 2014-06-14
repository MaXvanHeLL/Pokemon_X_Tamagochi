package com.reu.game.monster.bathroom;


public class BathroomNusselts extends BathroomMonster{
	public BathroomNusselts()
	{
		super();
		LoadMonsterTexture("nusselts_still.png");
		LoadBathAnimation(6, 6, "eating.png", 1);
		LoadIdleAnimation(6, 6, "idle_1.png", 1);
		LoadIdleAnimation(6, 6, "idle_2.png", 1);
	}
}
