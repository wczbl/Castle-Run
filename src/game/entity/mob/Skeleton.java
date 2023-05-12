package game.entity.mob;

import game.entity.item.GoldCoinItem;
import game.entity.item.SilverCoinItem;
import game.gfx.Art;
import game.gfx.Bitmap;

public class Skeleton extends Mob {

	protected int tt = 4;
	
	public Skeleton(float x, float y) {
		super(x, y);
		this.dieTime = 30;
	}
	
	public void render(Bitmap screen, double alpha) {
		if(this.removed) return;
		
		int anim = (this.tt + (this.die ? 0 : 1)) * 16;
		anim = this.die ? (int)(anim + (30 - this.dieTime) / 4 % 8) : (anim += this.time / 10 % 4);
		int xx = (int)(this.x - this.xr);
		int yy = (int)(this.y - this.yr);
		screen.draw(Art.sprites[anim], xx, yy);
	}
	
	public void die() {
		if(this.die) return;
		
		super.die();
		if(random.nextDouble() > 0.5f) this.level.add(new SilverCoinItem(this.x, this.y - this.yr));
		else if(random.nextDouble() > 0.9) this.level.add(new GoldCoinItem(this.x, this.y - this.yr));
	}

}