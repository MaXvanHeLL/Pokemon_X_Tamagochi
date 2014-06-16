package com.reu.game.monster.bathroom;

import com.reu.game.ReuGame;


public class BathroomNusselts extends BathroomMonster{
	public BathroomNusselts()
	{
		super();
		LoadMonsterTexture("bath_stand.png");
		
		//TODO: Load propper animations!
		AddBathAnimation(ReuGame.getAnimation("BathAnimation"));
		AddIdleAnimations(ReuGame.getAnimation("BathIdle0"));
	}
}
