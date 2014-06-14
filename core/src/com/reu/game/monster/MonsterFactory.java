package com.reu.game.monster;

import com.reu.game.monster.Monster;
import com.reu.game.monster.bathroom.BathroomNusselts;
import com.reu.game.monster.kitchen.KitchenNusselts;
import com.reu.game.monster.mainroom.MainroomNusselts;
import com.reu.game.types.MonsterType;
import com.reu.game.types.RoomType;

/***
 * This factory takes a monster and a room type and creates the appropriate
 * monster! Great stuff!
 */
public class MonsterFactory {

	public static Monster CreateMonster(RoomType room, MonsterType monster)
	{
		switch(room)
		{
		case MAINROOM:
			return CreateMainroomMonster(monster);
		case KITCHEN:
			return CreateKitchenMonster(monster);
		case BATHROOM:
			return CreateBathroomMonster(monster);
		default:
			throw new UnsupportedOperationException();
		}
	}
	
	private static Monster CreateMainroomMonster(MonsterType monster)
	{
		switch(monster)
		{
		case NUSSELTS:
			return new MainroomNusselts();
		default:
			throw new UnsupportedOperationException();
		}
	}
	private static Monster CreateKitchenMonster(MonsterType monster)
	{
		switch(monster)
		{
		case NUSSELTS:
			return new KitchenNusselts();
		default:
			throw new UnsupportedOperationException();
		}
	}
	private static Monster CreateBathroomMonster(MonsterType monster)
	{
		switch(monster)
		{
		case NUSSELTS:
			return new BathroomNusselts();
		default:
			throw new UnsupportedOperationException();
		}
	}
}
