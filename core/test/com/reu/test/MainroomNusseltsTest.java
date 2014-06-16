package com.reu.test;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
		assertFalse(test_monster_.isSleeping());
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

	public void testIsSleeping() {
		test_monster_.sleepTime();
		assertTrue(test_monster_.isSleeping());
		test_monster_.stopSleeping();
		assertFalse(test_monster_.isSleeping());
		test_monster_.Reset();
	}

	public void testGetState_time_() {
		test_monster_.setState_time_(15);
		assertEquals(test_monster_.getState_time_(), 15f);
	}

	public void testGetStop_time_() {
		test_monster_.setStop_time_(20);
		assertEquals(test_monster_.getStop_time_(), 20f);
	}

	public void testGetBusy_time_() {
		test_monster_.setBusy_time_(25);
		assertEquals(test_monster_.getBusy_time_(), 25f);
	}

	public void testIsAnimated_() {
		test_monster_.Reset();
		assertFalse(test_monster_.isAnimated_());
		test_monster_.sleepTime();
		assertTrue(test_monster_.isAnimated_());
		test_monster_.Reset();
	}

	public void testIsBusy_() {
		test_monster_.Reset();
		assertFalse(test_monster_.isBusy_());
		test_monster_.sleepTime();
		assertTrue(test_monster_.isBusy_());
		test_monster_.Reset();
	}

	public void testGetR_generator_() {
		assertNotNull(test_monster_.getR_generator_());
	}

	public void testAddWalkAnimation() {
		Animation walk_ani_ = new Animation(1, new TextureRegion());
		test_monster_.AddWalkAnimation(walk_ani_);
		assertEquals(test_monster_.getWalk_animation_(), walk_ani_);
	}

	public void testAddSleepAnimation() {
		Animation sleep_ani_ = new Animation(1, new TextureRegion());
		test_monster_.AddSleepAnimation(sleep_ani_);
		assertEquals(test_monster_.getSleep_animation_(), sleep_ani_);
	}

	public void testMoveTo() {
		
		test_monster_.Reset();
		float old_stop_time_ = test_monster_.getStop_time_();
		float old_busy_time_ = test_monster_.getBusy_time_();
		test_monster_.MoveTo(50, 50, 1);
		assertTrue(test_monster_.isAnimated_());
		assertTrue(test_monster_.isBusy_());
		assertTrue(test_monster_.getStop_time_() > old_stop_time_);
		assertTrue(test_monster_.getBusy_time_() > old_busy_time_);
		assertTrue(test_monster_.getActions().size == 1);
		
	}

	public void testGoToRoom() {

		List<Vector2> wp;
		test_monster_.Reset();

		test_monster_.GoToRoom(RoomType.KITCHEN);
		wp = test_monster_.getWaypoints_();
		assertEquals(wp.size(), 3);
		assertEquals(wp.get(0), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
		assertEquals(wp.get(1), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(140)));
		assertEquals(wp.get(2), new Vector2(Utils.GetPixelX(0), Utils.GetPixelY(140)));
		
		test_monster_.Reset();

		test_monster_.GoToRoom(RoomType.BATHROOM);
		wp = test_monster_.getWaypoints_();
		assertEquals(wp.size(), 3);
		assertEquals(wp.get(0), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
		assertEquals(wp.get(1), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(140)));
		assertEquals(wp.get(2), new Vector2(Utils.GetPixelX(90), Utils.GetPixelY(140)));
		
		test_monster_.Reset();

		test_monster_.GoToRoom(RoomType.PLAYROOM);
		wp = test_monster_.getWaypoints_();
		assertEquals(wp.size(), 3);
		assertEquals(wp.get(0), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
		assertEquals(wp.get(1), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(106)));
		assertEquals(wp.get(2), new Vector2(Utils.GetPixelX(0), Utils.GetPixelY(106)));
		
		test_monster_.Reset();

		test_monster_.GoToRoom(RoomType.BEDROOM);
		wp = test_monster_.getWaypoints_();
		assertEquals(wp.size(), 4);
		assertEquals(wp.get(0), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
		assertEquals(wp.get(1), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(106)));
		assertEquals(wp.get(2), new Vector2(Utils.GetPixelX(83), Utils.GetPixelY(106)));
		assertEquals(wp.get(3), new Vector2(Utils.GetPixelX(83), Utils.GetPixelY(114)));
		
	}

	public void testSleepTime() {
		test_monster_.Reset();
		test_monster_.sleepTime();
		assertTrue(test_monster_.isSleeping());
		assertTrue(test_monster_.isBusy_());
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getSleep_animation_());
		assertEquals(test_monster_.getAnimation_time_(), 0f);
		assertEquals(test_monster_.getStop_time_(), test_monster_.getState_time_() + 500);
		assertEquals(test_monster_.getStop_time_(), test_monster_.getBusy_time_());
	}

	public void testStopSleeping() {
		
		test_monster_.Reset();
		test_monster_.stopSleeping();
		
		assertFalse(test_monster_.isSleeping());
		assertEquals(test_monster_.getState_time_(), test_monster_.getStop_time_());
		assertEquals(test_monster_.getState_time_(), test_monster_.getBusy_time_());
		assertEquals(test_monster_.getCurrent_animation_(), test_monster_.getWalk_animation_());
		
		List<Vector2> wp = test_monster_.getWaypoints_();
		
		assertEquals(wp.size(), 3);
		assertEquals(wp.get(0), new Vector2(Utils.GetPixelX(83), Utils.GetPixelY(106)));
		assertEquals(wp.get(1), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(106)));
		assertEquals(wp.get(2), new Vector2(Utils.GetPixelX(45), Utils.GetPixelY(80)));
		
	}

	public void testDoSomething() {
		// Contains random events - can only be tested roughly
		float old_busy_time = test_monster_.getBusy_time_();
		test_monster_.doSomething();
		assertTrue(test_monster_.getBusy_time_() > old_busy_time);
	}

	public void testGetWalking_back_from_bedroom_() {
		test_monster_.Reset();
		assertFalse(test_monster_.getWalking_back_from_bedroom_());
	}

	public void testSetWalking_back_from_bedroom_() {
		test_monster_.Reset();
		test_monster_.setWalking_back_from_bedroom_(true);
		assertTrue(test_monster_.getWalking_back_from_bedroom_());
	}

	public void testGetCenterX() {
		assertEquals(test_monster_.GetCenterX(), test_monster_.getX() + (test_monster_.getOriginX() * test_monster_.getScaleX()));
	}

	public void testGetCenterY() {
		assertEquals(test_monster_.GetCenterY(), test_monster_.getY() + (test_monster_.getOriginY() * test_monster_.getScaleY()));
	}

	public void testIsClicked() {
		// True cases
		for(int x = 1; x < test_monster_.getWidth(); x++)
		{
			for(int y = 1; y < test_monster_.getHeight(); y++)
			{
				assertTrue(test_monster_.isClicked(test_monster_.GetCenterX() - (test_monster_.getWidth() / 2.0f) + x, test_monster_.GetCenterY() - test_monster_.getHeight() / 2.0f + y));
			}
		}
		// Negative cases (border arround true cases
		int x = 0;
		for(int y_ = 1; y_ < test_monster_.getHeight(); y_++)
		{
			assertFalse(test_monster_.isClicked(test_monster_.GetCenterX() - (test_monster_.getWidth() / 2.0f) + x, test_monster_.GetCenterY() - test_monster_.getHeight() / 2.0f + y_));
		}
		
		int y = 0;
		for(int x_ = 1; x_ < test_monster_.getWidth(); x_++)
		{
			assertFalse(test_monster_.isClicked(test_monster_.GetCenterX() - (test_monster_.getWidth() / 2.0f) + x_, test_monster_.GetCenterY() - test_monster_.getHeight() / 2.0f + y));
		}
	}

}
