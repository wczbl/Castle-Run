package game.entity;

import game.gfx.Art;
import game.gfx.Bitmap;
import game.screen.UpgradeScreen;

public class Merchant extends Entity {

	public int time = 1800;
	public boolean die;
	
	public Merchant(float x, float y) { super(x, y); }
	
	protected void touchedBy(Entity e) {
		if(this.die) return;
		
		if(e instanceof Knight) {
			this.die = true;
			this.level.gameScreen.setScreen(new UpgradeScreen(this.level.gameScreen));
		}
	}
	
	public void tick() {
		super.tick();
		if(this.time-- <= 0) this.removed = true;
	}
	
	public void renderShadow(Bitmap screen, double alpha) {
		int xx = (int)(this.x - this.xr);
		screen.draw(Art.sprites[1], xx, 160);
	}
	
	public void render(Bitmap screen, double alpha) {
		if(this.removed) return;
		int xx = (int)(this.x - this.xr);
		int yy = (int)(this.y - this.yr);
		screen.draw(Art.sprites[2], xx, yy);
	}

}