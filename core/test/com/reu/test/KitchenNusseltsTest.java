package com.reu.test;

import junit.framework.TestCase;

import com.reu.game.monster.kitchen.KitchenNusselts;
import com.reu.game.utils.Utils;

public class KitchenNusseltsTest extends TestCase {

	private KitchenNusselts test_monster_;
	
	public KitchenNusseltsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Utils.test_mode_ = true;
		test_monster_ = new KitchenNusselts();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testKitchenNusselts() {
		assertNotNull(test_monster_.getIdle_animations_());
		assertEquals(test_monster_.getState_time_(), 0f);
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		assertFalse(test_monster_.isAnimated_());
		assertEquals(test_monster_.getX(), Utils.GetPixelX(20));
		assertEquals(test_monster_.getY(), Utils.GetPixelY(35));
		assertEquals(test_monster_.getWidth(), Utils.GetPixelX(50));
		assertEquals(test_monster_.getHeight(), Utils.GetPixelY(50));
		assertEquals(test_monster_.getOriginX(), Utils.GetPixelX(25));
		assertEquals(test_monster_.getOriginY(), Utils.GetPixelY(25));
	}

	public void testReset() {
		test_monster_.Reset();
		assertFalse(test_monster_.isBusy_());
		assertFalse(test_monster_.isAnimated_());
	}
	
	public void testKitchenMonster() {
		assertNotNull(test_monster_.getIdle_animations_());
		assertEquals(test_monster_.getState_time_(), 0f);
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		assertFalse(test_monster_.isAnimated_());
		assertEquals(test_monster_.getX(), Utils.GetPixelX(20));
		assertEquals(test_monster_.getY(), Utils.GetPixelY(35));
		assertEquals(test_monster_.getWidth(), Utils.GetPixelX(50));
		assertEquals(test_monster_.getHeight(), Utils.GetPixelY(50));
		assertEquals(test_monster_.getOriginX(), Utils.GetPixelX(25));
		assertEquals(test_monster_.getOriginY(), Utils.GetPixelY(25));
	}

	public void testEatSomething() {
		test_monster_.eatSomething();
		assertTrue(test_monster_.isBusy_());
		assertTrue(test_monster_.isAnimated_());
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getEat_animation_());
		assertEquals(test_monster_.getAnimation_time_(), 0.f);
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 1);
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getStop_time_());
	}

	public void testPleaseNoMoreFood() {
		test_monster_.pleaseNoMoreFood();
		assertTrue(test_monster_.isBusy_());
		assertTrue(test_monster_.isAnimated_());
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getNooo_animation_());
		assertEquals(test_monster_.getAnimation_time_(), 0.f);
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 1);
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getStop_time_());
	}

	public void testGetIdle_animations_() {
		assertNotNull(test_monster_.getIdle_animations_());
	}

	public void testGetEat_animation_() {
		test_monster_.eatSomething();
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getEat_animation_());
	}

	public void testGetNooo_animation_() {
		test_monster_.pleaseNoMoreFood();
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getNooo_animation_());
	}

	public void testGetCurrent_animation_() {
		test_monster_.eatSomething();
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getEat_animation_());
		
		test_monster_.pleaseNoMoreFood();
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getNooo_animation_());
	}

	public void testGetState_time_() {
		test_monster_.eatSomething();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 1);
		
		test_monster_.pleaseNoMoreFood();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 1);
	}

	public void testGetStop_time_() {
		assertEquals(test_monster_.getStop_time_(), 0f);
		
		test_monster_.eatSomething();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 1);
		
		test_monster_.pleaseNoMoreFood();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 1);
	}

	public void testGetBusy_time_() {
		assertEquals(test_monster_.getBusy_time_(), 0f);
		
		test_monster_.eatSomething();
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getStop_time_());
	}

	public void testGetAnimation_time_() {
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		
		test_monster_.eatSomething();
		assertEquals(test_monster_.getAnimation_time_(), 0.f);
	}

	public void testIsAnimated_() {
		assertFalse(test_monster_.isAnimated_());
		
		test_monster_.eatSomething();
		assertTrue(test_monster_.isAnimated_());
	}

	public void testIsBusy_() {
		assertFalse(test_monster_.isBusy_());
		
		test_monster_.Reset();
		assertFalse(test_monster_.isBusy_());
		
		test_monster_.eatSomething();
		assertTrue(test_monster_.isBusy_());
	}

	public void testGetR_generator_() {
		assertNotNull(test_monster_.getR_generator_());
	}

	public void testGetCenterX() {
		assertEquals(test_monster_.getX(), Utils.GetPixelX(20));
	}

	public void testGetCenterY() {
		assertEquals(test_monster_.getY(), Utils.GetPixelY(35));
	}

	public void testIsClicked() {
		assertTrue(test_monster_.isClicked(test_monster_.GetCenterX(), test_monster_.GetCenterY()));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX() + 1000, test_monster_.GetCenterY()));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX() - 1000, test_monster_.GetCenterY()));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX(), test_monster_.GetCenterY() + 1000));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX(), test_monster_.GetCenterY() - 1000));
	}

}
