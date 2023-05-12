package game.entity.item;

import game.entity.ItemEntity;
import game.entity.Knight;
import game.gfx.Art;
import game.gfx.Bitmap;

public class SilverCoinItem extends ItemEntity {

	public int score = 1;
	
	public SilverCoinItem(float x, float y) {
		super(x, y);
		this.xr = this.hw = Art.pickups[0].w * 0.5f;
		this.yr = this.hh = Art.pickups[0].h * 0.5f;
	}

	public void collect(Knight knight) {
		if(this.removed) return;
		knight.score += this.score;
	}
	
	public void render(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		int yy = (int)((this.y - this.yr) + Math.sin(this.tickTime * 0.01) * 3 + Math.cos(this.tickTime * 0.1) * 5.0);
		screen.draw(Art.pickups[this.tickTime / 10 % 3 + 3], xx, yy);
	}

}