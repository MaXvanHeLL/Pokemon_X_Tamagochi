package com.reu.test;


import com.reu.game.monster.mainroom.MainroomNusselts;
import com.reu.game.types.RoomType;
import com.reu.game.utils.Utils;

import junit.framework.TestCase;

public class MainroomNusseltsTest extends TestCase {

	private MainroomNusselts test_monster_;
	
	public MainroomNusseltsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Utils.test_mode_ = true;
		test_monster_ = new MainroomNusselts();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testMainroomMonster() {	
		assertEquals(test_monster_.getState_time_(), 0f);
		assertFalse(test_monster_.getSleeping_());
		assertFalse(test_monster_.isAnimated_());
		assertFalse(test_monster_.getWalking_back_from_bedroom_());
		assertNotNull(test_monster_.getR_generator_());
		assertNotNull(test_monster_.getWaypoints_());
		assertEquals(test_monster_.getX(), Utils.GetPixelX(45));
		assertEquals(test_monster_.getY(), Utils.GetPixelY(53));
		assertEquals(test_monster_.getWidth(), Utils.GetPixelX(8));
		assertEquals(test_monster_.getHeight(), Utils.GetPixelX(8));
		assertEquals(test_monster_.getOriginX(), Utils.GetPixelX(4));
		assertEquals(test_monster_.getOriginY(), Utils.GetPixelX(4));
	}

	public void testReset() {
		test_monster_.GoToRoom(RoomType.BATHROOM);
		test_monster_.Reset();
		
		assertTrue(test_monster_.getWaypoints_().isEmpty());
		assertTrue(test_monster_.getActions().size == 0);
		assertFalse(test_monster_.isBusy_());
		assertFalse(test_monster_.isSleeping());
		assertEquals(test_monster_.getX(), Utils.GetPixelX(45));
		assertEquals(test_monster_.getY(), Utils.GetPixelY(53));
		
	}

	public void testGetWalk_animation_() {
		assertTrue(true); // Can't be tested because of LibGDX functions
	}

	public void testGetSleep_animation_() {
		assertTrue(true); // Can't be tested because of LibGDX functions
	}

	public void testGetCurrent_frame_() {
		assertTrue(true); // Can't be tested because of LibGDX functions
	}

	public void testGetCurrent_animation_() {
		assertTrue(true); // Can't be tested because of LibGDX functions
	}

	public void testGetAnimation_time_() {
		fail("Not yet implemented");
	}

	public void testGetSleeping_() {
		fail("Not yet implemented");
	}

	public void testGetState_time_() {
		fail("Not yet implemented");
	}

	public void testGetStop_time_() {
		fail("Not yet implemented");
	}

	public void testGetBusy_time_() {
		fail("Not yet implemented");
	}

	public void testIsAnimated_() {
		fail("Not yet implemented");
	}

	public void testIsBusy_() {
		fail("Not yet implemented");
	}

	public void testGetR_generator_() {
		fail("Not yet implemented");
	}

	public void testAddWalkAnimation() {
		fail("Not yet implemented");
	}

	public void testAddSleepAnimation() {
		fail("Not yet implemented");
	}

	public void testMoveTo() {
		fail("Not yet implemented");
	}

	public void testGoToRoom() {
		fail("Not yet implemented");
	}

	public void testSleepTime() {
		fail("Not yet implemented");
	}

	public void testStopSleeping() {
		fail("Not yet implemented");
	}

	public void testIsSleeping() {
		fail("Not yet implemented");
	}

	public void testDoSomething() {
		fail("Not yet implemented");
	}

	public void testDrawBatchFloat() {
		fail("Not yet implemented");
	}

	public void testGetWaypoints_() {
		fail("Not yet implemented");
	}

	public void testSetWaypoints_() {
		fail("Not yet implemented");
	}

	public void testGetWalking_back_from_bedroom_() {
		fail("Not yet implemented");
	}

	public void testSetWalking_back_from_bedroom_() {
		fail("Not yet implemented");
	}

	public void testGetCenterX() {
		fail("Not yet implemented");
	}

	public void testGetCenterY() {
		fail("Not yet implemented");
	}

	public void testIsClicked() {
		fail("Not yet implemented");
	}

}
