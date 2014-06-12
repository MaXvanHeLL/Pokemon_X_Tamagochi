package com.reu.game.monster;

public class Stats {
	
	protected float happiness_;
	protected float hunger_;
	protected float tiredness_;
	protected float dirtness_;
	
	protected int strength_;
	protected int health_;
	protected int agility_;
	
	public Stats(float hunger, float happiness, float tiredness, float dirtness, int strength, int health, int agility)
	{
		this.happiness_ = happiness;
		this.hunger_ = hunger;
		this.tiredness_ = tiredness;
		this.dirtness_ = dirtness;
		
		this.strength_ = strength;
		this.health_ = health;
		this.agility_ = agility;
	}
	
	// CARE_MODE Attributes
		public float getHunger ()
		{
			return this.hunger_;
		}
		
		public void setHunger(float hunger)
		{
			this.hunger_ = hunger;
		}
		
		public float getHappiness ()
		{
			return this.happiness_;
		}
		public void setHappiness(float happiness)
		{
			this.happiness_ = happiness;
		}
		
		public float getTiredness ()
		{
			return this.tiredness_;
		}
		
		public void setTiredness(float tiredness)
		{
			this.tiredness_ = tiredness;
		}
		
		public float getDirtness ()
		{
			return this.dirtness_;
		}
		
		// dirty little Nusselts! :D
		public void setDirtness(float dirtness)
		{
			this.dirtness_ = dirtness;
		}

}
