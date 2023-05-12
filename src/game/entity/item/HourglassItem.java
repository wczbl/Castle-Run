package game.entity.item;

import game.entity.ItemEntity;
import game.entity.Knight;
import game.gfx.Art;
import game.gfx.Bitmap;

public class HourglassItem extends ItemEntity {

	public HourglassItem(float x, float y) {
		super(x, y);
		this.sprite = 1;
		this.xr = this.hw = Art.hourglass[0].w * 0.5f;
		this.yr = this.hh = Art.hourglass[0].h * 0.5f;
	}

	public void collect(Knight knight) { this.level.resetTime(); }

	public void render(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		int yy = (int)((this.y - this.yr) + Math.sin(this.tickTime * 0.01) * 3 + Math.cos(this.tickTime * 0.1) * 5.0);
		screen.draw(Art.hourglass[this.tickTime / 10 % 3], xx, yy);
	}
	
	
}