package game.entity.mob;

import game.entity.item.GoldCoinItem;
import game.gfx.Art;
import game.gfx.Bitmap;

public class Slime extends Mob {

	public Slime(float x, float y) {
		super(x, y);
		this.dieTime = 8;
		this.xr = this.hw = Art.slime[0].w / 2.0f;
		this.yr = this.hh = Art.slime[0].h / 2.0f;
		this.health = 4;
		this.dmg = 3;
	}
	
	public void tick() {
		super.tick();
		
		if(this.onGround && random.nextFloat() < 0.01) {
			this.ya -= (random.nextFloat() * 7.0f + 5.0f);
		}
	}
	
	public void renderShadow(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		screen.draw(Art.sprites[1], xx - 4, 160);
	}
	
	public void render(Bitmap screen, double alpha) {
		if(this.removed) return;
		
		int anim = 0;
		if(!this.die) {
			anim = (this.onGround ? 0 : 1) * 4;
			anim = this.onGround ? (anim += this.time / 5 % 4) : (anim += this.time / 20 % 2);
		} else anim += (8 - this.dieTime) / 4 % 2 + 9;
		
		int xx = (int)(this.x - this.xr);
		int yy = (int)(this.y - this.yr);
		screen.draw(Art.slime[anim], xx, yy);
	}
	
	public void die() {
		if(this.die) return;
		super.die();
		this.level.add(new GoldCoinItem(this.x, this.y - this.yr));
	}

}