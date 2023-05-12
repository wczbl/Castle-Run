package game.entity;

import game.gfx.Art;
import game.gfx.Bitmap;

public class Reaper extends Entity {

	private Knight knight;
	private float xLocal;
	
	public Reaper(float x, float y, Knight knight) {
		super(x, y);
		this.xr = this.hw = Art.death[0].w * 0.5f;
		this.yr = this.hh = Art.death[0].h * 0.5f;
		this.knight = knight;
	}
	
	public void tick() {
		super.tick();
		float distance = 178;
		float dist = (float)(10 - this.level.distanceTime) / 10.0f * distance;
		float xxa = (this.knight.x - distance + dist - this.x) / distance;
		this.xLocal += xxa;
		this.x = this.knight.x - distance + this.xLocal;
	}
	
	public void renderShadow(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		screen.draw(Art.sprites[1], xx, 160);
	}
	
	public void render(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		int yy = (int)(this.y - this.yr);
		screen.draw(Art.death[(int)Math.abs(this.x) / 10 % 2], xx, yy);
	}
	
}