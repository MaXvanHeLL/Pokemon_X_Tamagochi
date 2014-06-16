package com.reu.game.monster;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Stats {
	
	protected long creation_date_;
	
	protected float happiness_;
	protected float hunger_;
	protected float tiredness_;
	protected float dirtness_;	
	

	protected String name_;
	protected float weight_;	// In kg
	
	public Stats(float hunger, float happiness, float tiredness, float dirtness, String name, float weight, long creation_date)
	{
		this.happiness_ = happiness;
		this.hunger_ = hunger;
		this.tiredness_ = tiredness;
		this.dirtness_ = dirtness;
		
		
		this.name_ = name;
		this.weight_ = weight;
		
		this.creation_date_ = creation_date;
	}
	
	public Stats() {
		// TODO NOTHING HAHAHAHHAHA
	}
	
	public Stats(Stats old)
	{
		this.happiness_ = old.happiness_;
		this.hunger_ = old.hunger_;
		this.tiredness_ = old.tiredness_;
		this.dirtness_ = old.dirtness_;
		
		
		this.name_ = old.name_;
		this.weight_ = old.weight_;
		this.creation_date_ = old.creation_date_;
	}
	
	@Override
	public boolean equals(Object anObject)
	{
		if(anObject.getClass() == Stats.class)
		{
			Stats check = (Stats)anObject;
			return this.happiness_ == check.happiness_ && this.hunger_ == check.hunger_ && this.tiredness_ == check.tiredness_ &&
				this.dirtness_ == check.dirtness_  && this.weight_ == check.weight_ && this.creation_date_ == check.creation_date_ &&
				this.name_ == check.name_;
		}
		else
			return false;
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
		
	public void setTiredness(float d)
	{
		this.tiredness_ = d;
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

	public String getName() {
		return name_;
	}

	public void setName(String name_) {
		this.name_ = name_;
	}
	
	public void setCreationDate(long creation_date)
	{
		this.creation_date_ = creation_date;
	}

	public float getWeight() {
		return weight_;
	}

	public void setWeight(float weight_) {
		this.weight_ = weight_;
	}

	public int getAge() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.creation_date_);
		long ms   = cal.getTimeInMillis();
		long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - ms);
		return (int)(days);
	}
	
	public long getCreationDate(){
		return creation_date_;
	}
}
