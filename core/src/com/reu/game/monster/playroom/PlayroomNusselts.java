package com.reu.game.monster.playroom;

import com.reu.game.ReuGame;

public class PlayroomNusselts extends PlayroomMonster{
	public  PlayroomNusselts()
	{
		super();
		LoadMonsterTexture("nusselts_still.png");
		AddPlayAnimations(ReuGame.getAnimation("WalkAnimation"));
		AddPlayAnimations(ReuGame.getAnimation("EatAnimation"));
		AddIdleAnimations(ReuGame.getAnimation("Idle0"));
		AddIdleAnimations(ReuGame.getAnimation("Idle1"));
		AddNoooAnimation(ReuGame.getAnimation("Nonono"));
	}

}
