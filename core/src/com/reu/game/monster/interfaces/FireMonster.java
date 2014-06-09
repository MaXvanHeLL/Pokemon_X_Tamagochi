package com.reu.game.monster.interfaces;

public interface FireMonster {
	
	public static final short fire_weakness_ = 0;
	public static final short water_weakness = 10;
	public static final short electro_weakness = 5;

	abstract void FireAttack1();
	abstract void FireAttack2();
	abstract void FireAttack3();
	abstract void FireAttack4();

}
