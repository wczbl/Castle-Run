package game.particle;

import game.gfx.Art;
import game.gfx.Bitmap;

public class ExplosionParticle extends Particle {

	public ExplosionParticle(float x, float y) {
		super(x, y);
		this.maxTime = 18;
	}
	
	public void render(Bitmap screen, double alpha) {
		int xx = (int)(this.x - 16);
		int yy = (int)(this.y - 16);
		screen.draw(Art.sprites[this.time / 3 + 96], xx, yy);
	}

}