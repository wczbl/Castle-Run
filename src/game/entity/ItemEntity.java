package game.entity;

import game.gfx.Art;
import game.gfx.Bitmap;

public abstract class ItemEntity extends Entity {

	public int tickTime;
	
	public ItemEntity(float x, float y) { super(x, y); }
	
	public void tick() { this.tickTime++; }
	
	public abstract void collect(Knight knight);
	
	public void render(Bitmap screen, double alpha) {}
	
	public void renderShadow(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		screen.draw(Art.pickups[8], xx + 2, 182);
	}
	
}