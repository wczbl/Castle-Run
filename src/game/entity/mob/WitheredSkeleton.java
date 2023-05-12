package game.entity.mob;

import game.Ability;
import game.entity.item.GoldCoinItem;
import game.entity.item.HourglassItem;

public class WitheredSkeleton extends Skeleton {

	public WitheredSkeleton(float x, float y) {
		super(x, y);
		this.tt = 7;
		this.health = 16;
		this.dmg = 3 + random.nextInt(2) + 1;
	}
	
	public void tick() {
		super.tick();
		
		if(this.onGround && random.nextFloat() < 0.03) {
			this.ya = -(Ability.jumpVelocity.getCurrentValue().floatValue());
		}
	}

	public void die() {
		if(this.die) return;
		
		super.die();
		this.level.add(new GoldCoinItem(this.x, this.y - this.yr));
		if(random.nextFloat() > 0.3f) this.level.add(new HourglassItem(this.x, this.y - this.yr));
	}
	
}