package game.entity.mob;

import game.entity.item.GoldCoinItem;
import game.gfx.Art;
import game.gfx.Bitmap;

public class Coffin extends Mob {

	private float yStatic;
	private float speed;
	
	public Coffin(float x, float y) {
		super(x, y);
		this.yStatic = y;
		this.hh = 6.0f;
		this.speed = random.nextFloat() * 0.3f + 0.1f;
		this.dmg = 2;
		this.health = 2;
	}

	public void tick() {
		super.tick();
		this.xa -= this.speed;
		this.y = this.yStatic - (int)((Math.sin(this.x * 0.055) * 6));
	}
	
	public void render(Bitmap screen, double alpha) {
		if(this.removed) return;
		int xx = (int)(this.x - this.xr);
		int yy = (int)(this.y - this.yr);
		screen.draw(Art.sprites[0], xx, yy);
	}
	
	public void die() {
		if(this.die) return;
		
		super.die();
		if(random.nextFloat() > 0.3f) this.level.add(new GoldCoinItem(this.x, this.y - this.yr));
	}
	
}