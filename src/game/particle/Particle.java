package game.particle;

import java.util.Random;

import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.Level;

public abstract class Particle {

	public static final Random random = new Random();
	public double x;
	public double y;
	public double xa;
	public double ya;
	public float rot;
	public float rotA;
	public int sprite;
	public int time;
	public int maxTime;
	public Level level;
	public boolean removed;
	
	public Particle(float x, float y) {
		this.x = x;
		this.y = y;
		this.time = 0;
		this.maxTime = 120;
	}
	
	public void init(Level level) { this.level = level; }

	public void tick() {
		if(this.time++ >= this.maxTime) {
			die();
			return;
		}
	}
	
	public void render(Bitmap screen, double alpha) {
		int xx = (int)(this.x - 4);
		int yy = (int)(this.y - 4);
		screen.draw(Art.particles[this.sprite].rotate(this.rot), xx, yy);
	}
	
	public void die() { this.removed = true; }
}