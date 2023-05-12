package game;

import java.util.ArrayList;
import java.util.List;

import game.entity.Knight;

public class Ability<T> {
	
	public static final Ability<Integer> attackTime = new Ability<Integer>("Attack Time Upgrade").add(0, 60).add(10, 30).add(30, 15).add(60, 8);
	public static final Ability<Float> jumpVelocity = new Ability<Float>("Jump Velocity Upgrade").add(0, Float.valueOf(6.0f)).add(5, Float.valueOf(7.0f)).add(20, Float.valueOf(8.0f)).add(45, Float.valueOf(9.0f));
	public static final Ability<Float> movementSpeed = new Ability<Float>("Movement Speed Upgrade").add(0, Float.valueOf(0.08f)).add(15, Float.valueOf(0.1f)).add(40, Float.valueOf(0.125f)).add(80, Float.valueOf(0.15f));
	public static final List<Ability<?>> abilities = new ArrayList<Ability<?>>();
	private int level = 0;
	private final String name;
	private List<Pair<Integer, T>> values = new ArrayList<Pair<Integer, T>>();
	
	public Ability(String name) {
		this.name = name;
	}
	
	public static void resetAll() {
		for(Ability<?> ability : abilities) {
			ability.level = 0;
		}
	}
	
	protected Ability<T> add(Integer cost, T value) {
		this.values.add(new Pair<Integer, T>(cost, value));
		return this;
	}
	
	public T getCurrentValue() {
		return this.values.get((int)this.level).second;
	}
	
	public boolean upgrade(Knight knight) {
		if(this.level >= this.values.size() - 1) return false;
		if(knight.score < (Integer)this.values.get((int)(this.level + 1)).first) return false;
		
		this.level++;
		knight.score -= (this.values.get((int)this.level).first).intValue();
		return true;
	}
	
	public int getNextCost() {
		if(this.level >= this.values.size() - 1) return 0;
		return this.values.get((int)(this.level + 1)).first;
	}
	
	public String getName() { return this.name; }
	public int getLevel() { return this.level; }
	
	static {
	    abilities.add(attackTime);
	    abilities.add(movementSpeed);
	    abilities.add(jumpVelocity);
	}
	
}