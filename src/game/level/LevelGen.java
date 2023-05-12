package game.level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.entity.Entity;
import game.entity.Merchant;
import game.entity.item.GoldCoinItem;
import game.entity.item.HourglassItem;
import game.entity.item.SilverCoinItem;
import game.entity.mob.Coffin;
import game.entity.mob.Skeleton;
import game.entity.mob.Slime;
import game.entity.mob.WitheredSkeleton;

public class LevelGen<T extends Entity> {
	
	public static final LevelGen<?> gold = new LevelGen<GoldCoinItem>(GoldCoinItem.class).setIntervals(50, 10, 20).setYPos(60.0f, 80.0f);
	public static final LevelGen<?> silver = new LevelGen<SilverCoinItem>(SilverCoinItem.class).setIntervals(15, 5, 15).setYPos(80.0f, 80.0f);
	public static final LevelGen<?> time = new LevelGen<HourglassItem>(HourglassItem.class).setIntervals(5, 5, 10).setYPos(82.75862f, 68.57143f);
	public static final LevelGen<?> skeleton = new LevelGen<Skeleton>(Skeleton.class).setIntervals(35, 3, 6).setYPos(80.0f, 0.0f);
	public static final LevelGen<?> witheredSkeleton = new LevelGen<WitheredSkeleton>(WitheredSkeleton.class).setIntervals(500, 3, 15).setYPos(80.0f, 0.0f);
	public static final LevelGen<?> slime = new LevelGen<Slime>(Slime.class).setIntervals(200, 3, 15).setYPos(80.0f, 0.0f);
	public static final LevelGen<?> coffin = new LevelGen<Coffin>(Coffin.class).setIntervals(85, 3, 6).setYPos(80.0f, 80.0f);
	public static final LevelGen<?> merchant = new LevelGen<Merchant>(Merchant.class).setIntervals(0, 1, 120).setYPos(80.0f, 80.0f);
	public static final List<LevelGen<? extends Object>> generated = new ArrayList<LevelGen<? extends Object>>();
	
	private static final Random random = new Random();
	private Class<T> entityClass;
	private int minInterval;
	private int maxInterval;
	private int startBlock;
	private int xBlockLast;
	private float topPos;
	private float maxHeight;
	
	public LevelGen(Class<T> clazz) { this.entityClass = clazz; }
	
	public LevelGen<T> setIntervals(int start, int minInterval, int maxInterval) {
		this.startBlock = start;
		this.minInterval = minInterval;
		this.maxInterval = maxInterval;
		return this;
	}
	
	public LevelGen<T> setYPos(float topPos, float maxHeight) {
		this.topPos = topPos;
		this.maxHeight = maxHeight;
		return this;
	}
	
	public static void resetAll() {
		for(LevelGen<? extends Object> gen : generated) {
			gen.reset();
		}
	}
	
	public void reset() { this.xBlockLast = 0; }
	
	public float getYPos() { return random.nextFloat() * this.maxHeight + this.topPos; }
	public int getLastXBlock() { return this.xBlockLast; }
	public int getStartBlock() { return this.startBlock; }
	public int getMinInterval() { return this.minInterval; }
	public int getMaxInterval() { return this.maxInterval; }
	
	@SuppressWarnings("unchecked")
	public T createInstance(int xBlock) {
		this.xBlockLast = xBlock;
		Constructor<T> constructor = null;
		try {
			constructor = this.entityClass.getConstructor(Float.TYPE, Float.TYPE);
		} catch(NoSuchMethodException e) {
			throw new RuntimeException("Failed to get constructor");
		}
		
		try {
			return (T)((Entity)constructor.newInstance(Float.valueOf(xBlock * 32), Float.valueOf(getYPos())));
		} catch(InstantiationException e) {
			throw new RuntimeException("Failed to get instance");
		} catch(IllegalAccessException e) {
			throw new RuntimeException("Failed to get instance");
		} catch(InvocationTargetException e) {
			throw new RuntimeException("Failed to get instance");
		}
	}
	
	static {
		generated.add(gold);
		generated.add(silver);
		generated.add(time);
		generated.add(skeleton);
		generated.add(witheredSkeleton);
		generated.add(coffin);
		generated.add(merchant);
		generated.add(slime);
	}
	
}