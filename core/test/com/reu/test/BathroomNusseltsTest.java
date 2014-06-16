package com.reu.test;

import junit.framework.TestCase;

import com.reu.game.monster.bathroom.BathroomNusselts;
import com.reu.game.utils.Utils;

public class BathroomNusseltsTest extends TestCase {

	private BathroomNusselts test_monster_;
	
	public BathroomNusseltsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Utils.test_mode_ = true;
		test_monster_ = new BathroomNusselts();		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBathroomNusselts() {
		assertNotNull(test_monster_.getIdle_animations_());
		assertEquals(test_monster_.getState_time_(), 0f);
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		assertFalse(test_monster_.isAnimated_());
		assertEquals(test_monster_.getX(), Utils.GetPixelX(15));
		assertEquals(test_monster_.getY(), Utils.GetPixelY(30));
		assertEquals(test_monster_.getWidth(), Utils.GetPixelX(60));
		assertEquals(test_monster_.getHeight(), Utils.GetPixelY(60));
		assertEquals(test_monster_.getOriginX(), Utils.GetPixelX(30));
		assertEquals(test_monster_.getOriginY(), Utils.GetPixelY(30));
	}

	public void testReset() {
		test_monster_.Reset();
		assertFalse(test_monster_.isBusy_());
		assertFalse(test_monster_.isAnimated_());
	}

	public void testBathroomMonster() {
		assertNotNull(test_monster_.getIdle_animations_());
		assertEquals(test_monster_.getState_time_(), 0f);
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		assertFalse(test_monster_.isAnimated_());
		assertEquals(test_monster_.getX(), Utils.GetPixelX(15));
		assertEquals(test_monster_.getY(), Utils.GetPixelY(30));
		assertEquals(test_monster_.getWidth(), Utils.GetPixelX(60));
		assertEquals(test_monster_.getHeight(), Utils.GetPixelY(60));
		assertEquals(test_monster_.getOriginX(), Utils.GetPixelX(30));
		assertEquals(test_monster_.getOriginY(), Utils.GetPixelY(30));
	}

	public void testTakeBath() {
		test_monster_.takeBath();
		assertTrue(test_monster_.isBathing());
		assertTrue(test_monster_.isBusy_());
		assertTrue(test_monster_.isAnimated_());
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getBath_animation_());
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 500);
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getStop_time_());
	}

	public void testStopBath() {
		test_monster_.stopBath();
		assertFalse(test_monster_.isBathing());
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_());
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getState_time_());
	}

	public void testIsBathing() {
		assertFalse(test_monster_.isBathing());
		
		test_monster_.takeBath();
		assertTrue(test_monster_.isBathing());
		
		test_monster_.stopBath();
		assertFalse(test_monster_.isBathing());
	}

	public void testGetIdle_animations_() {
		assertNotNull(test_monster_.getIdle_animations_());
	}

	public void testGetBath_animation_() {
		test_monster_.takeBath();
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getBath_animation_());
	}

	public void testGetCurrent_animation_() {
		test_monster_.takeBath();
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getBath_animation_());
	}

	public void testGetState_time_() {
		assertEquals(test_monster_.getState_time_(), 0f);
		
		test_monster_.takeBath();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 500);
		
		test_monster_.stopBath();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_());
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getState_time_());
	}

	public void testGetStop_time_() {
		assertEquals(test_monster_.getStop_time_(), 0f);
		
		test_monster_.takeBath();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 500);
		
		test_monster_.stopBath();
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_());
	}

	public void testGetBusy_time_() {
		assertEquals(test_monster_.getBusy_time_(), 0f);
		
		test_monster_.takeBath();
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getStop_time_());
		
		test_monster_.stopBath();
		assertEquals(test_monster_.getBusy_time_(), test_monster_.getState_time_());
	}

	public void testGetAnimation_time_() {
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		
		test_monster_.takeBath();
		assertEquals(test_monster_.getAnimation_time_(), 0f);
	}

	public void testIsAnimated_() {
		assertFalse(test_monster_.isAnimated_());
		
		test_monster_.takeBath();
		assertTrue(test_monster_.isAnimated_());
		
		test_monster_.Reset();
		assertFalse(test_monster_.isAnimated_());
	}

	public void testIsBusy_() {
		assertFalse(test_monster_.isBusy_());
		
		test_monster_.takeBath();
		assertTrue(test_monster_.isBusy_());
		
		test_monster_.Reset();
		assertFalse(test_monster_.isBusy_());
	}

	public void testGetR_generator_() {
		assertNotNull(test_monster_.getR_generator_());
	}

	public void testGetCenterX() {
		assertEquals(test_monster_.getX(), Utils.GetPixelX(15));
	}

	public void testGetCenterY() {
		assertEquals(test_monster_.getY(), Utils.GetPixelY(30));
	}

	public void testIsClicked() {
		assertTrue(test_monster_.isClicked(test_monster_.GetCenterX(), test_monster_.GetCenterY()));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX() + 1000, test_monster_.GetCenterY()));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX() - 1000, test_monster_.GetCenterY()));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX(), test_monster_.GetCenterY() + 1000));
		assertFalse(test_monster_.isClicked(test_monster_.GetCenterX(), test_monster_.GetCenterY() - 1000));
	}

}
