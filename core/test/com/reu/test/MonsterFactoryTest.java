package com.reu.test;

import junit.framework.TestCase;

import com.reu.game.monster.MonsterFactory;
import com.reu.game.monster.bathroom.BathroomNusselts;
import com.reu.game.monster.kitchen.KitchenNusselts;
import com.reu.game.monster.mainroom.MainroomNusselts;
import com.reu.game.monster.playroom.PlayroomNusselts;
import com.reu.game.types.MonsterType;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

public class MonsterFactoryTest extends TestCase {

	MonsterFactory test_factory_;
	
	public MonsterFactoryTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Utils.test_mode_ = true;
		test_factory_ = new MonsterFactory();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateMonster() {
		assertTrue(MonsterFactory.CreateMonster(RoomType.MAINROOM, MonsterType.NUSSELTS) instanceof MainroomNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.MAINROOM, MonsterType.NUSSELTS) instanceof KitchenNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.MAINROOM, MonsterType.NUSSELTS) instanceof BathroomNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.MAINROOM, MonsterType.NUSSELTS) instanceof PlayroomNusselts);
		
		assertFalse(MonsterFactory.CreateMonster(RoomType.KITCHEN, MonsterType.NUSSELTS) instanceof MainroomNusselts);
		assertTrue(MonsterFactory.CreateMonster(RoomType.KITCHEN, MonsterType.NUSSELTS) instanceof KitchenNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.KITCHEN, MonsterType.NUSSELTS) instanceof BathroomNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.KITCHEN, MonsterType.NUSSELTS) instanceof PlayroomNusselts);
		
		assertFalse(MonsterFactory.CreateMonster(RoomType.BATHROOM, MonsterType.NUSSELTS) instanceof MainroomNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.BATHROOM, MonsterType.NUSSELTS) instanceof KitchenNusselts);
		assertTrue(MonsterFactory.CreateMonster(RoomType.BATHROOM, MonsterType.NUSSELTS) instanceof BathroomNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.BATHROOM, MonsterType.NUSSELTS) instanceof PlayroomNusselts);
		
		assertFalse(MonsterFactory.CreateMonster(RoomType.PLAYROOM, MonsterType.NUSSELTS) instanceof MainroomNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.PLAYROOM, MonsterType.NUSSELTS) instanceof KitchenNusselts);
		assertFalse(MonsterFactory.CreateMonster(RoomType.PLAYROOM, MonsterType.NUSSELTS) instanceof BathroomNusselts);
		assertTrue(MonsterFactory.CreateMonster(RoomType.PLAYROOM, MonsterType.NUSSELTS) instanceof PlayroomNusselts);
	}

}
