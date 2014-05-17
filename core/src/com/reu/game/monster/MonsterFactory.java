package com.reu.game.monster;

import com.reu.game.monster.Monster;
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
			throw new UnsupportedOperationException();
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
}