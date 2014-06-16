package com.reu.test;

import java.util.Calendar;

import com.reu.game.monster.Stats;

import junit.framework.TestCase;

public class StatsTest extends TestCase {
	
	private Stats test_stats_;
	private long now_;
	private long yesterday_;
	private long the_day_before_yesterday_;

	public StatsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		now_ = Calendar.getInstance().getTimeInMillis();
		yesterday_ = now_ - (1000 * 24 * 60 * 60) - 1;
		the_day_before_yesterday_ = yesterday_ - (1000 * 24 * 60 * 60);
		test_stats_ = new Stats(5,10,15,20,"Testlts",25,now_);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStatsFloatFloatFloatFloatStringFloatLong() {
		assertTrue(true);
	}

	public void testStats() {
		Stats new_stats = new Stats();
		new_stats.setCreationDate(now_);
		new_stats.setHunger(5);
		new_stats.setHappiness(10);
		new_stats.setTiredness(15);
		new_stats.setDirtness(20);
		new_stats.setName("Testlts");
		new_stats.setWeight(25);
		new_stats.setCreationDate(now_);
		assertEquals(new_stats, test_stats_);
	}

	public void testStatsStats() {
		Stats new_stats = new Stats(test_stats_);
		assertEquals(new_stats, test_stats_);
	}

	public void testEqualsObject() {
		Stats new_stats = new Stats(test_stats_);
		boolean equals = new_stats.getAge() == test_stats_.getAge()
				&& new_stats.getCreationDate() == test_stats_.getCreationDate()
				&& new_stats.getDirtness() == test_stats_.getDirtness()
				&& new_stats.getHappiness() == test_stats_.getHappiness()
				&& new_stats.getHunger() == test_stats_.getHunger()
				&& new_stats.getName() == test_stats_.getName()
				&& new_stats.getTiredness() == test_stats_.getTiredness()
				&& new_stats.getWeight() == test_stats_.getWeight();
		
		assertEquals(equals, true);
	}

	public void testGetHunger() {
		assertEquals(test_stats_.getHunger(), 5f);
	}

	public void testSetHunger() {
		test_stats_.setHunger(30.0f);
		assertEquals(test_stats_.getHunger(), 30f);
	}

	public void testGetHappiness() {
		assertEquals(test_stats_.getHappiness(), 10f);
	}

	public void testSetHappiness() {
		test_stats_.setHappiness(35.0f);
		assertEquals(test_stats_.getHappiness(), 35.0f);
	}

	public void testGetTiredness() {
		assertEquals(test_stats_.getTiredness(), 15f);
	}

	public void testSetTiredness() {
		test_stats_.setTiredness(40.0f);
		assertEquals(test_stats_.getTiredness(), 40.0f);
	}

	public void testGetDirtness() {
		assertEquals(test_stats_.getDirtness(), 20f);
	}

	public void testSetDirtness() {
		test_stats_.setDirtness(45.0f);
		assertEquals(test_stats_.getDirtness(), 45.0f);
	}

	public void testGetName() {
		assertEquals(test_stats_.getName(), "Testlts");
	}

	public void testSetName() {
		test_stats_.setName("Franz");
		assertEquals(test_stats_.getName(), "Franz");
	}

	public void testGetCreationDate() {
		assertEquals(test_stats_.getCreationDate(), now_);
	}
	
	public void testSetCreationDate() {
		test_stats_.setCreationDate(yesterday_);
		assertEquals(test_stats_.getCreationDate(), yesterday_);
	}

	public void testGetWeight() {
		assertEquals(test_stats_.getWeight(), 25.0f);
	}

	public void testSetWeight() {
		test_stats_.setWeight(50.0f);
		assertEquals(test_stats_.getWeight(), 50.0f);
	}

	public void testGetAgeOne() {
		test_stats_.setCreationDate(yesterday_);
		assertEquals(test_stats_.getAge(), 1);
	}
	
	public void testGetAgeTwo() {
		test_stats_.setCreationDate(the_day_before_yesterday_);
		assertEquals(test_stats_.getAge(), 2);
	}

}
