package com.reu.game.monster.bathroom;

import com.reu.game.ReuGame;


public class BathroomNusselts extends BathroomMonster{
	public BathroomNusselts()
	{
		super();
		LoadMonsterTexture("bath_stand.png");
		
		//TODO: Load propper animations!
<<<<<<< HEAD
		AddBathAnimation(ReuGame.getAnimation("Bathing"));
		AddIdleAnimations(ReuGame.getAnimation("Bath_Idle0"));
		AddIdleAnimations(ReuGame.getAnimation("Bath_Idle1"));

=======
		AddBathAnimation(ReuGame.getAnimation("EatAnimation"));
		AddIdleAnimations(ReuGame.getAnimation("Idle0"));
		AddIdleAnimations(ReuGame.getAnimation("Idle1"));
>>>>>>> dcb4a02e18f5a213dfd24550be6e395d2d6486a1
	}
}
